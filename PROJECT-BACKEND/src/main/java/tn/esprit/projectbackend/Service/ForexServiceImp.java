package tn.esprit.projectbackend.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tn.esprit.projectbackend.Entity.ApiResponseForex;
import tn.esprit.projectbackend.Repository.ForexRepository;

@Service
public class ForexServiceImp implements IForexService {

    @Autowired
    private ForexRepository forexRepository;

    @Autowired
    private RestTemplate restTemplate;

    private static final Logger logger = LoggerFactory.getLogger(ForexServiceImp.class);

    // Clé API
    private final String apiKey = "reCFP8EOKyHnFp_ooIbLlE2OMPQB93BW";

    @Override
    public ApiResponseForex getRealTimeForex(String pair, String range, String dateStart, String dateEnd) {
        // Construction de l'URL dynamique
        String url = String.format("https://api.polygon.io/v2/aggs/ticker/C:%s/range/%s/day/%s/%s?adjusted=true&sort=asc&apiKey=%s",
                pair.toUpperCase(), range, dateStart, dateEnd, apiKey);

        // Appel API
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            String jsonResponse = responseEntity.getBody();
            logger.info("Raw JSON Response: {}", jsonResponse);
            logger.info("Constructed URL: {}", url);

            // Désérialisation en ApiResponseForex
            ObjectMapper objectMapper = new ObjectMapper();
            ApiResponseForex response = null;
            try {
                response = objectMapper.readValue(jsonResponse, ApiResponseForex.class);
            } catch (JsonProcessingException e) {
                logger.error("Error while deserializing JSON response: {}", e.getMessage());
            }

            logger.info("Mapped API Response: {}", response);
            return response;
        } else {
            logger.error("API call failed with status code: {}", responseEntity.getStatusCode());
            return null;
        }
    }
}
