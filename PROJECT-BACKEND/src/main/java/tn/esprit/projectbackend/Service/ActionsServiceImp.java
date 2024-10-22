package tn.esprit.projectbackend.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import tn.esprit.projectbackend.Entity.Actions;
import tn.esprit.projectbackend.Entity.ApiResponse;
import tn.esprit.projectbackend.Entity.TimeSeriesData;
import tn.esprit.projectbackend.Repository.ActionsRepository;

@Service
public class ActionsServiceImp implements IActionsService {

    @Autowired
    private ActionsRepository actionsRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;  // Injecter le SimpMessagingTemplate

    private final String apiUrl = "https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY";
    private final String apiKey = "YLAO8BBIT6OEI9GD";  // Insère ta clé d'API ici

    @Override
    @Scheduled(fixedRate = 10000)  // Exécuter la méthode toutes les 10 secondes
    public Actions getRealTimeAction(String symbol) {
        String url = String.format("%s&symbol=%s&apikey=%s&interval=1min", apiUrl, symbol, apiKey);
        ApiResponse response = restTemplate.getForObject(url, ApiResponse.class);

        if (response != null && response.getTimeSeriesData() != null && !response.getTimeSeriesData().isEmpty()) {
            String lastTimestamp = response.getTimeSeriesData().keySet().iterator().next();
            TimeSeriesData lastData = response.getTimeSeriesData().get(lastTimestamp);
            Actions action = new Actions();
            action.setSymbol(symbol);
            action.setCompanyName(symbol);
            action.setCurrentPrice(Double.parseDouble(lastData.getClose()));
            action.setOpenPrice(Double.parseDouble(lastData.getOpen()));
            action.setDayHigh(Double.parseDouble(lastData.getHigh()));
            action.setDayLow(Double.parseDouble(lastData.getLow()));

            // Envoyer les données via WebSocket à tous les clients connectés
            messagingTemplate.convertAndSend("/topic/actions", action);
            return action;
        } else {
            throw new RuntimeException("Erreur lors de la récupération de l'action : " + symbol);
        }
    }
    @Scheduled(fixedRate = 10000) // Exemple : toutes les 10 secondes
    public void sendDataToClients() {
        String data = "Données en temps réel"; // Récupérer les données réelles ici
        messagingTemplate.convertAndSend("/topic/actions", data);
    }




}



