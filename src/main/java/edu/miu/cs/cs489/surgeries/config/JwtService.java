package edu.miu.cs.cs489.surgeries.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Service
public class JwtService {
    @Value("${jwt.secret}")
    private String SECRET;

    private String populateAuthorities(Collection<? extends GrantedAuthority> authorities) {
        return authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
    }

    private SecretKey signInKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET));
    }

    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .signWith(signInKey())
                .issuedAt(new Date())
                .issuer("rana")
                .expiration(new Date((new Date()).getTime() + 1000 * 60 * 60 * 24))
                .subject(userDetails.getUsername())
                .claim("authorities", populateAuthorities(userDetails.getAuthorities()))
                .compact();
    }

    public Claims parseSignedClaims(String token) {
        return Jwts.parser()
                .verifyWith(signInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
