package tn.esprit.projectbackend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.projectbackend.Entity.SentimentAnalysis;

public interface SentimentAnalysisRepository extends JpaRepository<SentimentAnalysis, Long> {
}
