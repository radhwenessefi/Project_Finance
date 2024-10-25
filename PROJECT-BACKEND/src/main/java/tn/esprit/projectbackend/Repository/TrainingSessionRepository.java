package tn.esprit.projectbackend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.projectbackend.Entity.TrainingSession;

public interface TrainingSessionRepository extends JpaRepository<TrainingSession, Long> {
}
