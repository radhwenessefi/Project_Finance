package tn.esprit.projectbackend.Service;

import tn.esprit.projectbackend.Entity.TrainingSession;
import tn.esprit.projectbackend.Repository.TrainingSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TrainingSessionService {

    @Autowired
    private TrainingSessionRepository trainingSessionRepository;

    public TrainingSession createTrainingSession(TrainingSession session) {
        return trainingSessionRepository.save(session);
    }

    public List<TrainingSession> getAllTrainingSessions() {
        return trainingSessionRepository.findAll();
    }

    public Optional<TrainingSession> getTrainingSessionById(Long id) {
        return trainingSessionRepository.findById(id);
    }

    public TrainingSession updateTrainingSession(Long id, TrainingSession updatedSession) {
        Optional<TrainingSession> existingSession = trainingSessionRepository.findById(id);
        if (existingSession.isPresent()) {
            TrainingSession session = existingSession.get();
            session.setSessionTitle(updatedSession.getSessionTitle());
            session.setSessionType(updatedSession.getSessionType());
            session.setLocation(updatedSession.getLocation());
            session.setMaxParticipants(updatedSession.getMaxParticipants());
            return trainingSessionRepository.save(session);
        }
        return null; // Handle appropriately in real use case.
    }

    public void deleteTrainingSession(Long id) {
        trainingSessionRepository.deleteById(id);
    }
}
