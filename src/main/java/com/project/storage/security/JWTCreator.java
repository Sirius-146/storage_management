package com.project.storage.security;

import java.util.Date;

import javax.crypto.SecretKey;

import com.project.storage.model.Role;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

public class JWTCreator {
    public static final String HEADER_AUTHORIZATION = "Authorization";
    public static final String ROLES_AUTHORITIES = "authorities";
    private static final long CLOCK_SKEW_SECONDS = 60L;

    private static SecretKey buildKey(String base64Secret) {
        // Base64-encode sua secret fora do código (env var). Aqui decodificamos para a key forte:
        byte[] keyBytes = Decoders.BASE64.decode(base64Secret);
        return Keys.hmacShaKeyFor(keyBytes); // correto para HS512 se o tamanho for adequado
    }

    public static String create(String prefix, String base64Secret, JWTObject jwtObject){
        SecretKey key = buildKey(base64Secret);

        String token = Jwts.builder()
                .subject(jwtObject.subject())
                .issuedAt(jwtObject.issuedAt())
                .expiration(jwtObject.expiration())
                .claim(ROLES_AUTHORITIES, jwtObject.role().name()) // salva como String
                .issuer("meu-app") //opcional
                .audience().add("meu-front").and() // opcional (JJWT 0.12+)
                .signWith(key, Jwts.SIG.HS512)
                .compact();
        return prefix + " " + token;
    }

    public static JWTObject create(String headerValue, String prefix, String base64Secret)
            throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException {
        
        if (headerValue == null || !headerValue.startsWith(prefix + " ")) {
            throw new MalformedJwtException("Authorization header null or not valid");
        }

        String token = headerValue.substring((prefix + " ").length()); // remove só o prefixo inicial
        SecretKey key = buildKey(base64Secret);
        
        Claims claims = Jwts.parser()
                    .clockSkewSeconds(CLOCK_SKEW_SECONDS)
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

        // (Opcional) Validações extras:
        Date now = new Date();
        if (claims.getExpiration() != null && claims.getExpiration().before(new Date(now.getTime() - CLOCK_SKEW_SECONDS * 1000))) {
            throw new ExpiredJwtException(null, claims, "Expired token");
        }
        return new JWTObject(
            claims.getSubject(),
            claims.getIssuedAt(),
            claims.getExpiration(),
            Role.valueOf((String) claims.get(ROLES_AUTHORITIES))
        );
    }
}
