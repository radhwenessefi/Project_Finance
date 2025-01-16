package tn.esprit.projectbackend.Entity;



import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;


import lombok.*;
import lombok.experimental.FieldDefaults;


import java.util.*;


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
  private long  id_ins;
  private int Duration;
  private Date StartDate;
  private Date EndDate;
  private boolean InsuranceStatus;

  @Enumerated(EnumType.STRING)
  InsuranceType typeinsurance;


  @OneToMany(mappedBy = "insurance", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonIgnore
  private List<Account> accounts = new ArrayList<>();


  @OneToMany(cascade = CascadeType.ALL, mappedBy="insurance",fetch = FetchType.EAGER)
  @JsonManagedReference
  private Set<Pack> packs;



}
