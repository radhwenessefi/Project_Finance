package tn.esprit.projectbackend.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SentimentAnalysis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le texte ne doit pas être vide.")
    private String text;

    private String sentimentResult; // Resultat de l'analyse
    private double sentimentScore;   // Score de l'analyse
    private String detailedAnalysis;  // Analyse détaillée
    private String language;          // Langue de l'analyse
    private LocalDateTime analysisDate; // Date de l'analyse
    private String originalText;      // Texte original
}
