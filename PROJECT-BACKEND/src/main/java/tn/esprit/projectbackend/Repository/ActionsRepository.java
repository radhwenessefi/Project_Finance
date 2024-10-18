package tn.esprit.projectbackend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.projectbackend.Entity.Actions;

public interface ActionsRepository extends JpaRepository<Actions, Long> {
    Actions findBySymbol(String symbol);
}
