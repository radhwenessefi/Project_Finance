package tn.esprit.projectbackend.Service;


import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tn.esprit.projectbackend.Entity.AppUser;
import tn.esprit.projectbackend.Entity.EmailConfirmationToken;
import tn.esprit.projectbackend.Repository.AppUserRepos;
import tn.esprit.projectbackend.auth.AuthenticationResponse;
import tn.esprit.projectbackend.dto.LoginUserDto;
import tn.esprit.projectbackend.dto.RegisterUserDto;
import tn.esprit.projectbackend.enums.Role;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AppUserRepos userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final EmailConfirmationTokenService emailConfirmationTokenService;
    private final EmailService emailService;

    public AuthenticationResponse signup(RegisterUserDto input) {
        try {
            if (userRepository.existsByEmail(input.getEmail())) {
                return AuthenticationResponse.builder()
                        .message("Email déjà utilisé")
                        .build();
            }

            AppUser user = AppUser.builder()
                    .nom(input.getNom())
                    .prenom(input.getPrenom())
                    .address(input.getAddress())
                    .email(input.getEmail())
                    .mdp(passwordEncoder.encode(input.getPassword()))
                    .role(Role.USER)
                    .enabled(false)  // Set isEnabled to false initially
                    .build();


// Save the user and get the saved instance
            AppUser savedUser = userRepository.save(user);

            // Créer le token de confirmation d'email

                    String token = UUID.randomUUID().toString();
            EmailConfirmationToken confirmationToken = new EmailConfirmationToken(
                    token,
                    LocalDateTime.now(),
                    LocalDateTime.now().plusMinutes(15),
                    savedUser

            );
            emailConfirmationTokenService.saveConfirmationToken(confirmationToken);

            // Envoyer l'email de confirmation
            String link = "http://localhost:8089/stimulateur/auth/confirm?token=" + token;
            String emailMessage = "Merci de vous être inscrit. Veuillez cliquer sur le lien ci-dessous pour activer votre compte: " + link;
            try {
                emailService.sendVerificationEmail(input.getEmail(), "Confirmer votre Courriel", emailMessage);
            } catch (MessagingException e) {
                e.printStackTrace();
                return AuthenticationResponse.builder()
                        .message("Failed to send verification email")
                        .build();
            }

            return AuthenticationResponse.builder()
                    .message("Registration successful. A verification email has been sent.")
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return AuthenticationResponse.builder()
                    .message("An error occurred during registration")
                    .build();
        }
    }

    public AppUser authenticate(LoginUserDto input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()
                )
        );

        return userRepository.findByEmail(input.getEmail()).orElseThrow();
    }

    public List<AppUser> allUsers() {
        List<AppUser> users = new ArrayList<>();

        userRepository.findAll().forEach(users::add);

        return users;
    }

    //logout
    public String extractTokenFromRequest(HttpServletRequest request) {
        // Get the Authorization header from the request
        String authorizationHeader = request.getHeader("Authorization");

        // Check if the Authorization header is not null and starts with "Bearer "
        if (StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
            // Extract the JWT token (remove "Bearer " prefix)
            return authorizationHeader.substring(7);
        }

        // If the Authorization header is not valid, return null
        return null;
    }
}