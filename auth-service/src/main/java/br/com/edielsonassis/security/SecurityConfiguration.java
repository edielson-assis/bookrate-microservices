package br.com.edielsonassis.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import br.com.edielsonassis.configs.CorsConfig;

import lombok.AllArgsConstructor;

@Configuration
@EnableWebSecurity
@AllArgsConstructor 
public class SecurityConfiguration {

    private static final String ADMIN = "Admin";
    private static final String PUBLIC_POST_METHODS = "/auth-service/**";
    private static final String PUBLIC_GET_METHODS = "/auth-service/**";
    private static final String[] SWAGGER = {"/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**"};

    private final SecurityFilter securityFilter;
    private final CorsConfig corsConfig;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .cors(cors -> cors.configurationSource(corsConfig.corsConfigurationSource()))
                .authorizeHttpRequests(req -> {
                    req.requestMatchers(HttpMethod.POST, PUBLIC_POST_METHODS).permitAll()
                    .requestMatchers(HttpMethod.GET, PUBLIC_GET_METHODS).permitAll()
                    .requestMatchers(SWAGGER).permitAll()
                    .requestMatchers("/users").denyAll()
                    .requestMatchers("/admin/**").hasAuthority(ADMIN);
                    req.anyRequest().authenticated();
                })
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}