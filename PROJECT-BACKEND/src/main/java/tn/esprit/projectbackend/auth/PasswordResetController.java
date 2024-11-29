package tn.esprit.projectbackend.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.projectbackend.Service.PasswordResetService;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class PasswordResetController {

    private final PasswordResetService passwordResetService;

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestParam String email) {
        String response = passwordResetService.createPasswordResetToken(email);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Map<String, String>> resetPassword(@RequestParam String token, @RequestParam String newPassword) {
        String response = passwordResetService.resetPassword(token, newPassword);
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("message", response);
        return ResponseEntity.ok(responseBody);
    }


    @GetMapping("/reset-password")
    public ResponseEntity<String> showResetPasswordMessage(@RequestParam String token) {
        log.info("Token received for reset: {}", token);
        String responseMessage = "\n\n\n"
                + "      Votre code de réinitialisation de mot de      \n"
                + "      passe est :                                   \n"
                + "                                                    \n"
                + "                  '" + token + "'                   \n"
                ;
        return ResponseEntity.ok(responseMessage);
    }
}
