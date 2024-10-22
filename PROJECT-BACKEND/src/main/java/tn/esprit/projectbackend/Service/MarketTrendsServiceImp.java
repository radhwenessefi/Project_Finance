package tn.esprit.projectbackend.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tn.esprit.projectbackend.Entity.Actions;
import tn.esprit.projectbackend.Entity.ApiResponse;
import tn.esprit.projectbackend.Entity.MarketTrendResponse;
import tn.esprit.projectbackend.Entity.TimeSeriesData;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class MarketTrendsServiceImp {

    @Autowired
    private RestTemplate restTemplate;

    private final String apiUrl = "https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY&apikey=YLAO8BBIT6OEI9GD";

    public MarketTrendResponse getMarketTrends() {
        List<Actions> actionsList = new ArrayList<>();
        List<String> symbols = List.of("AAPL", "GOOGL", "MSFT", "AMZN", "TSLA", "X", "BABA");



        for (String symbol : symbols) {
            String url = String.format("%s&symbol=%s&interval=1min", apiUrl, symbol);
            ApiResponse response = restTemplate.getForObject(url, ApiResponse.class);

            if (response != null && response.getTimeSeriesData() != null && !response.getTimeSeriesData().isEmpty()) {
                String lastTimestamp = response.getTimeSeriesData().keySet().iterator().next();
                TimeSeriesData lastData = response.getTimeSeriesData().get(lastTimestamp);


                Actions action = new Actions();
                action.setSymbol(symbol);
                action.setCurrentPrice(Double.parseDouble(lastData.getClose()));
                action.setOpenPrice(Double.parseDouble(lastData.getOpen()));
                action.setDayHigh(Double.parseDouble(lastData.getHigh()));
                action.setDayLow(Double.parseDouble(lastData.getLow()));
                actionsList.add(action);
            }
        }


        actionsList.sort(Comparator.comparingDouble(Actions::getCurrentPrice));


        List<Actions> topPerformers = actionsList.subList(Math.max(actionsList.size() - 3, 0), actionsList.size()); // Top 3
        List<Actions> worstPerformers = actionsList.subList(0, Math.min(3, actionsList.size())); // Worst 3

        return new MarketTrendResponse(topPerformers, worstPerformers);
    }
}
