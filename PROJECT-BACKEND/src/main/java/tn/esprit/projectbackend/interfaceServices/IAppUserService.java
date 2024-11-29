package tn.esprit.projectbackend.interfaceServices;



import tn.esprit.projectbackend.Entity.AppUser;
import tn.esprit.projectbackend.dto.AdminUserDto;
import tn.esprit.projectbackend.enums.Role;

import java.util.List;

public interface IAppUserService {

    public String createAdminUser(AdminUserDto input);

    public List<AppUser> readAllAppUser();

    public AppUser readAppUser(Long idUser);

    public String updateAdminUser(Long userId, AdminUserDto input);

    public void deleteAppUser(Long idUser);

    // Nouvelle méthode pour changer le rôle d'un utilisateur
    AppUser changeUserRole(Long userId, Role newRole);
}
