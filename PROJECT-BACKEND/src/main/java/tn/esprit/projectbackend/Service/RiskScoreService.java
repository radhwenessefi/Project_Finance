package tn.esprit.projectbackend.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.projectbackend.Entity.Account;
import tn.esprit.projectbackend.Entity.Refund;
import tn.esprit.projectbackend.Repository.RefundRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RiskScoreService implements IRiskScoreService {

    private final RefundRepository refundRepository;

    /**
     * Calcule l'écart-type pour une liste de doubles.
     * @param values Liste des valeurs (historique des remboursements)
     * @return L'écart-type
     */
    public double calculateStandardDeviation(List<Double> values) {
        if (values == null || values.isEmpty()) {
            return 0.0; // Aucun historique
        }

        double mean = values.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
        double variance = values.stream()
                .mapToDouble(value -> Math.pow(value - mean, 2))
                .average()
                .orElse(0.0);
        return Math.sqrt(variance);
    }

    /**
     * Calcule le score de risque pour un compte.
     * @param account Le compte pour lequel calculer le score
     * @return Le score de risque (Low, Medium, High)
     */
    public String calculateRiskScore(Account account) {
        // Étape 1 : Récupérer l'historique des remboursements pour le compte
        List<Refund> refunds = refundRepository.findByAccountId(account.getId());

        // Vérification : Si aucun remboursement trouvé, retourner un score par défaut
        if (refunds.isEmpty()) {
            System.out.println("No refund history found for account ID: " + account.getId());
            return "Low"; // Par défaut, un score de risque faible si pas d'historique
        }

        // Étape 2 : Extraire les montants des remboursements
        List<Double> refundAmounts = refunds.stream()
                .map(Refund::getRefundAmount) // Récupérer le montant de chaque remboursement
                .toList();

        // Étape 3 : Calculer l'écart-type (stdDev) des montants
        double stdDev = calculateStandardDeviation(refundAmounts);

        // Étape 4 : Déterminer le score de risque basé sur l'écart-type
        String riskScore;
        if (stdDev < 50) {
            riskScore = "Low";
        } else if (stdDev < 150) {
            riskScore = "Medium";
        } else {
            riskScore = "High";
        }

        // Étape 5 : Loguer les informations utiles pour le suivi
        System.out.printf(
                "Account ID: %d, StdDev: %.2f, Risk Score: %s%n",
                account.getId(), stdDev, riskScore
        );

        // Retourner le score de risque calculé
        return riskScore;
    }
}


