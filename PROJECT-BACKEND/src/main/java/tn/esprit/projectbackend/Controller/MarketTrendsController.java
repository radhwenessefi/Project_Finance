package tn.esprit.projectbackend.Controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.projectbackend.Entity.MarketTrendResponse;
import tn.esprit.projectbackend.Service.MarketTrendsServiceImp;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/PIDEV/actions")
public class MarketTrendsController {
    @Autowired
    private MarketTrendsServiceImp marketTrendsService;

    @GetMapping("/trends") // Endpoint pour récupérer les tendances du marché
    public MarketTrendResponse getMarketTrends() {
        return marketTrendsService.getMarketTrends();
    }
}

