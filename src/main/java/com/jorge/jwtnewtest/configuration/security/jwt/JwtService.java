package com.jorge.jwtnewtest.configuration.security.jwt;

import com.jorge.jwtnewtest.exception.security.TokenExpiredException;
import com.jorge.jwtnewtest.exception.security.TokenInvalidException;
import com.jorge.jwtnewtest.exception.security.TokenMalformedException;
import com.jorge.jwtnewtest.exception.security.TokenSignatureException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static java.lang.String.format;

@Component
public class JwtService {
    @Value("${application.jwt.key}")
    private String SECRET_KEY;

    public String generateToken(UserDetails user){
        return generateToken(new HashMap<>(), user);
    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails user) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60))    // 1 Minute
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Claims extractAllClaims(String token){
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new TokenExpiredException("The JWT token has expired");
        } catch (MalformedJwtException e) {
            throw new TokenMalformedException("The JWT token is Malformed. Its structure appears to be invalid");
        } catch (SignatureException e) {
            throw new TokenSignatureException("The JWT token Signature is invalid. Token might have been tampered");
        } catch (UnsupportedJwtException | IllegalArgumentException e) {
            throw new TokenInvalidException(e.getMessage());
        }
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }

    private Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    }

    private boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date(System.currentTimeMillis()));
    }

    public boolean isTokenValid(String token, UserDetails user){
        var username = extractUsername(token);
        return (username.equals(user.getUsername()) && !isTokenExpired(token));
    }


    private Key getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
