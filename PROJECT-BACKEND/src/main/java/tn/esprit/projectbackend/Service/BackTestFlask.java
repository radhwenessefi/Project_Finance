package tn.esprit.projectbackend.Service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@AllArgsConstructor
@Service
@Transactional
@Slf4j
public class BackTestFlask {
    private final RestTemplate restTemplate = new RestTemplate();

    public String callFlaskApi(String symbol, int cash, float margin, int stratNum) {
        // Construire l'URL de l'API Flask
        String apiUrl = "http://localhost:5000/get_stat?symbol=" + symbol +
                "&cash=" + cash + "&margin=" + margin + "&stratNum=" + stratNum;

        // Préparer l'en-tête de la requête
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        // Appeler l'API Flask
        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.GET, requestEntity, String.class);

        // Retourner la réponse
        return response.getBody();
    }
}
