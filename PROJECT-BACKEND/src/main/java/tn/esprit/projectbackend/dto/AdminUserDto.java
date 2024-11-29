package tn.esprit.projectbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tn.esprit.projectbackend.enums.Role;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminUserDto {
    private Long idUser;
    private String nom;
    private String prenom;
    private String address;
    private String email;
    private Role role;
    private String password;
    private boolean enabled;
}
