    package tn.esprit.projectbackend.Controller;

    import tn.esprit.projectbackend.Entity.Event;
    import tn.esprit.projectbackend.Service.EventService;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;

    import java.util.List;

    @RestController
    @RequestMapping("/api/events")
    public class EventController {

        @Autowired
        private EventService eventService;

        // Créer un événement
        @PostMapping
        public ResponseEntity<Event> createEvent(@RequestBody Event event) {
            Event createdEvent = eventService.createEvent(event);
            return ResponseEntity.ok(createdEvent);
        }

        // Récupérer tous les événements
        @GetMapping
        public ResponseEntity<List<Event>> getAllEvents() {
            List<Event> events = eventService.getAllEvents();
            if (events.isEmpty()) {
                return ResponseEntity.noContent().build(); // Retourne 204 si la liste est vide
            }
            return ResponseEntity.ok(events); // Retourne 200 avec la liste d'événements
        }

        // Récupérer un événement par ID
        @GetMapping("/{id}")
        public ResponseEntity<Event> getEventById(@PathVariable Long id) {
            return eventService.getEventById(id)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        }

        // Mettre à jour un événement
        @PutMapping("/{id}")
        public ResponseEntity<Event> updateEvent(@PathVariable Long id, @RequestBody Event event) {
            Event updatedEvent = eventService.updateEvent(id, event);
            return updatedEvent != null ? ResponseEntity.ok(updatedEvent) : ResponseEntity.notFound().build();
        }

        // Supprimer un événement par ID
        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
            eventService.deleteEvent(id);
            return ResponseEntity.noContent().build();
        }
    }
