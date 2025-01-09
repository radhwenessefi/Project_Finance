package tn.esprit.projectbackend;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Applique CORS à tous les endpoints
                .allowedOrigins("http://localhost:4200") // Autorise l'origine Angular
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Autorise ces méthodes
                .allowedHeaders("*") // Autorise tous les headers
                .allowCredentials(true); // Autorise les cookies si nécessaire
    }
}
