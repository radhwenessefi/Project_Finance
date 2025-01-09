package tn.esprit.projectbackend.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.projectbackend.Entity.SentimentAnalysis;
import tn.esprit.projectbackend.Repository.SentimentAnalysisRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class SentimentAnalysisService implements ISentimentAnalysisService {

    @Autowired
    private SentimentAnalysisRepository sentimentAnalysisRepository;

    // Définition des mots et de leurs scores associés
    private final Map<String, String> sentimentMap = Map.of(
            "satisfait", "Positive",
            "très satisfait", "Very Positive",
            "triste", "Negative",
            "très triste", "Very Negative"
    );

    private final Map<String, Double> scoreMap = Map.of(
            "satisfait", 0.5,
            "très satisfait", 1.0,
            "triste", -0.5,
            "très triste", -1.0
    );

    @Override
    public SentimentAnalysis analyzeAndSaveSentiment(String text) {
        // Nettoyer le texte en supprimant les guillemets et les espaces avant et après
        String cleanedText = text.replace("\"", "").trim();

        // Log pour vérifier le texte nettoyé
        System.out.println("Texte nettoyé : " + cleanedText); // Vérification de la valeur

        String sentimentResult = sentimentMap.getOrDefault(cleanedText, "Unknown");
        double sentimentScore = scoreMap.getOrDefault(cleanedText, 0.0); // 0.0 si le texte ne correspond à aucun mot

        // Log pour vérifier le résultat du sentiment
        System.out.println("Résultat du sentiment : " + sentimentResult);
        System.out.println("Score du sentiment : " + sentimentScore);

        LocalDateTime analysisDate = LocalDateTime.now();

        SentimentAnalysis sentimentAnalysis = new SentimentAnalysis();
        sentimentAnalysis.setText(cleanedText);
        sentimentAnalysis.setSentimentResult(sentimentResult);
        sentimentAnalysis.setSentimentScore(sentimentScore);
        sentimentAnalysis.setDetailedAnalysis("Analyse détaillée basée par choix du sentiment .");
        sentimentAnalysis.setLanguage("fr");
        sentimentAnalysis.setAnalysisDate(analysisDate);
        sentimentAnalysis.setOriginalText(text); // Optionnel

        return sentimentAnalysisRepository.save(sentimentAnalysis);
    }

    @Override
    public List<SentimentAnalysis> getAllSentiments() {
        return sentimentAnalysisRepository.findAll();
    }

    @Override
    public Optional<SentimentAnalysis> getSentimentById(Long id) {
        return sentimentAnalysisRepository.findById(id);
    }

    @Override
    public void deleteSentimentById(Long id) {
        sentimentAnalysisRepository.deleteById(id);
    }
}
