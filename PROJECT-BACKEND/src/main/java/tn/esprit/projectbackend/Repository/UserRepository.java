package tn.esprit.projectbackend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.projectbackend.Entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
