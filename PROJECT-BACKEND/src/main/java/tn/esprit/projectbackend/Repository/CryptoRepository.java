package tn.esprit.projectbackend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.projectbackend.Entity.Crypto;

public interface CryptoRepository extends JpaRepository<Crypto, Long> {
    Crypto findBySymbol(String symbol);
}
