package com.vedruna.TFG_Workly.security.auth.services;

import com.vedruna.TFG_Workly.models.Usuario;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.util.Map;


public interface JWTServiceI {
    String getToken(Usuario user);

    String getToken(Map<String, Object> extraClaims, Usuario user);

    Key getKey();

    String getUsernameFromToken(String token);

    boolean isTokenValid(String token, UserDetails userDetails);
}
