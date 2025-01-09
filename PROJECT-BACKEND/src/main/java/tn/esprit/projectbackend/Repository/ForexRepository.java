package tn.esprit.projectbackend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.projectbackend.Entity.Forex;

public interface ForexRepository extends JpaRepository<Forex, Long> {
    Forex findByPair(String pair);
}
