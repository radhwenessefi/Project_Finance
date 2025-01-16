package tn.esprit.projectbackend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tn.esprit.projectbackend.Entity.Pack;
import tn.esprit.projectbackend.Entity.Portfolio;

import java.util.List;
import java.util.Map;

public interface PackRepository extends JpaRepository<Pack,Long> {

//List<Pack> findByPriceBetween (long c,long a);

}
