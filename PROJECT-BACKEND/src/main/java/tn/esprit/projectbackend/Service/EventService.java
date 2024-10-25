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

    public Event createEvent(Event event) {
        validateEvent(event);  // Appel des contrôles de saisie avant la création
        return eventRepository.save(event);
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Optional<Event> getEventById(Long id) {
        return eventRepository.findById(id);
    }

    public Event updateEvent(Long id, Event updatedEvent) {
        validateEvent(updatedEvent);  // Appel des contrôles de saisie avant la mise à jour
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

    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }

    // --- Méthodes de validation ---

    // Valider les champs d'un événement avant la sauvegarde ou la mise à jour
    private void validateEvent(Event event) {
        // Validation du titre de l'événement (non vide et au moins 3 caractères)
        if (event.getEventTitle() == null || event.getEventTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Le titre de l'événement est obligatoire.");
        }

        if (event.getEventTitle().length() < 3) {
            throw new IllegalArgumentException("Le titre de l'événement doit comporter au moins 3 caractères.");
        }

        // Validation du type d'événement (non vide)
        if (event.getEventType() == null) {
            throw new IllegalArgumentException("Le type de l'événement est obligatoire.");
        }



        // Validation du lieu de l'événement (non vide et longueur max 100 caractères)
        if (event.getLocation() == null || event.getLocation().trim().isEmpty()) {
            throw new IllegalArgumentException("Le lieu de l'événement est obligatoire.");
        }

        if (event.getLocation().length() > 100) {
            throw new IllegalArgumentException("Le lieu de l'événement ne doit pas dépasser 100 caractères.");
        }

        // Ici, tu pourrais ajouter une validation pour t'assurer que l'événement a une date future, si applicable.
        // if (event.getEventDate().isBefore(LocalDate.now())) {
        //     throw new IllegalArgumentException("La date de l'événement doit être dans le futur.");
        // }
    }
}
