package br.com.edielsonassis.configs;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Value("${cors.allowedOrigins}")
	private String allowedOrigins;

    private static final String[] PUBLIC_METHODS = {"GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"};
    private static final String[] PUBLIC_HEADERS = {"Content-Type", "Authorization", "Access-Control-Allow-Origin"};
    private static final String ALL = "/**";
    
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(List.of(allowedOrigins));
        corsConfiguration.setAllowedMethods(List.of(PUBLIC_METHODS));
        corsConfiguration.setAllowedHeaders(List.of(PUBLIC_HEADERS));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration(ALL, corsConfiguration);
        return source;
    }
}