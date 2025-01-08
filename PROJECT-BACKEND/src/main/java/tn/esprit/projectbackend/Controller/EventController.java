package tn.esprit.projectbackend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.projectbackend.Entity.Event;
import tn.esprit.projectbackend.Entity.EventType;
import tn.esprit.projectbackend.Entity.User;
import tn.esprit.projectbackend.Entity.UserRankingDTO;
import tn.esprit.projectbackend.Service.EventService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/events")
public class EventController {

    @Autowired
    private EventService eventService;

    // Create a new event
    @PostMapping
    public ResponseEntity<Event> createEvent(@RequestBody Event event) {
        Event createdEvent = eventService.createEvent(event);
        return ResponseEntity.ok(createdEvent);
    }

    // Generate a test event (for testing purposes)
    @PostMapping("/generate")
    public ResponseEntity<Event> generateEvent() {
        Event generatedEvent = eventService.generateEvent(
                "Crypto Trading Championship",
                EventType.TRADING_COMPETITION,
                "Compete to trade and win big!",
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(3),
                1000.0,
                10
        );
        return ResponseEntity.ok(generatedEvent);
    }

    // Get all events
    @GetMapping("/all")
    public ResponseEntity<List<Event>> getAllEvents() {
        List<Event> events = eventService.getAllEvents();
        if (events.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(events);
    }

    // Get event by ID
    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable Long id) {
        return eventService.getEventById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Update an event
    @PutMapping("/{id}")
    public ResponseEntity<Event> updateEvent(@PathVariable Long id, @RequestBody Event event) {
        Event updatedEvent = eventService.updateEvent(id, event);
        return updatedEvent != null ? ResponseEntity.ok(updatedEvent) : ResponseEntity.notFound().build();
    }

    // Delete an event
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }

    // Add a participant to an event
    @PostMapping("/{eventId}/add-participant/{userId}")
    public ResponseEntity<String> addParticipantToEvent(@PathVariable Long eventId, @PathVariable Long userId) {
        eventService.addParticipant(eventId, userId);
        return ResponseEntity.ok("Participant added successfully!");
    }

    // Get rankings for an event
    @GetMapping("/{id}/rankings")
    public ResponseEntity<List<UserRankingDTO>> getEventRankings(@PathVariable Long id) {
        List<UserRankingDTO> rankings = eventService.calculateRankings(id);
        return ResponseEntity.ok(rankings);
    }

    // Award a winner for an event
    @PostMapping("/{id}/award-winner")
    public ResponseEntity<String> awardWinner(@PathVariable Long id) {
        Optional<User> winner = eventService.awardWinner(id);
        return winner.map(user -> ResponseEntity.ok("Winner: " + user.getUsername()))
                .orElse(ResponseEntity.ok("No winner could be determined."));
    }
}
