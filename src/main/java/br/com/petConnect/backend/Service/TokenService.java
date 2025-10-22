package br.com.petConnect.backend.Service;

import br.com.petConnect.backend.Model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Service
public class TokenService {

    @Value("${jwt.expiration.time}")
    private String expiration;

    @Value("${api.jwt.secret}")
    private String secret;

    public String generateToken(Authentication authentication) {
        User logged = (User) authentication.getPrincipal();
        Date today = new Date();
        Date expirationDate = new Date(today.getTime() + Long.parseLong(expiration));

        Key key = Keys.hmacShaKeyFor(secret.getBytes());

        return Jwts.builder()
                .setIssuer("PetConnect")
                .setSubject(logged.getId().toString())
                .setIssuedAt(today)
                .setExpiration(expirationDate)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public boolean isTokenValid(String token) {

        Key key = Keys.hmacShaKeyFor(secret.getBytes());

        try{
            Jwts.parser()
                    .verifyWith((SecretKey) key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public Long getIdUser(String token) {

        Key key = Keys.hmacShaKeyFor(secret.getBytes());

        Claims claims = Jwts.parser()
                .verifyWith((SecretKey) key)
                .build()
                .parseSignedClaims(token)
                .getBody();

        return Long.parseLong(claims.getSubject());
    }

    public String generateTokenForOAuth2User(User user) {
        Date today = new Date();
        Date expirationDate = new Date(today.getTime() + Long.parseLong(expiration));

        Key key = Keys.hmacShaKeyFor(secret.getBytes());

        return Jwts.builder()
                .setIssuer("PetConnect")
                .setSubject(user.getId().toString())
                .setIssuedAt(today)
                .setExpiration(expirationDate)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }
}
