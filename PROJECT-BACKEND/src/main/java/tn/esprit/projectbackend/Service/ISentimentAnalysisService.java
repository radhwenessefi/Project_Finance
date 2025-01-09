package tn.esprit.projectbackend.Service;

import tn.esprit.projectbackend.Entity.SentimentAnalysis;

import java.util.List;
import java.util.Optional;

public interface ISentimentAnalysisService {
    SentimentAnalysis analyzeAndSaveSentiment(String text);
    List<SentimentAnalysis> getAllSentiments();
    Optional<SentimentAnalysis> getSentimentById(Long id);
    void deleteSentimentById(Long id);
}
