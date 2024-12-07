package tn.esprit.projectbackend.Entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate; // Import pour utiliser les dates modernes

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String eventTitle; 
    // Utilisation de l'énumération pour le type d'événement
    @Enumerated(EnumType.STRING)
    EventType eventType;

    String location;

    // Ajout d'une date pour l'événement
    LocalDate eventDate;
}
