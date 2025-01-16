package tn.esprit.projectbackend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.projectbackend.Entity.Account;

public interface AccountRepository extends JpaRepository<Account,Long> {
}
