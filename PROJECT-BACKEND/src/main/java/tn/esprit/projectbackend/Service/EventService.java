package tn.esprit.projectbackend.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.projectbackend.Entity.Event;
import tn.esprit.projectbackend.Entity.EventType;
import tn.esprit.projectbackend.Entity.User;
import tn.esprit.projectbackend.Entity.UserRankingDTO;
import tn.esprit.projectbackend.Repository.EventRepository;
import tn.esprit.projectbackend.Repository.TradeeRepository;
import tn.esprit.projectbackend.Repository.UserRepository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TradeeRepository tradeeRepository;

    // Create an event
    public Event createEvent(Event event) {
        validateEvent(event);
        return eventRepository.save(event);
    }

    // Generate a pre-configured event
    public Event generateEvent(String title, EventType eventType, String description, LocalDateTime startDate, LocalDateTime endDate, Double prizePool, Integer maxParticipants) {
        Event event = new Event();
        event.setEventTitle(title);
        event.setEventType(eventType);
        event.setDescription(description);
        event.setEventStartDate(startDate);
        event.setEventEndDate(endDate);
        event.setPrizePool(prizePool);
        event.setMaxParticipants(maxParticipants);
        event.setParticipants(new HashSet<>());

        return eventRepository.save(event);
    }

    // Get all events
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    // Get event by ID
    public Optional<Event> getEventById(Long id) {
        return eventRepository.findById(id);
    }

    // Update an event
    public Event updateEvent(Long id, Event updatedEvent) {
        validateEvent(updatedEvent);
        Optional<Event> existingEvent = eventRepository.findById(id);
        if (existingEvent.isPresent()) {
            Event event = existingEvent.get();
            event.setEventTitle(updatedEvent.getEventTitle());
            event.setEventType(updatedEvent.getEventType());
            event.setDescription(updatedEvent.getDescription());
            event.setEventStartDate(updatedEvent.getEventStartDate());
            event.setEventEndDate(updatedEvent.getEventEndDate());
            event.setMaxParticipants(updatedEvent.getMaxParticipants());
            event.setPrizePool(updatedEvent.getPrizePool());
            event.setParticipants(updatedEvent.getParticipants());
            return eventRepository.save(event);
        }
        return null;
    }

    // Delete an event
    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }

    // Add a participant to an event
    public void addParticipant(Long eventId, Long userId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (event.getParticipants().size() >= event.getMaxParticipants()) {
            throw new RuntimeException("Event has reached its maximum capacity.");
        }

        event.getParticipants().add(user);
        eventRepository.save(event);
    }

    // Calculate rankings for an event
    public List<UserRankingDTO> calculateRankings(Long eventId) {
        // Retrieve the event by ID
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        // Create a list to hold the UserRankingDTO
        List<UserRankingDTO> rankings = new ArrayList<>();

        for (User participant : event.getParticipants()) {
            // Calculate profit for each user during the event's time period
            Double profit = tradeeRepository.calculateProfitForUser(
                    participant.getId(), event.getEventStartDate(), event.getEventEndDate());

            // Add the UserRankingDTO to the list
            rankings.add(new UserRankingDTO(
                    participant.getUsername(),          // User's first name
                    participant.getId(),            // User's ID
                    profit == null ? 0.0 : profit   // Profit, default to 0.0 if null
            ));
        }

        // Sort the rankings by profit in descending order
        return rankings.stream()
                .sorted(Comparator.comparingDouble(UserRankingDTO::getProfit).reversed())
                .collect(Collectors.toList());
    }




    // Award a winner for an event
    public Optional<User> awardWinner(Long eventId) {
        Optional<User> winner = determineWinner(eventId);
        winner.ifPresent(user -> {
            Event event = eventRepository.findById(eventId)
                    .orElseThrow(() -> new RuntimeException("Event not found"));

            user.setAccountBalance(user.getAccountBalance() + event.getPrizePool());
            userRepository.save(user);
        });
        return winner;
    }

    // Determine the winner based on profit
    public Optional<User> determineWinner(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        if (event.getParticipants().isEmpty()) {
            throw new RuntimeException("No participants in this event.");
        }

        User winner = null;
        double maxProfit = Double.NEGATIVE_INFINITY;

        for (User participant : event.getParticipants()) {
            Double profit = tradeeRepository.calculateProfitForUser(
                    participant.getId(), event.getEventStartDate(), event.getEventEndDate());

            if (profit != null && profit > maxProfit) {
                maxProfit = profit;
                winner = participant;
            }
        }

        return Optional.ofNullable(winner);
    }

    // Validate event fields
    private void validateEvent(Event event) {
        if (event.getEventTitle() == null || event.getEventTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Event title is required.");
        }
        if (event.getEventType() == null) {
            throw new IllegalArgumentException("Event type is required.");
        }
        if (event.getPrizePool() == null || event.getPrizePool() <= 0) {
            throw new IllegalArgumentException("Prize pool must be greater than 0.");
        }
        if (event.getMaxParticipants() == null || event.getMaxParticipants() <= 0) {
            throw new IllegalArgumentException("Maximum participants must be greater than 0.");
        }
    }
}
