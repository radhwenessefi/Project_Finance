package tn.esprit.projectbackend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tn.esprit.projectbackend.Entity.Insurance;
import tn.esprit.projectbackend.Entity.InsuranceType;
import tn.esprit.projectbackend.Entity.Portfolio;

import java.util.List;
import java.util.Map;

public interface InsuranceRepository extends JpaRepository<Insurance,Long> {

    List<Insurance> findAllByTypeinsurance (InsuranceType c);



}
