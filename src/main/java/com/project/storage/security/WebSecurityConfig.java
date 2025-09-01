package com.project.storage.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.project.storage.handler.CustomAccessDeniedHandler;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {
    @Bean
    public PasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }

    private static final String[] SWAGGER_WHITELIST = {
        "/v3/api-docs/**",     // documentação JSON
        "/swagger-ui/**",      // arquivos estáticos da UI
        "/swagger-ui.html"     // entrada principal
    };

    @Bean
    protected SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            JWTFilter jwtFilter,
            CustomAuthenticationEntryPoint entryPoint,
            CustomAccessDeniedHandler accessDeniedHandler) throws Exception {
        
        http.headers(headers -> headers.frameOptions(frame -> frame.disable()));

        http.cors(Customizer.withDefaults())
            .csrf(csrf -> csrf.disable())
            .addFilterAfter(jwtFilter, UsernamePasswordAuthenticationFilter.class)
            .authorizeHttpRequests( authz -> authz
                .requestMatchers(SWAGGER_WHITELIST).permitAll()
                .requestMatchers("/h2-console/**").permitAll() // ⚠️ só em dev
                .requestMatchers("/workers/**").hasAnyRole("ADMIN", "HUMAN_RESOURCES")
                .requestMatchers(HttpMethod.POST, "/login").permitAll()
                .requestMatchers("/products/**").permitAll()
                .requestMatchers("/reservations/**").hasAnyRole("ADMIN", "RECEPTIONIST", "MAITRE")
                .requestMatchers("/sells/**").hasAnyRole("ADMIN", "WAITER", "MAITRE")
                .requestMatchers("/stock/**").hasAnyRole("ADMIN", "STORAGE_MANAGER")
                .anyRequest().authenticated()                
            )
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(entryPoint)
                        .accessDeniedHandler(accessDeniedHandler));
        return http.build();
    }
}
