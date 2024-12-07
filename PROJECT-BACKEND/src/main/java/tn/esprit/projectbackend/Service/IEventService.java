package tn.esprit.projectbackend.Service;

import tn.esprit.projectbackend.Entity.Event;

import java.util.List;
import java.util.Optional;

public interface IEventService {

    // Créer un événement
    Event createEvent(Event event);

    // Récupérer tous les événements
    List<Event> getAllEvents();

    // Récupérer un événement par ID
    Optional<Event> getEventById(Long id);

    // Mettre à jour un événement
    Event updateEvent(Long id, Event event);

    // Supprimer un événement par ID
    void deleteEvent(Long id);
}
