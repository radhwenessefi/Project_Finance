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
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Tradee>> getTradesByUserId(@PathVariable Long userId) {
        List<Tradee> trades = tradeeRepository.findByUserId(userId);
        if (trades.isEmpty()) {
            return ResponseEntity.noContent().build(); // Return 204 No Content if no trades are found
        }
        return ResponseEntity.ok(trades); // Return 200 OK with the list of trades
    }

}
