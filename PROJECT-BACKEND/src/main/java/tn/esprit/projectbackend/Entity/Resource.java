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
public class Resource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String resourceTitle;
    String resourceType;
    String url;
}
