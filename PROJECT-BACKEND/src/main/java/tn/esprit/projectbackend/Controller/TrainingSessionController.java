package tn.esprit.projectbackend.Controller;

import tn.esprit.projectbackend.Entity.Resource;
import tn.esprit.projectbackend.Entity.TrainingSession;
import tn.esprit.projectbackend.Service.TrainingSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trainingsessions")
public class TrainingSessionController {

    @Autowired
    private TrainingSessionService trainingSessionService;

    @PostMapping
    public ResponseEntity<TrainingSession> createTrainingSession(@RequestBody TrainingSession session) {
        TrainingSession createdSession = trainingSessionService.createTrainingSession(session);
        return ResponseEntity.ok(createdSession);
    }

    @GetMapping
    public List<TrainingSession> getAllTrainingSessions() {
        return trainingSessionService.getAllTrainingSessions();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TrainingSession> getTrainingSessionById(@PathVariable Long id) {
        return trainingSessionService.getTrainingSessionById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<TrainingSession> updateTrainingSession(@PathVariable Long id, @RequestBody TrainingSession session) {
        TrainingSession updatedSession = trainingSessionService.updateTrainingSession(id, session);
        return updatedSession != null ? ResponseEntity.ok(updatedSession) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrainingSession(@PathVariable Long id) {
        trainingSessionService.deleteTrainingSession(id);
        return ResponseEntity.noContent().build();
    }
}
