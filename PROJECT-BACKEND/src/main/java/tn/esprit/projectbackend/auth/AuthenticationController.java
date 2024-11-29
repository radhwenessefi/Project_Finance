package tn.esprit.projectbackend.auth;



import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.projectbackend.Entity.AppUser;
import tn.esprit.projectbackend.Service.TokenBlacklist;
import tn.esprit.projectbackend.Service.AuthenticationService;
import tn.esprit.projectbackend.Service.EmailConfirmationTokenService;
import tn.esprit.projectbackend.Service.JwtService;
import tn.esprit.projectbackend.dto.LoginUserDto;
import tn.esprit.projectbackend.dto.RegisterUserDto;

@RequestMapping("/auth")
@RestController
@RequiredArgsConstructor
public class AuthenticationController {
    private final JwtService jwtService;
    private final AuthenticationService authenticationService;
    private final EmailConfirmationTokenService emailConfirmationTokenService;
    private final TokenBlacklist tokenBlacklist;



    @PostMapping("/signup")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterUserDto registerUserDto) {
        return ResponseEntity.ok(authenticationService.signup(registerUserDto));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDto loginUserDto) {
        AppUser authenticatedUser = authenticationService.authenticate(loginUserDto);
        String jwtToken = jwtService.generateToken(authenticatedUser, authenticatedUser.getAuthorities());
        LoginResponse loginResponse = new LoginResponse().setToken(jwtToken).setExpiresIn(jwtService.getExpirationTime());

        return ResponseEntity.ok(loginResponse);
    }

    @GetMapping("/confirm")
    public ResponseEntity<String> confirm(@RequestParam("token") String token) {
        String result = emailConfirmationTokenService.confirmToken(token);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        String token = authenticationService.extractTokenFromRequest(request);
        tokenBlacklist.addToBlacklist(token);

        // Clear any session-related data if necessary

        return ResponseEntity.ok().body("{\"message\": \"Logged out successfully\"}");
    }
}
