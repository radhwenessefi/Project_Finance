package tn.esprit.projectbackend.Configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/ws/**") // Autoriser les requêtes vers les points de terminaison WebSocket
                .allowedOrigins("*") // Autoriser toutes les origines
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS");

    }
}
