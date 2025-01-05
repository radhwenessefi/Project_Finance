package tn.esprit.projectbackend.Entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Tradee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) // Foreign key to User
    User user; // User who performed the trade

    @ManyToOne
    @JoinColumn(name = "asset_id", nullable = false) // Foreign key to Asset
    Asset asset; // Asset involved in the trade

    @Column(nullable = false)
    Double profit; // Profit or loss from the trade

    @Column(nullable = false)
    LocalDateTime tradeDate; // Date of the trade
}
