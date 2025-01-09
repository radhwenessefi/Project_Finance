package tn.esprit.projectbackend.Security;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.Collections;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration configuration = new CorsConfiguration();

        // Allow all origins
        configuration.setAllowedOriginPatterns(Collections.singletonList("*"));

        // Define allowed HTTP methods
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));

        // Define allowed headers
        configuration.setAllowedHeaders(Arrays.asList("*"));

        // Allow credentials (cookies and authentication information)
        configuration.setAllowCredentials(true);

        // Create CORS configuration source based on URL
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        // Register configuration for all URLs
        source.registerCorsConfiguration("/**", configuration);

        return new CorsFilter(source);
    }
}
