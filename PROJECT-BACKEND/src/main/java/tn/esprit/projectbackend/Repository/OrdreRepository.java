package tn.esprit.projectbackend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.projectbackend.Entity.Ordre;

public interface OrdreRepository extends JpaRepository<Ordre, Long> {
}
