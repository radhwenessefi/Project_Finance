package tn.esprit.projectbackend.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import org.springframework.web.client.RestTemplate;
import tn.esprit.projectbackend.Entity.Portfolio;
import tn.esprit.projectbackend.Repository.PortfolioRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@PropertySource("classpath:application.properties")
public class PortfolioServiceImp implements IPortfolioService {
    PortfolioRepository portfolioRepository;
    private final RestTemplate restTemplate = new RestTemplate();
    ObjectMapper mapper = new ObjectMapper();
    //@Value("${api.url}") private String apiUrl;
    private static String apiTest="http://127.0.0.1:8000/apply_dbscan";
    private static String apiTest1="http://127.0.0.1:8000/Prediction";
    private static final Logger logger = LoggerFactory.getLogger(PortfolioServiceImp.class);
    public List<Portfolio> getAllPortfolio(){
        return portfolioRepository.findAll();
    }

    public Portfolio getPortfolio(Long portfolioId){
        return portfolioRepository.findById(portfolioId).get();
    }

    public  Portfolio addPortfolio(Portfolio b){
        return portfolioRepository.save(b);
    }
    public void removePortfolio(Long portfolioId){
        portfolioRepository.deleteById(portfolioId);
    }
    public Portfolio modifyPortfolio(Portfolio portfolio){
        return portfolioRepository.save(portfolio);
    }
    // Consumtion of the Clustring API
    public List<Portfolio> fetchDataFromApi() {
        logger.info("Raw API response: {}", apiTest);
        try {
            ResponseEntity<String> rawResponseEntity = restTemplate.getForEntity(apiTest, String.class);
            String rawResponse = rawResponseEntity.getBody();
            logger.info("Raw API response: {}", rawResponse);

            // Check if the response is an array or an object
            if (rawResponse.startsWith("[")) { // Starts with '[' -> it's a list
                List<Portfolio> portfolioList = mapper.readValue(rawResponse, new TypeReference<List<Portfolio>>() {});
                logger.info("The list of portfolio: {}", portfolioList);
                return portfolioList;
            } else { // Else it's a single object, so handle it accordingly
                Portfolio portfolio = mapper.readValue(rawResponse, Portfolio.class);
                List<Portfolio> singlePortfolioList = new ArrayList<>();
                singlePortfolioList.add(portfolio);
                logger.info("The single portfolio: {}", portfolio);
                return singlePortfolioList;
            }
        } catch (IOException e) {
            logger.error("Error fetching data from API: {}", e.getMessage(), e);
            // Handle the error as needed
            return Collections.emptyList();
        }
    }

    @Override
    public Map<Long, List<Portfolio>> getPortfolioByCluster() {
        return portfolioRepository.findAllPortfolios()
                .stream()
                .collect(Collectors.groupingBy(Portfolio::getClusterLabels));
    }





    public Float predictionForVolume(Long pid) {
        try {
            Portfolio p = portfolioRepository.findByClusterLabels(pid).get(0);
            // Convert Pridect object to JSON string with desired format
            String requestBodyJson = String.format("{\"Open\": %s, \"High\": %s, \"Low\": %s, \"Close\": %s, \"Adj_close\": %s}",
                    p.getOpen(), p.getHigh(), p.getLow(), p.getClose(), p.getAdjClose());
            // Log the JSON request body
            logger.info("Request body: {}", requestBodyJson);
            // Set headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> requestEntity = new HttpEntity<>(requestBodyJson, headers);
            // Make the API call with the JSON request body
            ResponseEntity<String> rawResponseEntity = restTemplate.postForEntity(apiTest1, requestEntity, String.class);
            String rawResponse = rawResponseEntity.getBody();
            // Log the API response
            logger.info("Raw API response: {}", rawResponse);
            // Extract the float value from the response
            //Float rawResponseValue = Float.parseFloat(rawResponse.substring(2, rawResponse.length() - 2));
            String numericPart = rawResponse.substring(3, rawResponse.length() - 4); // Adjust the substring indices
            //Parse the numeric part as a float
            float rawResponseValue = Float.parseFloat(numericPart);
            return rawResponseValue;
        } catch (Exception e) {
            logger.error("Error occurred while processing the request: {}", e.getMessage());
            return null;
        }


    }




}
