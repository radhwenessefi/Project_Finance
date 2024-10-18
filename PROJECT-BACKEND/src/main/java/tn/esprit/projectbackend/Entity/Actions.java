package tn.esprit.projectbackend.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Actions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le symbole ne doit pas être vide.")
    @Size(min = 1, max = 5, message = "Le symbole doit contenir entre 1 et 5 caractères.")
    private String symbol;

    @NotBlank(message = "Le nom de l'entreprise ne doit pas être vide.")
    @Size(max = 100, message = "Le nom de l'entreprise ne doit pas dépasser 100 caractères.")
    private String companyName;

    @Positive(message = "Le prix actuel doit être supérieur à 0.")
    private double currentPrice;

    private double openPrice; // Prix d'ouverture
    private double previousClose; // Prix de clôture précédent
    private double dayHigh; // Plus haut du jour
    private double dayLow; // Plus bas du jour



    @OneToMany(mappedBy = "actions", cascade = CascadeType.ALL)
    private List<Ordre> orders;
}
