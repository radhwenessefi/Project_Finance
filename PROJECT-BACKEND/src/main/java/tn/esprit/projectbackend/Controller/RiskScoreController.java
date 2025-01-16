package tn.esprit.projectbackend.Controller;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.projectbackend.Entity.Account;
import tn.esprit.projectbackend.Repository.AccountRepository;
import tn.esprit.projectbackend.Service.RiskScoreService;

@CrossOrigin("http://localhost:4200/")
@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/Risk")
public class RiskScoreController {

    private final RiskScoreService riskScoreService;
    private final AccountRepository accountRepository;

    @GetMapping("/score/{accountId}")
    public ResponseEntity<String> getRiskScore(@PathVariable Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        String riskScore = riskScoreService.calculateRiskScore(account);
        return ResponseEntity.ok(riskScore);
    }
}
