package tn.esprit.projectbackend.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String username;
    private String email;
    private String password;
    private String status;
    private double balance;

    double highestsold;
    double lowestsold;

    // Nouveau champ pour stocker la balance précédente
    @Column(name = "previous_balance")
    double previousBalance;

    @ManyToOne
    @JsonIgnore
    @JoinTable(
            name = "account_insurance",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "insurance_id")
    )
    private Insurance insurance;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "pack_id")
    private Pack pack;


    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Refund> refunds = new ArrayList<>();
}
