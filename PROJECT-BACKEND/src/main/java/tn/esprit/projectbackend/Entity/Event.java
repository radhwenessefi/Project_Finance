package tn.esprit.projectbackend.Entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Set;

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

    @Enumerated(EnumType.STRING)
    EventType eventType; // e.g., TRADING_COMPETITION

    String description;

    LocalDateTime eventStartDate;

    LocalDateTime eventEndDate;

    Integer maxParticipants;

    @ManyToMany
    Set<User> participants; // Users participating in the event

    Double prizePool; // Total prize pool for the competition
}
