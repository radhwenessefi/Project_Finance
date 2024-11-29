package tn.esprit.projectbackend.Service;



import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tn.esprit.projectbackend.Entity.AppUser;
import tn.esprit.projectbackend.Entity.EmailConfirmationToken;
import tn.esprit.projectbackend.Repository.AppUserRepos;
import tn.esprit.projectbackend.Repository.EmailConfirmationTokenRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailConfirmationTokenService {

    private final EmailConfirmationTokenRepository emailConfirmationTokenRepository;
    private final AppUserRepos userRepos;

    public void saveConfirmationToken(EmailConfirmationToken emailConfirmationToken) {
        emailConfirmationTokenRepository.save(emailConfirmationToken);
    }

    void deleteConfirmationToken(Long id){
        emailConfirmationTokenRepository.deleteById(id);
    }

    public Optional<EmailConfirmationToken> getByToken(String token) {
        return emailConfirmationTokenRepository.findByToken(token);
    }

    public int updateConfirmedAt(String token) {
        return emailConfirmationTokenRepository.updateConfirmedAt(token, LocalDateTime.now());
    }

    @Transactional
    public String confirmToken(String token) {
        log.info("Confirming token: {}", token);
        EmailConfirmationToken confirmationToken = emailConfirmationTokenRepository.findByToken(token)
                .orElseThrow(() -> new IllegalStateException("Token not found"));

        if (confirmationToken.getConfirmedAt() != null) {
            return "Token already confirmed";
        }

        LocalDateTime expiredAt = confirmationToken.getExpiredAt();
        if (expiredAt.isBefore(LocalDateTime.now())) {
            return "Token expired";
        }

        confirmationToken.setConfirmedAt(LocalDateTime.now());
        emailConfirmationTokenRepository.save(confirmationToken);

        AppUser user = confirmationToken.getUser();
        if (user != null) {
            user.setEnabled(true);
            userRepos.save(user);
        } else {
            throw new IllegalStateException("Client associated with the token not found");
        }

        return "Token confirmed";
    }

    @Transactional
    public void confirmUser(EmailConfirmationToken confirmationToken) {
        final AppUser user = confirmationToken.getUser();
        user.setEnabled(true);
        userRepos.save(user);  // Save the updated client with isEnabled set to true
        emailConfirmationTokenRepository.save(confirmationToken);  // Save the updated token with confirmedAt time
    }

    @Transactional
    public void deleteExpiredTokens() {
        LocalDateTime now = LocalDateTime.now();
        emailConfirmationTokenRepository.deleteByExpiredAtBefore(now);
    }
}
