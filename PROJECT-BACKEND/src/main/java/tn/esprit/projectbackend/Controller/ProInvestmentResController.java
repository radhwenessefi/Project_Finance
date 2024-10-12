package tn.esprit.projectbackend.Controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import tn.esprit.projectbackend.Entity.PortfolioInvestment;

import tn.esprit.projectbackend.Service.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@CrossOrigin("http://localhost:4200/")
@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/ProInvestment")
public class ProInvestmentResController {
    IProInvestment proInvestment;

    PorInvesmentImp porInvesmentImp;


    @CrossOrigin("http://localhost:4200/")
    @PostMapping("/add-portfolio-Investment/{userid}/{portfolioid}")
    public ResponseEntity<String> addPortfolio(@Valid @RequestBody PortfolioInvestment p,
                                               @PathVariable("userid") Long userId,
                                               @PathVariable("portfolioid") Long portfolioId)
    {
        log.info("The order is " + p.getOrderType());

        if (p.getOrderType().toString().equals("buy")) {
            if (p.getStopLoss() >= p.getTakeProfit()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Stop Loss cannot be greater or equals than Take Profit for a buy order.");
            } else {
                porInvesmentImp.addPortfolioInvestment(p, userId,portfolioId);
                return ResponseEntity.status(HttpStatus.OK).body("Portfolio investment added successfully.");
            }
        } else if (p.getOrderType().toString().equals("sell")) {
            if (p.getStopLoss() <= p.getTakeProfit()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Stop Loss cannot be less or equals than Take Profit for a sell order.");
            } else {
                porInvesmentImp.addPortfolioInvestment(p, userId,portfolioId);
                return ResponseEntity.ok("Portfolio investment added successfully.");
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid order type: " + p.getOrderType());
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
    @DeleteMapping("/closeorder/{order-id}")
    public void  removeProject(@PathVariable("order-id") Long orderId){
        porInvesmentImp.closeOrder(orderId);
    }
    @GetMapping("/get-all-portfolioInvesment")
    public List<PortfolioInvestment> getAllInvestment() {
        return proInvestment.getAllInvestment();
    }

    @PutMapping("/modify-order")
    public PortfolioInvestment modifyProject(@RequestBody PortfolioInvestment b){
        return   proInvestment.updateOrder(b);

    }
}
