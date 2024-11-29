package tn.esprit.projectbackend.Service;


import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.projectbackend.Entity.AppUser;
import tn.esprit.projectbackend.Repository.AppUserRepos;
import tn.esprit.projectbackend.dto.AdminUserDto;
import tn.esprit.projectbackend.enums.Role;
import tn.esprit.projectbackend.interfaceServices.IAppUserService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AppUserServiceImpl implements IAppUserService {

    private final AppUserRepos userRepos;
    private final PasswordEncoder passwordEncoder;

    public String createAdminUser(AdminUserDto input) {
        try {
            if (userRepos.existsByEmail(input.getEmail())) {
                return "Email déjà utilisé";
            }

            AppUser user = AppUser.builder()
                    .nom(input.getNom())
                    .prenom(input.getPrenom())
                    .address(input.getAddress())
                    .email(input.getEmail())
                    .mdp(passwordEncoder.encode(input.getPassword()))
                    .role(Role.ADMIN)
                    .enabled(input.isEnabled())
                    .build();

            userRepos.save(user);

            // Create SQL Server user
            String sqlServerLoginName = input.getEmail(); // Use email as login name
            String sqlServerPassword = "temporary_password"; // Generate or use a default password
            String databaseName = "YourDatabase"; // Update with your database name


            return "Admin account created successfully";
        } catch (Exception e) {
            e.printStackTrace();
            return "An error occurred during registration";
        }
    }

    @Override
    public List<AppUser> readAllAppUser() {
        return userRepos.findAll();
    }

    public List<AdminUserDto> getAllAdminUsers() {
        return userRepos.findAll().stream()
                .filter(user -> user.getRole() == Role.ADMIN)
                .map(user -> new AdminUserDto(
                        user.getId(),
                        user.getNom(),
                        user.getPrenom(),
                        user.getAddress(),
                        user.getEmail(),
                        user.getRole(),
                        user.getMdp(),
                        user.isEnabled()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public AppUser readAppUser(Long idUser) {
        return userRepos.findById(idUser).orElse(null);
    }

    public String updateAdminUser(Long userId, AdminUserDto input) {
        try {
            AppUser user = userRepos.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));

            if (!user.getEmail().equals(input.getEmail()) && userRepos.existsByEmail(input.getEmail())) {
                return "Email déjà utilisé";
            }

            user.setNom(input.getNom());
            user.setPrenom(input.getPrenom());
            user.setAddress(input.getAddress());
            user.setEmail(input.getEmail());
            if (input.getPassword() != null && !input.getPassword().isEmpty()) {
                user.setMdp(passwordEncoder.encode(input.getPassword()));
            }
            user.setEnabled(input.isEnabled());
            userRepos.save(user);

            // Update SQL Server user if needed
            // You can add logic here to handle updates in SQL Server if necessary

            return "Admin account updated successfully";
        } catch (Exception e) {
            e.printStackTrace();
            return "An error occurred during update";
        }
    }

    @Override
    public void deleteAppUser(Long idUser) {
        userRepos.deleteById(idUser);
        // Optionally, handle SQL Server user deletion if needed
    }

    @Transactional
    public AppUser changeUserRole(Long userId, Role newRole) {
        if (newRole != Role.ADMIN && newRole != Role.USER) {
            throw new IllegalArgumentException("Invalid role: " + newRole);
        }

        Optional<AppUser> optionalUser = userRepos.findById(userId);
        if (optionalUser.isPresent()) {
            AppUser user = optionalUser.get();
            user.setRole(newRole);
            AppUser updatedUser = userRepos.save(user);

            // Optionally, update role permissions in SQL Server if needed

            return updatedUser;
        }
        throw new IllegalArgumentException("User not found with id: " + userId);
    }
}
