package tn.esprit.projectbackend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.projectbackend.Entity.SentimentAnalysis;
import tn.esprit.projectbackend.Service.ISentimentAnalysisService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/sentiments")
public class SentimentAnalysisController {

    @Autowired
    private ISentimentAnalysisService sentimentAnalysisService;

    @PostMapping("/analyze")
    public ResponseEntity<SentimentAnalysis> analyzeSentiment(@RequestBody String text) {
        SentimentAnalysis sentimentAnalysis = sentimentAnalysisService.analyzeAndSaveSentiment(text);
        return ResponseEntity.ok(sentimentAnalysis);
    }

    @GetMapping
    public List<SentimentAnalysis> getAllSentiments() {
        return sentimentAnalysisService.getAllSentiments();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SentimentAnalysis> getSentimentById(@PathVariable Long id) {
        Optional<SentimentAnalysis> sentimentAnalysis = sentimentAnalysisService.getSentimentById(id);
        return sentimentAnalysis.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSentimentById(@PathVariable Long id) {
        sentimentAnalysisService.deleteSentimentById(id);
        return ResponseEntity.noContent().build();
    }
}
