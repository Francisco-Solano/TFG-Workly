package com.vedruna.TFG_Workly.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Extrae el token de las peticiones JWT que van llegando y de la cabecera Authorization de la petición HTTP.
 */
@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    JwtTokenProvider tokenProvider;

    @Autowired
    UserDetailsService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getServletPath();
        // <- Aquí añadimos esta línea:
        if (path.startsWith("/auth/")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = extractToken(request);
        System.out.println("JwtFilter - Token extraído: " + token);

        if (token != null && tokenProvider.isValidToken(token)) {
            String email = tokenProvider.getEmailFromToken(token);
            System.out.println("JwtFilter - Email del token: " + email);

            try {
                UserDetails user = userService.loadUserByUsername(email);
                System.out.println("JwtFilter - UserDetails cargado: " + user);

                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(auth);

            } catch (UsernameNotFoundException ex) {
                // Si no existe, simplemente no autenticamos aquí
                System.out.println("JwtFilter - Usuario no encontrado en token: " + email);
            }
        } else {
            System.out.println("JwtFilter - Token inválido o no presente");
        }

        filterChain.doFilter(request, response);
    }


    private String extractToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasLength(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7).trim();
        }
        return null;
    }

    private Collection<? extends GrantedAuthority> extractAuthoritiesFromToken(String token) {
        JwtParser parser = Jwts.parser()
                .setSigningKey(Keys.hmacShaKeyFor(tokenProvider.getJwtSecret().getBytes(StandardCharsets.UTF_8)))
                .build();

        Claims claims = parser.parseClaimsJws(token).getBody();

        List<String> roles = claims.get("roles", List.class);

        if (roles == null) {
            return List.of();
        }

        return roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}