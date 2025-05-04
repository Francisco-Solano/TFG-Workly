package com.vedruna.TFG_Workly.security.auth.services;

import com.vedruna.TFG_Workly.models.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;


@Service
public class JWTService implements JWTServiceI{

    @Value("${SECRET_KEY}")
    String SECRET_KEY;

    @Override
    public String getToken(Usuario user) {
        return getToken(Map.of(
            "email", user.getEmail(),
            "username", user.getUsername()
            //, los atributos que querais guardar
        ), user);
    }


    @Override
    public String getToken(Map<String, Object> extraClaims, Usuario user) {
        return Jwts.builder()
                .claims(extraClaims)
                .subject(Integer.toString(user.getUsuarioId()))
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+1000*60*60*24)) //Tiempo expiracion 24h. Deberia ser 5 mins.
                .signWith(getKey())
                .compact();
    }

    @Override
    public SecretKey getKey() {
        byte[] keyBytes= Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String getUsernameFromToken(String token) {
        return getClaim(
                        token, 
                        (claims) -> claims.get("username", String.class)
                        );
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username=getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private Claims getAllClaims(String token)
    {
        return Jwts
                .parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public <T> T getClaim(String token, Function<Claims,T> claimsResolver)
    {
        final Claims claims=getAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Date getExpiration(String token)
    {
        return getClaim(token, Claims::getExpiration);
    }

    private boolean isTokenExpired(String token)
    {
        return getExpiration(token).before(new Date());
    }

}
