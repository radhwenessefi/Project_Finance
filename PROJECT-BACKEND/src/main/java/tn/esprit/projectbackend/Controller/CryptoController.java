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
import tn.esprit.projectbackend.Service.ICryptoService;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/crypto")
public class CryptoController {

    private final ICryptoService cryptoService;

    // This is your messaging template for WebSocket communication (if needed)
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    // Endpoint for getting real-time crypto data
    @GetMapping("/{symbol}/{range}/{datestart}/{dateend}")
    public ApiResponseForex getCrypto(@PathVariable String symbol,
                                      @PathVariable String range,
                                      @PathVariable String datestart,
                                      @PathVariable String dateend) {
        log.info("Fetching crypto data for symbol: {}, range: {}, start date: {}, end date: {}",
                symbol, range, datestart, dateend);

        ApiResponseForex response = cryptoService.getRealTimeCrypto(symbol, range, datestart, dateend);

        // You can use SimpMessagingTemplate to send the response to clients if needed
        // messagingTemplate.convertAndSend("/topic/crypto", response);

        return response;
    }





    // Optionally, you can define additional endpoints or WebSocket broadcasting logic
}
