package id.ac.ui.cs.apap.sceleNG.util;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(Date.from(Instant.now().plusSeconds(3600))) // Token berlaku 1 jam
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
    public boolean validateToken(String token, UserDetails userDetails) {
    try {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    } catch (SignatureException |
             MalformedJwtException |
             ExpiredJwtException |
             UnsupportedJwtException |
             IllegalArgumentException e) {
        System.out.println("JWT validation error: " + e.getMessage());
        return false;
    }
}

private boolean isTokenExpired(String token) {
    Date expiration = Jwts.parser()
            .setSigningKey(secret)
            .parseClaimsJws(token)
            .getBody()
            .getExpiration();
    return expiration.before(new Date());
}
}
