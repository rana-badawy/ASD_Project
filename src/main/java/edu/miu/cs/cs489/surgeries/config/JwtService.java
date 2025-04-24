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
    //@Value("${jwt.secret}")
    private String SECRET = "37965956e56dfe129471f9581b10f5f76daefb717acf569565799c4c4271be0f7ccd28de89aeafac26b39598958a24b7af02faada8725cfbda22169073aa2a29e0528c1135ddccf40261d2e9e02370f7bbb66159846241e2657434db3a2efb8af7de1ad0f010f8109d0e186c12b91e12d1704c9340466fa29a5ed4ecd9886f7adfcc92c698548bece5b3d2458b2fed54efee6e27536e201b558f71a363a4ce0a3648273fe1133066839f092b6771b710a7c5edcd6bdb7655db2626309f9a0873b789797e7b631bd4cc03d7c9084ba270c91204a02b1ff414cfc8511e2869bab143724b40c78ea5c93d8b2e61fdd9b9338a76291026c42e51e5ec125a0d21051e";

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
