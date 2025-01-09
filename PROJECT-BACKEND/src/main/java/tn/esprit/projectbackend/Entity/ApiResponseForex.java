package tn.esprit.projectbackend.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ApiResponseForex {
    @JsonProperty("request_id")
    private String requestId;
    private String ticker;         // Identifiant de l'actif
    private int queryCount;        // Nombre de requêtes effectuées
    private int resultsCount;      // Nombre de résultats dans la réponse
    private boolean adjusted;      // Indique si les données sont ajustées ou non
    private List<Result> results;  // Liste des résultats
    private String status;         // Statut de la réponse
    private int count;             // Compte des éléments dans la réponse

    @Getter
    @Setter
    public static class Result {
        @JsonProperty("v")
        private double volume;  // Volume de transactions

        @JsonProperty("vw")
        private double volumeWeightedAverage; // Moyenne pondérée par le volume

        @JsonProperty("o")
        private double openPrice;  // Prix d'ouverture

        @JsonProperty("c")
        private double closePrice;  // Prix de clôture

        @JsonProperty("h")
        private double highPrice;  // Prix le plus haut de la journée

        @JsonProperty("l")
        private double lowPrice;  // Prix le plus bas de la journée

        @JsonProperty("t")
        private long timestamp;   // Horodatage de la donnée

        @JsonProperty("n")
        private int tradeCount;    // Nombre de transactions
    }
}
