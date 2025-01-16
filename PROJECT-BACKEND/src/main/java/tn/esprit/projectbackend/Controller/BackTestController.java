package tn.esprit.projectbackend.Controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.projectbackend.Service.BackTestFlask;

@CrossOrigin("http://localhost:4200/")
@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/api/backtest")
public class BackTestController {
    @Autowired
    private BackTestFlask backTestFlask;

    @GetMapping("/run")
    public String runBacktest(
            @RequestParam String symbol,
            @RequestParam int cash,
            @RequestParam float margin,
            @RequestParam int stratNum) {

        // Appeler le service pour consommer l'API Flask
        return backTestFlask.callFlaskApi(symbol, cash, margin, stratNum);
    }
}
