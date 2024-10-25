package tn.esprit.projectbackend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.projectbackend.Entity.Event;

public interface EventRepository extends JpaRepository<Event, Long> {
}
