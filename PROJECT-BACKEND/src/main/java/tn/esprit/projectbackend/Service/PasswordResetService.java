package tn.esprit.projectbackend.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import tn.esprit.projectbackend.Entity.AppUser;
import tn.esprit.projectbackend.Entity.PasswordResetToken;
import tn.esprit.projectbackend.Repository.AppUserRepos;
import tn.esprit.projectbackend.Repository.PasswordResetTokenRepository;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PasswordResetService {

    private final AppUserRepos userRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final EmailService emailService;

    @Transactional
    public String createPasswordResetToken(String email) {
        AppUser user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("User not found"));

        String token = UUID.randomUUID().toString();
        PasswordResetToken resetToken = PasswordResetToken.builder()
                .token(token)
                .user(user)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(30))
                .build();

        passwordResetTokenRepository.save(resetToken);

        String resetLink = "http://localhost:8089/stimulateur/auth/reset-password?token=" + token;
        String emailMessage = "Cliquez sur le lien ci-dessous pour réinitialiser votre mot de passe : " + resetLink;

        try {
            emailService.sendVerificationEmail(user.getEmail(), "Réinitialisation de mot de passe", emailMessage);
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to send password reset email";
        }

        return "Password reset email sent";
    }

    @Transactional
    public String resetPassword(String token, String newPassword) {
        PasswordResetToken resetToken = passwordResetTokenRepository.findByToken(token)
                .orElseThrow(() -> new IllegalStateException("Invalid token"));

        if (resetToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            return "Token expired";
        }

        AppUser user = resetToken.getUser();
        user.setMdp(new BCryptPasswordEncoder().encode(newPassword));
        userRepository.save(user);

        passwordResetTokenRepository.delete(resetToken);

        return "Password successfully reset";
    }}
