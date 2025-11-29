package id.ac.ui.cs.apap.sceleNG.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import id.ac.ui.cs.apap.sceleNG.util.JwtFilter;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    @Order(1)
    public SecurityFilterChain jwtFilterChain(HttpSecurity http) throws Exception {
        http.securityMatcher("/api/**")
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(requests -> requests
                .requestMatchers(HttpMethod.GET, "/api/users/**")
                    .authenticated()
                .requestMatchers(HttpMethod.GET, "/api/courses/**")
                    .authenticated()
                .requestMatchers(HttpMethod.GET, "/api/resources/**")
                    .authenticated()
                .requestMatchers(HttpMethod.GET, "/api/assignments", "/api/assignments/{id}")
                    .hasAnyRole("STUDENT", "LECTURER", "ASSISTANT")
                .requestMatchers(HttpMethod.POST, "/api/assignments")
                    .hasAnyRole("LECTURER", "ASSISTANT")
                .requestMatchers(HttpMethod.PUT, "/api/assignments/{id}","/api/assignments/{id}/delete")
                    .hasAnyRole("LECTURER", "ASSISTANT")
                .requestMatchers(HttpMethod.GET, "/api/wikis", "/api/wikis/{id}")
                    .hasAnyRole("STUDENT", "LECTURER", "ASSISTANT")
                .requestMatchers(HttpMethod.POST, "/api/wikis")
                    .hasAnyRole("STUDENT","LECTURER", "ASSISTANT")
                .requestMatchers(HttpMethod.PUT, "/api/wikis/{id}","/api/wikis/{id}/delete")
                    .hasAnyRole("STUDENT","LECTURER", "ASSISTANT")
                .requestMatchers(HttpMethod.GET, "/api/submission", "/api/submission/{id}")
                    .hasAnyRole("STUDENT")
                .requestMatchers(HttpMethod.POST, "/api/submission")
                    .hasAnyRole("STUDENT")
                .requestMatchers(HttpMethod.PUT, "/api/submission/{id}","/api/submission/{id}/delete")
                    .hasAnyRole("STUDENT")
                .anyRequest().authenticated()
            )
            .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
            .exceptionHandling(e -> e
                .authenticationEntryPoint((request, response, authException) -> {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().write("Unauthorized: You need to login first!");
                })
                .accessDeniedHandler((request, response, accessDeniedException) -> {
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    response.getWriter().write("Forbidden: You do not have access to this resource!");
                })
            );

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:5173"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}