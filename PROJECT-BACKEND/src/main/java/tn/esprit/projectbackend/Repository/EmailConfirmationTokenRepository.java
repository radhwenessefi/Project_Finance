package tn.esprit.projectbackend.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.esprit.projectbackend.Entity.AppUser;
import tn.esprit.projectbackend.Entity.EmailConfirmationToken;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface EmailConfirmationTokenRepository extends JpaRepository<EmailConfirmationToken, Long> {
    List<EmailConfirmationToken> findByAppUser(AppUser appUser);

    Optional<EmailConfirmationToken> findByToken(String token);

    EmailConfirmationToken findByUser(AppUser user);  // Use the correct field name
    @Modifying
    @Query("update EmailConfirmationToken c set c.confirmedAt = :confirmedAt where c.token = :token")
    int updateConfirmedAt(@Param("token") String token, @Param("confirmedAt") LocalDateTime confirmedAt);

    @Modifying
    void deleteByExpiredAtBefore(LocalDateTime expiryDate);

}
