package tn.esprit.projectbackend.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;



@AllArgsConstructor
@Service

@Slf4j
public class BackTestFlask {

    RestTemplate restTemplate = new RestTemplate();

    // Set the Flask API URL

    public String callFlaskApi(String jsonData, int cash, float margin, int stratNum) {
        String apiUrl = "http://localhost:5000/get_stat?jsonData="+ jsonData + "&cash=" + cash + "&margin=" + margin + "&stratNum=" + stratNum;

        // Prepare the request body
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);


        return restTemplate.exchange(apiUrl, HttpMethod.GET, requestEntity, String.class).getBody();
    }
}


