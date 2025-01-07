package tn.esprit.projectbackend.Entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Asset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false, unique = true)
    String assetName; // Name of the asset (e.g., Bitcoin, Apple Stock)

    @Column(nullable = false)
    String assetType; // Type of asset (e.g., Crypto, Stock)
}
