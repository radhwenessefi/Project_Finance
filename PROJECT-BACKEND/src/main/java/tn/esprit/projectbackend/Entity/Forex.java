package tn.esprit.projectbackend.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Forex {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "La paire de devises ne doit pas être vide.")
    @Size(min = 3, max = 7, message = "La paire de devises doit contenir entre 3 et 7 caractères.")
    private String pair; // Paire de devises, ex : EUR/USD

    @Positive(message = "Le prix actuel doit être supérieur à 0.")
    private double currentPrice;

    private double previousClose;
    private double dayHigh;
    private double dayLow;
}
