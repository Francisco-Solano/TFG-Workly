package com.vedruna.TFG_Workly.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.List;

@Configuration
public class CorsConfig {
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        // 1) Orígenes explícitos: en tu caso, frontend en localhost:5173
        config.setAllowedOrigins(List.of(
                "http://localhost:5174",
                "http://localhost:8080",
                "https://francisco-solano.github.io"
        ));

        // 2) Métodos HTTP permitidos
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        // 3) Cabeceras permitidas (puedes dejar "*" para permitir todas)
        config.setAllowedHeaders(List.of("*"));
        // 4) Permitir credenciales (cookies / cabeceras Authorization)
        config.setAllowCredentials(true);
        // 5) Si necesitas exponer alguna cabecera extra al cliente, puedes usar:
        //    config.setExposedHeaders(List.of("Authorization", "Content-Type", ...));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // Aplica esta configuración a todas las rutas (“/**”)
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
