package tn.esprit.projectbackend.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long UserId;
    String FName;
    String LName;
    Number PhoneNumber;
    String email;
    String password;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="usersportfolio")
    private Set<PortfolioInvestment> portfolioInvestments;


}
