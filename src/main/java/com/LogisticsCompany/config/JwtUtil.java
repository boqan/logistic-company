package com.LogisticsCompany.config;

import com.LogisticsCompany.model.Credentials;
import com.LogisticsCompany.repository.CredentialsRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

import java.security.Key;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class JwtUtil {

    @Autowired
    private CredentialsRepository credentialsRepository;

    private static final String SECRET_KEY = "ECFEABFD15296E4589222EC8C82C1ECFEABFD15296E4589222EC8C82C1";
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Long getUserId(String username) {
        // Retrieve the user by username from the database and return the user ID
        // This is just a placeholder, you'll need to replace it with your actual logic
        Optional<Credentials> credentials = credentialsRepository.findByUsername(username);
        return credentials != null ? credentials.get().getConnectedId() : null;
    }

    public String generateToken(UserDetails userDetails) {
        String roles = userDetails.getAuthorities().stream()
                .map(grantedAuthority -> grantedAuthority.getAuthority())
                .collect(Collectors.joining(","));

        Long userId = getUserId(userDetails.getUsername());


        return Jwts
                .builder()
                .setSubject(userDetails.getUsername())
                .claim("roles", roles)
                .claim("userId", userId)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24 ))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();

    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24 ))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();

    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }


    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSigningKey() {
        byte[] secretBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(secretBytes);
    }
}
