package tn.esprit.projectbackend.Service;

import tn.esprit.projectbackend.Entity.Event;
import tn.esprit.projectbackend.Repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    // Créer un événement
    public Event createEvent(Event event) {
        validateEvent(event);  // Validation des champs avant la création
        return eventRepository.save(event);
    }

    // Récupérer tous les événements
    public List<Event> getAllEvents() {
        return eventRepository.findAll();  // Appelle findAll() de JpaRepository
    }

    // Récupérer un événement par ID
    public Optional<Event> getEventById(Long id) {
        return eventRepository.findById(id); // Appelle findById() pour un événement spécifique
    }

    // Mettre à jour un événement
    public Event updateEvent(Long id, Event updatedEvent) {
        validateEvent(updatedEvent); // Validation des champs avant la mise à jour
        Optional<Event> existingEvent = eventRepository.findById(id);
        if (existingEvent.isPresent()) {
            Event event = existingEvent.get();
            event.setEventTitle(updatedEvent.getEventTitle());
            event.setEventType(updatedEvent.getEventType());
            event.setLocation(updatedEvent.getLocation());
            return eventRepository.save(event);
        }
        return null;
    }

    // Supprimer un événement par ID
    public void deleteEvent(Long id) {
        eventRepository.deleteById(id); // Supprime l'événement par ID
    }

    // --- Méthodes de validation --- //
    private void validateEvent(Event event) {
        if (event.getEventTitle() == null || event.getEventTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Le titre de l'événement est obligatoire.");
        }
        if (event.getEventTitle().length() < 3) {
            throw new IllegalArgumentException("Le titre de l'événement doit comporter au moins 3 caractères.");
        }
        if (event.getEventType() == null) {
            throw new IllegalArgumentException("Le type de l'événement est obligatoire.");
        }
        if (event.getLocation() == null || event.getLocation().trim().isEmpty()) {
            throw new IllegalArgumentException("Le lieu de l'événement est obligatoire.");
        }
        if (event.getLocation().length() > 100) {
            throw new IllegalArgumentException("Le lieu de l'événement ne doit pas dépasser 100 caractères.");
        }
    }
}
