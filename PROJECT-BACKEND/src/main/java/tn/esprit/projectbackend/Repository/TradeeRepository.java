package tn.esprit.projectbackend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.esprit.projectbackend.Entity.Tradee;

import java.time.LocalDateTime;
import java.util.List;

public interface TradeeRepository extends JpaRepository<Tradee, Long> {

    @Query("SELECT SUM(t.profit) FROM Tradee t WHERE t.user.id = :userId AND t.tradeDate BETWEEN :startDate AND :endDate")
    Double calculateProfitForUser(@Param("userId") Long userId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
    List<Tradee> findByUserId(Long userId);

}
