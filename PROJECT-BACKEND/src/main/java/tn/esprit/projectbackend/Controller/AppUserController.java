package tn.esprit.projectbackend.Controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import tn.esprit.projectbackend.Entity.AppUser;
import tn.esprit.projectbackend.Service.AppUserServiceImpl;
import tn.esprit.projectbackend.dto.AdminUserDto;
import tn.esprit.projectbackend.enums.Role;

import java.util.List;

@RestController
@RequestMapping("/api/appUser")
@AllArgsConstructor
public class AppUserController {

    private final AppUserServiceImpl appUserService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    //-----------------------------------CRUD begins-----------------------------------//

    // Create AppUser
    @PostMapping("/create")
    public ResponseEntity<String> createAdmin(@RequestBody AdminUserDto adminUserDto) {
        try {
            String response = appUserService.createAdminUser(adminUserDto);
            if (response.equals("Admin account created successfully")) {
                return ResponseEntity.status(HttpStatus.OK).body("{\"message\": \"Admin account created successfully\"}");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\": \"" + response + "\"}");
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\": \"Invalid input provided\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\": \"An error occurred during registration\"}");
        }
    }

    // Get All Admin Users
    @GetMapping("/admins")
    public ResponseEntity<List<AdminUserDto>> getAllAdminUsers() {
        List<AdminUserDto> adminUserDtos = appUserService.getAllAdminUsers();
        return ResponseEntity.ok(adminUserDtos);
    }

    // Read All AppUsers
    @GetMapping("/getAllAppUsers")
    public ResponseEntity<?> readAllAppUsers() {
        try {
            List<AppUser> allAppUsers = appUserService.readAllAppUser();
            return ResponseEntity.status(HttpStatus.OK).body(allAppUsers);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\": \"Une erreur s'est produite.\"}");
        }
    }

    // Read AppUser by ID
    @GetMapping("/getAppUser/{id}")
    public ResponseEntity<?> readAppUser(@PathVariable Long id) {
        try {
            AppUser appUser = appUserService.readAppUser(id);
            if (appUser != null) {
                return ResponseEntity.status(HttpStatus.OK).body(appUser);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\": \"No AppUser found.\"}");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\": \"Une erreur s'est produite.\"}");
        }
    }

    // Update AppUser
    @PutMapping("/update")
    public ResponseEntity<String> updateAdmin(@RequestParam Long userId, @RequestBody AdminUserDto adminUserDto) {
        try {
            String response = appUserService.updateAdminUser(userId, adminUserDto);
            if (response.equals("Admin account updated successfully")) {
                return ResponseEntity.status(HttpStatus.OK).body("{\"message\": \"Admin account updated successfully\"}");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\": \"" + response + "\"}");
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\": \"Invalid input provided\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\": \"An error occurred during update\"}");
        }
    }

    // Delete AppUser by ID
    @DeleteMapping("/removeAppUser/{id}")
    public ResponseEntity<String> deleteAppUser(@PathVariable Long id) {
        try {
            appUserService.deleteAppUser(id);
            return ResponseEntity.status(HttpStatus.OK).body("{\"message\": \"AppUser supprimé avec succès\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\": \"Une erreur s'est produite.\"}");
        }
    }

    // Change User Role
    @PutMapping("/{userId}/role")
    public ResponseEntity<AppUser> changeUserRole(@PathVariable Long userId, @RequestParam Role newRole) {
        AppUser updatedUser = appUserService.changeUserRole(userId, newRole);
        return ResponseEntity.ok(updatedUser);
    }

    //-----------------------------------Activation begins-----------------------------------//

    // Activate User Account
    @PostMapping("/activate")
    public ResponseEntity<String> activateUser(@RequestParam String userId, @RequestParam String newPassword) {
        try {
            String sql = "UPDATE UserCredentials SET PlainPassword = ? WHERE Username = ?";
            int rows = jdbcTemplate.update(sql, newPassword, userId);

            if (rows > 0) {
                // Update the SQL Server login password
                String alterLoginSql = "ALTER LOGIN [" + userId + "] WITH PASSWORD = '" + newPassword + "';";
                jdbcTemplate.execute(alterLoginSql);
                return ResponseEntity.ok("{\"message\": \"User activated successfully!\"}");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\": \"User not found!\"}");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\": \"Error activating user: " + e.getMessage() + "\"}");
        }
    }
}
