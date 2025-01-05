package tn.esprit.projectbackend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.projectbackend.Entity.Tradee;
import tn.esprit.projectbackend.Repository.TradeeRepository;

import java.util.List;

@RestController
@RequestMapping("/api/trades")
public class TradeController {

    @Autowired
    private TradeeRepository tradeeRepository;

    // Create a new trade
    @PostMapping
    public ResponseEntity<Tradee> createTrade(@RequestBody Tradee tradee) {
        Tradee createdTrade = tradeeRepository.save(tradee);
        return ResponseEntity.ok(createdTrade);
    }

    // Get all trades
    @GetMapping("/all")
    public ResponseEntity<List<Tradee>> getAllTrades() {
        List<Tradee> trades = tradeeRepository.findAll();
        return ResponseEntity.ok(trades);
    }
}
