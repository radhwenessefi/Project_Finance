package tn.esprit.projectbackend.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Crypto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le symbole ne doit pas être vide.")
    @Size(min = 1, max = 10, message = "Le symbole doit contenir entre 1 et 10 caractères.")
    private String symbol; // Symbole de la cryptomonnaie (ex: BTC, ETH)

    @NotBlank(message = "Le nom de la cryptomonnaie ne doit pas être vide.")
    @Size(max = 100, message = "Le nom de la cryptomonnaie ne doit pas dépasser 100 caractères.")
    private String cryptoName;

    @Positive(message = "Le prix actuel doit être supérieur à 0.")
    private double currentPrice; // Prix actuel de la cryptomonnaie

    private double openPrice; // Prix d'ouverture
    private double previousClose; // Prix de clôture précédent
    private double dayHigh; // Plus haut du jour
    private double dayLow; // Plus bas du jour


}
