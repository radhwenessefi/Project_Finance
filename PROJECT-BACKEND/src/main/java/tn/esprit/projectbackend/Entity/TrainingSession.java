package tn.esprit.projectbackend.Entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate; // Pour gérer les dates modernes
import java.util.List;

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

    @Enumerated(EnumType.STRING)
    SessionType sessionType;

    String location;

    int maxParticipants;

    // Ajout d'un champ pour représenter la date de la session
    LocalDate sessionDate;

    @OneToMany(mappedBy = "trainingSession", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Resource> resources; // Association with Resource
}
