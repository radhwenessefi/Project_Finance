package tn.esprit.projectbackend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.projectbackend.Entity.Portfolio;
import tn.esprit.projectbackend.Entity.PortfolioInvestment;
import tn.esprit.projectbackend.Entity.User;

import java.util.List;

public interface ProInvestmentRepository extends JpaRepository<PortfolioInvestment,Long> {
    List<PortfolioInvestment> findByUsersportfolio(User user);
    List<PortfolioInvestment> findByPortfolios(Portfolio portfolio);

}
