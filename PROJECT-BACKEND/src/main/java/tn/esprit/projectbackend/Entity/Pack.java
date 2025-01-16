package tn.esprit.projectbackend.Entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)

public class Pack {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_pack;
     private String title;
     private String Description;
    private Float Price;
    private Float pourcentage;

@ManyToOne
@JsonBackReference
private Insurance insurance;

@OneToMany(mappedBy = "pack")
@JsonIgnore
private List<Account> accounts;


    public double calculateRefund(double loss) {
        return (loss * pourcentage) / 100; // Calcul du remboursement basé sur la perte et le pourcentage du pack
    }

    public void add(Pack pack) {
    }
}
