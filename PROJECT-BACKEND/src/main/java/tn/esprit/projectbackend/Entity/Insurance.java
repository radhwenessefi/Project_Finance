package tn.esprit.projectbackend.Entity;



import jakarta.persistence.*;


import lombok.*;
import lombok.experimental.FieldDefaults;


import java.util.Date;
import java.util.Set;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

@FieldDefaults(level = AccessLevel.PRIVATE)
public class Insurance {

      @Id
      @GeneratedValue(strategy = GenerationType.IDENTITY)
        Long  PolicyNumber;
        String PolicyHolderName;
        Date StartDate;
        Date EndDate;
        String InsuranceType;
        float ContributionAmount;
        float CoverageAmount;
        String InsuranceStatus;
        String Agent;
  @OneToMany(cascade = CascadeType.ALL, mappedBy="insurances")
  private Set<Pack> packs;






}
