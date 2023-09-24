package com.ejemplo.api.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${application.security.jwt.secret-key}")
    private String secretKey;
    @Value("${application.security.jwt.expiration}")
    private Long jwtExpiration;
    @Value("${application.security.jwt.refresh-token.expiration}")
    private Long refreshExpiration;
    public String extractUsername(String jwt) {
        return extractClaim(jwt, Claims::getSubject);
    }

    public boolean isTokenValid(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public String generateToken (UserDetails userDetails){
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateRefreshToken(UserDetails userDetails){

        return buildToken(new HashMap<>(), userDetails, refreshExpiration);
    }
    public String generateToken(Map<String, Object> claims, UserDetails userDetails){

        return buildToken(claims, userDetails, jwtExpiration);
    }
    private String buildToken(Map<String, Object> extraClaims,
                              UserDetails userDetails,
                              Long expiration){
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+ expiration))
                .signWith(getSignIngKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public <T> T extractClaim (String token, Function<Claims, T> claimResolver){
        final Claims claim = allClaims(token);
        return claimResolver.apply(claim);
    }


    public Claims allClaims(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignIngKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignIngKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}