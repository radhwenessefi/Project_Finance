package tn.esprit.projectbackend.Controller;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.projectbackend.Entity.Portfolio;
import tn.esprit.projectbackend.Entity.Pridect;
import tn.esprit.projectbackend.Service.IPortfolioService;
import org.springframework.http.ResponseEntity;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@CrossOrigin("http://localhost:4200/")
@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/portfolio")
public class PortfolioResController {
    IPortfolioService portfolioService;

    @GetMapping("/get-all-portfolios")
    public List<Portfolio> getAllPortfolio(){
        List<Portfolio> listAllPortfolios = portfolioService.getAllPortfolio();
        return listAllPortfolios;
    }
    @GetMapping("/get-portfolios/{idPortfolio}")
    public Portfolio  getPortfolio(@PathVariable("idPortfolio") Long blId){
        Portfolio portfolio = portfolioService.getPortfolio(blId);
        return portfolio;
    }
    @PostMapping("/add-portfolio")
    public  Portfolio addPortfolio(@RequestBody Portfolio p){
        Portfolio portfolio = portfolioService.addPortfolio(p);
        return  portfolio;
    }
    @DeleteMapping("/remove-portfolio/{portfolio-id}")
    public void  removePortfolio(@PathVariable("portfolio-id") Long blId){
        portfolioService.removePortfolio(blId);
    }
    @PutMapping("/modify-portfolio")
    public Portfolio modifyPortfolio(@RequestBody Portfolio b){
        Portfolio portfolio = portfolioService.modifyPortfolio(b);
        return portfolio ;
    }
    @CrossOrigin("http://localhost:4200/")
    @PostMapping("/add-portfolio-fromAPI")
    public ResponseEntity<String> addPortfolioFromAPI() {
        try {
            List<Portfolio> portfolioList = portfolioService.fetchDataFromApi();

            for (Portfolio portfolio : portfolioList) {
                portfolioService.addPortfolio(portfolio);
            }

            return ResponseEntity.ok("Portfolios added successfully");
        } catch (Exception e) {
            // Handle exceptions here
            return ResponseEntity.status(500).body("Failed to add portfolios. Error: " + e.getMessage());
        }
    }

@GetMapping("/get-clustred-portfolios")
  public List<Map<Long, Portfolio>> getPortfolioByCluster() {
    List<Map<Long, Portfolio>> listAllPortfolios = portfolioService.getPortfolioByCluster();
    return listAllPortfolios;
}
    @PostMapping("/get-prediction-portfolios/{portfolio-id}")
    public Float pridectionPortFolio(@PathVariable("portfolio-id") Long blId){
        log.info("test"+blId);
        Float pridiction = portfolioService.predictionForVolume(blId);
        return pridiction;
    }
}
