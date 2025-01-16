package tn.esprit.projectbackend.Service;

import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tn.esprit.projectbackend.Entity.*;
import tn.esprit.projectbackend.Repository.AccountRepository;
import tn.esprit.projectbackend.Repository.InsuranceRepository;
import tn.esprit.projectbackend.Repository.PackRepository;
import tn.esprit.projectbackend.Repository.RefundRepository;

import javax.mail.MessagingException;
import java.time.LocalDateTime;
import java.util.List;

import static java.awt.geom.Path2D.contains;

@Service
@AllArgsConstructor
public class InsuranceService implements IInsuranceService{

    private InsuranceRepository insuranceRepository;

    private AccountRepository accountRepository;

    private PackRepository packRepository;

    private RefundRepository refundRepository;

    private EmailService emailService;
    public Insurance addInsurance(Insurance insurance) {

        return insuranceRepository.save(insurance);
    }


    public void deleteInsurance(int Id_Study) {
        insuranceRepository.deleteById((long) Id_Study);
    }


    public Insurance updateInsurance(Insurance insurance) {
        return insuranceRepository.save(insurance);
    }

    public List<Insurance> getAllInsurance() {
        return insuranceRepository.findAll();
    }

    public Insurance getInsurance(int Id_Study) {
        return insuranceRepository.findById((long) Id_Study).get();
    }

    public List<Insurance> searchByType(InsuranceType type) {
        return insuranceRepository.findAllByTypeinsurance(type);
    }


    /**
     * Méthode pour acheter une assurance pour un compte.
     *
     * @param accountId ID du compte qui souhaite acheter une assurance
     * @param insuranceId ID de l'assurance à acheter
     * @return Message confirmant l'achat ou indiquant un problème
     */
    public String purchaseInsurance(long accountId, long insuranceId, long packId) {
        // Récupérer le compte
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        // Récupérer l'assurance
        Insurance insurance = insuranceRepository.findById(insuranceId)
                .orElseThrow(() -> new RuntimeException("Insurance not found"));

        // Récupérer le pack
        Pack pack = packRepository.findById(packId)
                .orElseThrow(() -> new RuntimeException("Pack not found"));

        // Vérifier si le compte a déjà une assurance
        if (account.getInsurance() != null) {
            return "Error: The account already has an insurance.";
        }

        // Vérifier si le solde est suffisant
        if (account.getBalance() < pack.getPrice()) {
            return "Error: Insufficient balance to purchase this insurance.";
        }

        account.setPreviousBalance(account.getBalance());

        // Débiter le compte
        account.setBalance(account.getBalance() - pack.getPrice());

        // Associer l'assurance au compte
        account.setInsurance(insurance);

        // Associer le pack au compte
        account.setPack(pack);

        // Sauvegarder les modifications
        accountRepository.save(account);

        return String.format("Success: Insurance '%s' purchased successfully. Remaining balance: %.2f",
                insurance.getTypeinsurance(),
                account.getBalance());
    }


    public String refundAccountOnLoss(long accountId, double lossAmount) {
        // Récupérer le compte
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        // Vérifier si le compte a un pack associé
        Pack pack = account.getPack();
        if (pack == null) {
            return "Error: No pack associated with this account. Refund cannot be processed.";
        }

        // Calculer le montant à rembourser en fonction du pourcentage du pack
        double refundPercentage = pack.getPourcentage() / 100.0; // Convertir en décimal
        double refundAmount = lossAmount * refundPercentage;

        if (refundAmount <= 0) {
            return "Error: Refund amount must be greater than zero.";
        }

        //account.setBalance(account.getBalance() - lossAmount);

        // Ajouter le montant remboursé au solde du compte
        account.setBalance(account.getBalance() + refundAmount);
        account.setPreviousBalance(account.getBalance()+ refundAmount);

        // Créer une nouvelle entrée de remboursement dans l’historique
        Refund refund = new Refund(account, refundAmount, LocalDateTime.now(), "Remboursement pour perte");
        refundRepository.save(refund);
        
        // Sauvegarder les modifications dans la base de données
        accountRepository.save(account);

//        // Envoi de l'email après achat réussi
//        try {
//            String subject = "Confirmation d'achat d'assurance";
//            String body = String.format(
//                    "<h3>Achat confirmé !</h3><p>Bonjour %s,</p><p>Vous avez acheté avec succès une assurance '%s' au prix de %.2f.</p>",
//                    account.getUsername(), pack.getPrice()
//            );
//
//            emailService.sendAttachmentEmail(account.getEmail());
//        } catch (jakarta.mail.MessagingException e) {
//            throw new RuntimeException(e);
//        }


        return String.format("Success: Account refunded with %.2f. New balance: %.2f",
                refundAmount, account.getBalance());


    }

    public List<Refund> getRefundHistory(long accountId) {
        return refundRepository.findByAccountId(accountId);
    }

   // @Scheduled(fixedRate = 5000)
    public void calculateVarianceAndProcessRefunds() {
        List<Account> accounts = accountRepository.findAll();

        for (Account account : accounts) {
            double previousBalance = account.getPreviousBalance();
            double currentBalance = account.getBalance();
            double variance = currentBalance - previousBalance;

            if (variance < 0) {
                double lossAmount = -variance;
                refundAccountOnLoss(account.getId(), lossAmount);
            }

            account.setPreviousBalance(currentBalance);
            accountRepository.save(account);
        }
    }

}
