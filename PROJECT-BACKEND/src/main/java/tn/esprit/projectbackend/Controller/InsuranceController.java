package tn.esprit.projectbackend.Controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;
import tn.esprit.projectbackend.Entity.Insurance;
import tn.esprit.projectbackend.Entity.InsuranceType;
import tn.esprit.projectbackend.Entity.Refund;
import tn.esprit.projectbackend.Service.EmailService;
import tn.esprit.projectbackend.Service.IInsuranceService;

import javax.mail.MessagingException;
import java.util.List;

@CrossOrigin("http://localhost:4200/")
@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/Insurance")
public class InsuranceController {

    private IInsuranceService iInsuranceService;
    public JavaMailSender emailSender;
    private EmailService emailService;


    @PostMapping("/add")
    public ResponseEntity<Insurance> addInsurance(@RequestBody Insurance insurance) {
        return ResponseEntity.ok(iInsuranceService.addInsurance(insurance));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteInsurance(@PathVariable("id") int id) {
        iInsuranceService.deleteInsurance(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/update")
    public ResponseEntity<Insurance> updateInsurance(@RequestBody Insurance insurance) {
        return ResponseEntity.ok(iInsuranceService.updateInsurance(insurance));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Insurance>> getAllInsurance() {
        return ResponseEntity.ok(iInsuranceService.getAllInsurance());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Insurance> getInsurance(@PathVariable("id") int id) {
        return ResponseEntity.ok(iInsuranceService.getInsurance(id));
    }

    @GetMapping("/searchByType")
    public ResponseEntity<List<Insurance>> searchByType(@RequestParam InsuranceType type) {
        return ResponseEntity.ok(iInsuranceService.searchByType(type));
    }


    @PostMapping("/{accountId}/buy/{insuranceId}/{packId}")
    public ResponseEntity<String> purchaseInsurance(
            @PathVariable long accountId,
            @PathVariable long insuranceId,
            @PathVariable long packId) {
        String response = iInsuranceService.purchaseInsurance(accountId, insuranceId, packId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/Insurance/refund/{accountId}/{lossAmount}")
    public ResponseEntity<String> refundAccountOnLoss(
            @PathVariable long accountId,
            @PathVariable double lossAmount) {
        String response = iInsuranceService.refundAccountOnLoss(accountId, lossAmount);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{accountId}/refunds")
    public ResponseEntity<List<Refund>> getRefundHistory(@PathVariable Long accountId) {
        List<Refund> refunds = iInsuranceService.getRefundHistory(accountId);

        if (refunds.isEmpty()) {
            return ResponseEntity.noContent().build(); // HTTP 204
        }
        return ResponseEntity.ok(refunds); // HTTP 200
    }


    /**
     * Endpoint pour déclencher manuellement le calcul de la variance et les remboursements.
     * Utile pour les tests ou pour vérifier si la méthode fonctionne comme prévu.
     */
    @PostMapping("/calculate-variance")
    public String calculateVarianceAndProcessRefunds() {
        try {
            iInsuranceService.calculateVarianceAndProcessRefunds();
            return "Variance calculation and refunds processed successfully.";
        } catch (Exception e) {
            return "Error occurred during processing: " + e.getMessage();
        }
    }

    @PostMapping("/testsendattachementemail")
    @ResponseBody
    public String sendAttachmentEmail(@RequestBody String mail ) throws MessagingException, jakarta.mail.MessagingException {
        emailService.sendAttachmentEmail(mail);
        return ("Email Sent !") ;
    }

    /**
     * Endpoint pour récupérer tous les comptes avec leurs détails.
     * Permet de visualiser les balances actuelles et les balances précédentes.
     */
}
