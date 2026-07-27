package az.librarycrudapi.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/books/**", "/api/authors/**", "/api/members/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/books/**", "/api/authors/**", "/api/members/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/books/**", "/api/authors/**", "/api/members/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/books/**", "/api/authors/**", "/api/members/**").hasAnyRole("USER", "ADMIN")
                        .anyRequest().authenticated()
                )
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(authenticationEntryPoint())
                        .accessDeniedHandler(accessDeniedHandler())
                );

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return (request, response, authException) -> {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);

            Map<String, Object> body = new HashMap<>();
            body.put("timestamp", LocalDateTime.now().toString());
            body.put("status", HttpStatus.UNAUTHORIZED.value());
            body.put("error", "Unauthorized");
            body.put("message", "Giris edilmeyib ve ya token sehvdir");

            response.getWriter().write(objectMapper.writeValueAsString(body));
        };
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return (request, response, accessDeniedException) -> {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);

            Map<String, Object> body = new HashMap<>();
            body.put("timestamp", LocalDateTime.now().toString());
            body.put("status", HttpStatus.FORBIDDEN.value());
            body.put("error", "Forbidden");
            body.put("message", "Bu emeliyyat üçün icazaniz yoxdur");

            response.getWriter().write(objectMapper.writeValueAsString(body));
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
