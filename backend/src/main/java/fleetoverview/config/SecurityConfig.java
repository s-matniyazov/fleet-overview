package fleetoverview.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import fleetoverview.config.filters.AuthorizationFilter;
import fleetoverview.config.filters.AuthenticationFilter;
import fleetoverview.config.filters.CORSFilter;

import java.util.List;

/**
 * @author :  sardor.matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 18 февр. 2025
 **/
@Configuration
public class SecurityConfig {
    private final AuthorizationFilter authorizationFilter;
    private final AuthenticationFilter authenticationFilter;
    private final CORSFilter corsFilter;

    @Autowired
    public SecurityConfig(AuthorizationFilter authorizationFilter, AuthenticationFilter authenticationFilter, CORSFilter corsFilter) {
        this.authorizationFilter = authorizationFilter;
        this.authenticationFilter = authenticationFilter;
        this.corsFilter = corsFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests((requests) -> {
                            requests.requestMatchers(
                                    "/api/auth/login"
                            ).permitAll();

                            requests.requestMatchers("/v3/api-docs/**", "/swagger-ui/**").permitAll();

                            requests.anyRequest().authenticated();
                        }
                )
                .httpBasic(Customizer.withDefaults())
//                .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(se -> se.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:5173", "https://yourfrontend.com"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
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
