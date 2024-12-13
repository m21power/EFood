package EFood.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {
        @Autowired
        private AuthenticationProvider authenticationProvider;
        @Autowired
        private JwtAuthenticationFilter jwtAuthenticationFilter;

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http.csrf(csrf -> csrf.disable())
                                .cors(c -> c.configurationSource(corsConfigurationSource()))
                                .authorizeHttpRequests(auth -> auth
                                                .requestMatchers("/auth/**", "/v3/api-docs/**", "/swagger-ui/**",
                                                                "/swagger-ui.html")
                                                .permitAll() // Public endpoints
                                                .requestMatchers("/api/foods/**", "/api/report/**").hasRole("ADMIN")
                                                .requestMatchers("/api/orders/**", "/api/users/**")
                                                .hasAnyRole("ADMIN", "USER")
                                                .anyRequest().authenticated()) // All other requests require
                                .exceptionHandling(exceptionHandling -> exceptionHandling
                                                .authenticationEntryPoint((request, response, authException) -> {
                                                        response.setStatus(HttpStatus.UNAUTHORIZED.value());
                                                        response.getWriter()
                                                                        .write("{\"error\": \"Unauthorized access. Please log in.\"}");
                                                })
                                                .accessDeniedHandler((request, response, accessDeniedException) -> {
                                                        response.setStatus(HttpStatus.FORBIDDEN.value());
                                                        response.getWriter().write(
                                                                        "{\"error\": \"You do not have permission to access this resource.\"}");
                                                })) // authentication
                                .sessionManagement(session -> session
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                .authenticationProvider(authenticationProvider)
                                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

                return http.build();
        }

        @Bean
        CorsConfigurationSource corsConfigurationSource() {
                CorsConfiguration configuration = new CorsConfiguration();
                configuration.setAllowedOrigins(
                                List.of("https://localhost:3000"));
                configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
                configuration.setAllowedHeaders(List.of(
                                "Authorization",
                                "Content-Type",
                                "Accept",
                                "Cache-Control",
                                "Content-Disposition",
                                "Origin"));
                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                configuration.setAllowCredentials(true);
                source.registerCorsConfiguration("/**", configuration);

                return source;
        }
}