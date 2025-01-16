package tn.esprit.projectbackend.Service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.projectbackend.Entity.Account;
import tn.esprit.projectbackend.Entity.Pack;
import tn.esprit.projectbackend.Repository.AccountRepository;
import tn.esprit.projectbackend.Repository.PackRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PackService implements IPackService{

    private PackRepository packRepository;
    private AccountRepository accountRepository;
    public Pack addPack(Pack pack) {

        return packRepository.save(pack);
    }


    public void deletePack(int Id_Study) {
        packRepository.deleteById((long) Id_Study);
    }


    public Pack updatePack(Pack pack) {
        return packRepository.save(pack);
    }

    public List<Pack> getAllPack() {
        return packRepository.findAll();
    }

    public Pack getPack(int Id_Study) {
        return packRepository.findById((long) Id_Study).get();
    }

//    public List<Pack> searchByPriceP(long minPrice, long maxPrice) {
//        return packRepository.findByPriceBetween(minPrice, maxPrice);
//    }
//
//    @Transactional
//    public void affecterPackToCompte(Long idAccount, Long idPack, Float perte) {
//        Optional<Account> optionalAccount = accountRepository.findById(idAccount);
//        Optional<Pack> optionalPack = packRepository.findById(idPack);
//
//        if (optionalAccount.isPresent() && optionalPack.isPresent()) {
//            Account account = optionalAccount.get();
//            Pack pack = optionalPack.get();
//            account.setPack(pack);
//            Float remboursement = perte * (pack.getPourcentage() / 100);
//            account.setBalance(account.getBalance() + remboursement);
//
//            accountRepository.save(account);
//        } else {
//            throw new RuntimeException("Account or Pack not found");
//        }
//    }

    /**
     * Calcule le remboursement pour un compte en fonction de sa perte et de son pack.
     * @param accountId ID du compte
     * @param loss Montant de la perte
     * @return Montant du remboursement
     * @throws RuntimeException en cas d'erreur
     */
    public double calculateRefund(long accountId, double loss) {
        // Récupérer le compte
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        // Vérifier si un pack est associé au compte
        Pack pack = account.getPack();
        if (pack == null) {
            throw new RuntimeException("No pack associated with this account");
        }

        // Vérifier si le pourcentage de remboursement est défini
        double percentage = pack.getPourcentage();


        // Calculer le montant du remboursement
        double refundAmount = (loss * percentage) / 100;

        // Ajouter le remboursement au solde du compte
        account.setBalance(account.getBalance() + refundAmount);

        // Sauvegarder les changements dans la base de données
        accountRepository.save(account);

        return refundAmount;
    }
}
