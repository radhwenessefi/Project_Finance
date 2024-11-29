package tn.esprit.projectbackend.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

@Service
public class PythonRecommendationService {

    private final String pythonApiUrl = "http://127.0.0.1:5000/recommend";

    public Map<String, Object> getRecommendations(int userId) {
        RestTemplate restTemplate = new RestTemplate();
        String url = pythonApiUrl + "?user_id=" + userId;

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> customResponse = new HashMap<>();

        try {
            JsonNode root = mapper.readTree(response.getBody());
            customResponse.put("userId", userId);
            customResponse.put("recommendations", root);
        } catch (Exception e) {
            e.printStackTrace(); // Handle error
        }

        return customResponse;
    }
}
