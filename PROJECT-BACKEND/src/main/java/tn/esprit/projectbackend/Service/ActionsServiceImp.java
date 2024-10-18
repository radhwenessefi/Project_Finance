package tn.esprit.projectbackend.Service;

import org.springframework.beans.factory.annotation.Autowired;
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

    private final String apiUrl = "https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY";
    private final String apiKey = "YLAO8BBIT6OEI9GD";  // Insère ta clé d'API ici

    @Override
    public Actions getRealTimeAction(String symbol) {
        // Construction de l'URL de l'API pour récupérer les données en temps réel
        String url = String.format("%s&symbol=%s&apikey=%s&interval=1min", apiUrl, symbol, apiKey);

        // Appel à l'API externe pour récupérer les données
        ApiResponse response = restTemplate.getForObject(url, ApiResponse.class);

        // Vérification de la réponse
        if (response != null && response.getTimeSeriesData() != null && !response.getTimeSeriesData().isEmpty()) {
            // Récupération des données du dernier enregistrement
            String lastTimestamp = response.getTimeSeriesData().keySet().iterator().next();
            TimeSeriesData lastData = response.getTimeSeriesData().get(lastTimestamp);

            // Création d'un objet Actions et mappage des attributs supplémentaires
            Actions action = new Actions();
            action.setSymbol(symbol);
            action.setCompanyName(symbol); // Le nom de l'entreprise peut être récupéré d'une autre source si disponible

            // Mapper les valeurs renvoyées par l'API dans les attributs de l'action
            action.setCurrentPrice(Double.parseDouble(lastData.getClose())); // Prix de clôture
            action.setOpenPrice(Double.parseDouble(lastData.getOpen())); // Prix d'ouverture
            action.setDayHigh(Double.parseDouble(lastData.getHigh())); // Plus haut du jour
            action.setDayLow(Double.parseDouble(lastData.getLow())); // Plus bas du jour

            // Remplir d'autres attributs si nécessaire
            // action.setPreviousClose(...); // Ce champ peut être récupéré d'une autre API ou en analysant l'historique

            return action; // Retourner l'objet Actions complet
        } else {
            throw new RuntimeException("Erreur lors de la récupération de l'action : " + symbol);
        }
    }
}
