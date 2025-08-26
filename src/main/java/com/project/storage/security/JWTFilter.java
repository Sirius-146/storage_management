package com.project.storage.security;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.project.storage.model.Role;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JWTFilter.class);
    private JWTProperties securityConfig = new JWTProperties();

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        // obtém o token da request com AUTHORIZATION
        String token = request.getHeader(JWTCreator.HEADER_AUTHORIZATION);
        // esta implementação só está validando a integridade do token
        try {
            if (token != null && !token.isBlank()){
                JWTObject tokenObject = JWTCreator.create(token, securityConfig.getPrefix(), securityConfig.getKey());

                Collection<? extends GrantedAuthority> authorities = authorities(tokenObject.role());

                UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                    tokenObject.subject(),
                    null,
                    authorities
                );

                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                SecurityContextHolder.clearContext();
            }
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException e) {
            logger.warn("Expired token: {}", e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"error\":\"expired tokenexpirado\"}");
        } catch (UnsupportedJwtException | MalformedJwtException e) {
            logger.warn("Invalid token: {}", e.getMessage());
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("{\"error\":\"Invalid token\"}");
        }
    }

    private Collection<? extends GrantedAuthority> authorities(Role role) {
    return List.of(new SimpleGrantedAuthority(role.name()));
}
    
}
