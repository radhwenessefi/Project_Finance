package tn.esprit.projectbackend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.projectbackend.Service.InMemoryTokenBlacklist;

@RestController
@RequestMapping("/api/tokens")
public class TokenBlacklistController {

    private final InMemoryTokenBlacklist tokenBlacklist;

    @Autowired
    public TokenBlacklistController(InMemoryTokenBlacklist tokenBlacklist) {
        this.tokenBlacklist = tokenBlacklist;
    }

    // Endpoint pour ajouter un token à la liste noire
    @PostMapping("/blacklist")
    public ResponseEntity<String> addToBlacklist(@RequestBody String token) {
        tokenBlacklist.addToBlacklist(token);
        return ResponseEntity.ok("Token ajouté à la liste noire.");
    }

    // Endpoint pour vérifier si un token est dans la liste noire
    @GetMapping("/isBlacklisted")
    public ResponseEntity<Boolean> isBlacklisted(@RequestParam String token) {
        boolean isBlacklisted = tokenBlacklist.isBlacklisted(token);
        return ResponseEntity.ok(isBlacklisted);
    }
}
