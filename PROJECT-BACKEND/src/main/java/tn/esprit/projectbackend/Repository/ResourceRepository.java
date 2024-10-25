package tn.esprit.projectbackend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.projectbackend.Entity.Resource;

public interface ResourceRepository extends JpaRepository<Resource, Long> {
}
