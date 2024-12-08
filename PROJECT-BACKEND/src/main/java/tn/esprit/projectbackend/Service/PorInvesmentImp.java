package tn.esprit.projectbackend.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.projectbackend.Entity.Portfolio;
import tn.esprit.projectbackend.Entity.PortfolioInvestment;
import tn.esprit.projectbackend.Entity.User;
import tn.esprit.projectbackend.Repository.ProInvestmentRepository;
import tn.esprit.projectbackend.Repository.PortfolioRepository;
import tn.esprit.projectbackend.Repository.UserRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
@Slf4j
public class PorInvesmentImp implements IProInvestment {
    @Autowired
    private ProInvestmentRepository proInvestmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PortfolioRepository portfolioRepository;

    public void addPortfolioInvestment(PortfolioInvestment p, Long userId, Long cluster_label) {
        try {
            // Fetch portfolios by cluster_label
            List<Portfolio> portfolioList = portfolioRepository.findByClusterLabels(cluster_label);
            log.info("Fetched portfolios: " + portfolioList);

            // Fetch the user
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User with ID " + userId + " not found"));

            // Set the user to the investment
            p.setUsersportfolio(user);
            p.setClusterLabels(cluster_label);

            // Convert List to Set to match entity expectations
            Set<Portfolio> portfolioSet = new HashSet<>(portfolioList); // Conversion instead of casting
            p.setPortfolios(portfolioSet);

            // Check for existing portfolio investments for the user
            List<PortfolioInvestment> portfolioInvestmentsUser = proInvestmentRepository.findByUsersportfolio(user);
            List<PortfolioInvestment> portfolioInvestmentsCluster = new ArrayList<>();
            for (Portfolio portfolio : portfolioSet) {
                portfolioInvestmentsCluster.addAll(proInvestmentRepository.findByPortfolios(portfolio));
            }

            if (!portfolioInvestmentsUser.isEmpty() && !portfolioInvestmentsCluster.isEmpty()) {
                // Update existing investment
                PortfolioInvestment existingInvestment = portfolioInvestmentsCluster.get(0); // Modify logic as per requirement
                existingInvestment.setAmount(p.getAmount() + existingInvestment.getAmount());
                proInvestmentRepository.save(existingInvestment);
            } else {
                // Save new investment
                proInvestmentRepository.save(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error while adding portfolio investment: " + e.getMessage());
        }
    }


    public void closeOrder(Long investId){
        proInvestmentRepository.deleteById(investId);
    }
    //    public void sendEmail(String to, String subject, String body){
//
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setFrom("essefi.radhwen@gmail.com");
//        message.setTo(to);
//        message.setSubject(subject);
//        message.setText(body);
//        mailSender.send(message);
//
//    }
    public List<PortfolioInvestment> getAllInvestment(){
        return proInvestmentRepository.findAll();
    }
    public PortfolioInvestment updateOrder(PortfolioInvestment portfolioInvestment) {
        return proInvestmentRepository.save(portfolioInvestment);
    }

}
