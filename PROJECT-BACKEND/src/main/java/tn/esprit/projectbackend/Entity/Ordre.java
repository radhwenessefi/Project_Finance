package tn.esprit.projectbackend.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString


public class Ordre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Le type d'ordre ne peut pas être nul.")
    @Enumerated(EnumType.STRING)
    private OrderType type;

    @Min(value = 1, message = "La quantité doit être positive.")
    private int quantity;

    @DecimalMin(value = "0.01", message = "Le prix doit être supérieur à 0.")
    private double price;

    @NotNull(message = "Le montant ne peut pas être nul")
    Long takeProfit;
    @NotNull(message = "Le montant ne peut pas être nul")
    Long stopLoss;

    @NotNull(message = "Le statut de l'ordre ne peut pas être nul.")
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @NotNull(message = "La date de création ne peut pas être nulle.")
    private LocalDateTime timestamp;




    @ManyToOne
    @JoinColumn(name = "actions_id")
    private Actions actions;

    public enum OrderType {
        BUY, SELL
    }

    public enum OrderStatus {
        PENDING, EXECUTED, CANCELLED
    }


    // Méthode pour valider l'ordre
    public void validate() {

        if (this.type == OrderType.BUY && this.quantity <= 0) {
            throw new IllegalArgumentException("La quantité pour un ordre d'achat doit être supérieure à zéro.");
        }
        if (this.type == OrderType.SELL && this.quantity <= 0) {
            throw new IllegalArgumentException("La quantité pour un ordre de vente doit être supérieure à zéro.");
        }
    }
}
