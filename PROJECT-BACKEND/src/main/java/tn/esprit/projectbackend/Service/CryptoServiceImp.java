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
import tn.esprit.projectbackend.Repository.CryptoRepository;

@Service
public class CryptoServiceImp implements ICryptoService {

    @Autowired
    private CryptoRepository cryptoRepository;

    @Autowired
    private RestTemplate restTemplate;

    private static final Logger logger = LoggerFactory.getLogger(CryptoServiceImp.class);

    // Optional: Move this to application.properties for security
    private final String apiKey = "reCFP8EOKyHnFp_ooIbLlE2OMPQB93BW";

    @Override
    public ApiResponseForex getRealTimeCrypto(String symbol, String range, String dateStart, String dateEnd) {
        // Dynamically use the range, dateStart, and dateEnd parameters in the URL
        String url = String.format("https://api.polygon.io/v2/aggs/ticker/X:%sUSD/range/%s/day/%s/%s?adjusted=true&sort=asc&apiKey=%s",
                symbol.toUpperCase(), range, dateStart, dateEnd, apiKey);

        // Call the API and log the raw JSON response
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);

        // Check if the response status is successful before processing
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            String jsonResponse = responseEntity.getBody();
            logger.info("Raw JSON Response: {}", jsonResponse);
            logger.info("Constructed URL: {}", url);

            // Deserialize JSON response to ApiResponse object
            ObjectMapper objectMapper = new ObjectMapper();
            ApiResponseForex response = null;
            try {
                response = objectMapper.readValue(jsonResponse, ApiResponseForex.class);
            } catch (JsonProcessingException e) {
                logger.error("Error while deserializing JSON response: {}", e.getMessage());
            }

            // Log the mapped response
            logger.info("Mapped API Response: {}", response);
            return response;
        } else {
            logger.error("API call failed with status code: {}", responseEntity.getStatusCode());
            return null; // Or handle with a custom error response
        }
    }

    @Override
    public ApiResponseForex getRealTimeCrypto(String symbol) {
        return null;
    }




}
