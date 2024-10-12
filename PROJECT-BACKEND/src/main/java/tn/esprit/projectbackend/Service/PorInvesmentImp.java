package tn.esprit.projectbackend.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.projectbackend.Entity.Portfolio;
import tn.esprit.projectbackend.Entity.PortfolioInvestment;
import tn.esprit.projectbackend.Repository.ProInvestmentRepository;
import tn.esprit.projectbackend.Repository.PortfolioRepository;

import java.util.List;
@Service
@AllArgsConstructor
@Slf4j
public class PorInvesmentImp implements IProInvestment {
    @Autowired
    private ProInvestmentRepository proInvestmentRepository;



    @Autowired
    private PortfolioRepository portfolioRepository;

    public void addPortfolioInvestment(PortfolioInvestment p,Long userId, Long portfolioId) {
//        try {
//
//            Portfolio portfolio = portfolioRepository.findById(portfolioId).get();
//            log.info("aaaaaaaaaaaaaaaaaaaa"+portfolio);
//            User user1 = userRepository.findById(userId).get();
//
//            p.setUsersportfolio(user1);
//            p.setPortfolios(portfolio);
//
//            List<PortfolioInvestment> portfolioInvestmentsuser = proInvestmentRepository.findByUsersportfolio(p.getUsersportfolio());
//            List<PortfolioInvestment> portfolioInvestments = proInvestmentRepository.findByPortfolios(p.getPortfolios());
//            if (!portfolioInvestmentsuser.isEmpty() && !portfolioInvestments.isEmpty()) {
//                PortfolioInvestment portfolioInvestment = portfolioInvestments.get(0); // Assuming you want to use the first result
//                portfolioInvestment.setAmount(p.getAmount() + portfolioInvestment.getAmount());
//                proInvestmentRepository.save(portfolioInvestment);
//            } else {
//                proInvestmentRepository.save(p);
//            }
//        } catch (Exception e) {
//
//            e.printStackTrace();
//        }
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
//    }
    public List<PortfolioInvestment> getAllInvestment(){
        return proInvestmentRepository.findAll();
    }
    public PortfolioInvestment updateOrder(PortfolioInvestment portfolioInvestment) {
        return proInvestmentRepository.save(portfolioInvestment);
    }

}
