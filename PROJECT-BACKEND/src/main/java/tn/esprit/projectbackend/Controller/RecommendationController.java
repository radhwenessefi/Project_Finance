package tn.esprit.projectbackend.Controller;

import tn.esprit.projectbackend.Service.PythonRecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class RecommendationController {

    @Autowired
    private PythonRecommendationService recommendationService;

    @GetMapping("/api/recommend")
    public Map<String, Object> getRecommendations(@RequestParam int userId) {
        return recommendationService.getRecommendations(userId);
    }
}
