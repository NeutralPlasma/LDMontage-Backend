package eu.virtusdevelops.ldmontage.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.virtusdevelops.ldmontage.middleware.CookieAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Configuration
@EnableWebSecurity(debug = true)
@RequiredArgsConstructor
@EnableMethodSecurity()
public class SecurityConfig {

    private final AuthenticationProvider authenticationProvider;
    private final CookieAuthenticationFilter cookieAuthenticationFilter;

    //private final LogoutService logoutService;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors
                        .configurationSource(corsConfigurationSource())
                )
                .addFilterBefore(cookieAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(e -> e
                        .authenticationEntryPoint(restAuthPoint())
                ).authorizeHttpRequests(a -> a
//                        .requestMatchers("/ws").permitAll() // web socket
                                .requestMatchers("/api/v1/auth/**").permitAll() // auth
                                .anyRequest().authenticated()

                ).authenticationProvider(authenticationProvider)
                .logout(logout -> logout
                        .logoutUrl("/api/v1/auth/logout")
                        //.addLogoutHandler(logoutService)
                        .deleteCookies()
                        .logoutSuccessHandler(
                                (request, response, authentication) -> SecurityContextHolder.clearContext()
                        )
                ).build();
    }



    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(List.of(
                "http://localhost:5173", "localhost:5173", "http://localhost:5173/",
                "https://ld-montage-be.virtus.lol/", "ld-montage-be.virtus.lol", "https://ld-montage-be.virtus.lol",
                "https://ld-montage.virtus.lol/", "ld-montage.virtus.lol", "https://ld-montage.virtus.lol"
        ));

        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH"));
        configuration.setAllowedHeaders(List.of("Content-Type", "Authorization"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


    private final ObjectMapper objectMapper = new ObjectMapper();

    @Bean
    public AuthenticationEntryPoint restAuthPoint() {
        return ((request, response, authException) -> {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());

            Map<String, Object> data = new HashMap<>();
            data.put(
                    "timestamp",
                    Calendar.getInstance().getTime());
            data.put(
                    "exception",
                    authException.getMessage());
            data.put(
                    "status",
                    HttpStatus.UNAUTHORIZED.value());

            response.setContentType("application/problem+json");

            response.getOutputStream()
                    .println(objectMapper.writeValueAsString(data));

        });
    }
}
