package tn.esprit.projectbackend.Controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.projectbackend.Entity.ApiResponseForex;
import tn.esprit.projectbackend.Service.IForexService;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/forex")
public class ForexController {

    private final IForexService forexService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    // Endpoint pour obtenir les données Forex en temps réel
    @GetMapping("/{pair}/{range}/{datestart}/{dateend}")
    public ApiResponseForex getForex(@PathVariable String pair,
                                     @PathVariable String range,
                                     @PathVariable String datestart,
                                     @PathVariable String dateend) {
        log.info("Fetching forex data for pair: {}, range: {}, start date: {}, end date: {}",
                pair, range, datestart, dateend);

        ApiResponseForex response = forexService.getRealTimeForex(pair, range, datestart, dateend);

        // Optionnel : Envoyer les données via WebSocket
        // messagingTemplate.convertAndSend("/topic/forex", response);

        return response;
    }
}
