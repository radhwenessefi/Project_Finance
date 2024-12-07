package tn.esprit.projectbackend.Entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate; // Pour gérer les dates modernes

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TrainingSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String sessionTitle;
    String sessionType;
    String location;
    int maxParticipants;

    // Ajout d'un champ pour représenter la date de la session
    LocalDate sessionDate;
}
