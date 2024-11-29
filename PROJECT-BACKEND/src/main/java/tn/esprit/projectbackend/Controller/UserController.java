package tn.esprit.projectbackend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostMapping("/activate")
    public ResponseEntity<String> activateUser(@RequestParam String userId, @RequestParam String newPassword) {
        try {
            String sql = "UPDATE UserCredentials SET PlainPassword = ? WHERE Username = ?";
            int rows = jdbcTemplate.update(sql, newPassword, userId);

            if (rows > 0) {
                // Update the SQL Server login password
                String alterLoginSql = "ALTER LOGIN [" + userId + "] WITH PASSWORD = '" + newPassword + "';";
                jdbcTemplate.execute(alterLoginSql);
                return ResponseEntity.ok("User activated successfully!");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found!");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error activating user: " + e.getMessage());
        }
    }
}
