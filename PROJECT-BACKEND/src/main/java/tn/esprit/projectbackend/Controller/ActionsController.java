package tn.esprit.projectbackend.Controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.projectbackend.Entity.Actions;
import tn.esprit.projectbackend.Service.IActionsService;


@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/actions")
public class ActionsController {
/*
    private IActionsService actionsService;

    @GetMapping("/{symbol}")
    public ResponseEntity<Actions> getRealTimeAction(@PathVariable String symbol) {
        try {
            Actions action = actionsService.getRealTimeAction(symbol);
            return ResponseEntity.ok(action);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Retourner 404 si l'action n'est pas trouvée
        }
    }*/

    private IActionsService actionsService;

    // Injecter le SimpMessagingTemplate pour envoyer des messages WebSocket
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @GetMapping("/{symbol}")
    public ResponseEntity<Actions> getRealTimeAction(@PathVariable String symbol) {
        try {
            Actions action = actionsService.getRealTimeAction(symbol);

            // Envoyer les informations d'actions en temps réel via WebSocket à tous les clients connectés
            messagingTemplate.convertAndSend("/topic/actions", action);

            return ResponseEntity.ok(action);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Retourner 404 si l'action n'est pas trouvée
        }
    }
}
