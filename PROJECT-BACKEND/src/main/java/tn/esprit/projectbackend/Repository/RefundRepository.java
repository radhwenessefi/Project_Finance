package tn.esprit.projectbackend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.projectbackend.Entity.Refund;

import java.util.List;

public interface RefundRepository extends JpaRepository<Refund,Long> {
    List<Refund> findByAccountId(Long accountId);

}
