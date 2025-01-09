package tn.esprit.projectbackend.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;


    // Ajoutez votre méthode getBody() ici

    @Getter
    @Setter
    public class Result {
        @JsonProperty("v")
        private double v;  // volume

        @JsonProperty("vw")
        private double vw; // volume weighted average

        @JsonProperty("o")
        private double o;  // open

        @JsonProperty("c")
        private double c;  // close

        @JsonProperty("h")
        private double h;  // high

        @JsonProperty("l")
        private double l;  // low

        @JsonProperty("t")
        private long t;    // timestamp

        @JsonProperty("n")
        private int n;     // number of trades
    }

    // Ajoutez d'autres méthodes ou constructeurs si nécessaire
