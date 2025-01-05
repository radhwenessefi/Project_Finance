package tn.esprit.projectbackend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.projectbackend.Entity.Asset;

public interface AssetRepository extends JpaRepository<Asset, Long> {
}
