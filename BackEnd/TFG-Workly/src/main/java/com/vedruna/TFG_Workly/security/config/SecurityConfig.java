package com.vedruna.TFG_Workly.security.config;

import com.vedruna.TFG_Workly.repositories.IUsuarioRepository;
import com.vedruna.TFG_Workly.security.jwt.JwtFilter;
import com.vedruna.TFG_Workly.security.service.UsuarioDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Autowired
    JwtFilter jwtFilter;
    @Autowired
    IUsuarioRepository usuarioRepository;


    @Bean
    public AuthenticationManager authManager(HttpSecurity http,
                                             PasswordEncoder passwordEncoder,
                                             UserDetailsService userDetailsService) throws Exception {

        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder)
                .and().build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .cors()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests()
                // 1) Preflight (OPTIONS) para todo
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                // 2) Login / Registro
                .requestMatchers("/auth/**").permitAll()
                // 3) PERMITIR GET /api/v1/usuarios/email/{email} SIN ROL
                .requestMatchers(HttpMethod.GET, "/api/v1/usuarios/email/**").permitAll()
                // 4) Cualquier otro /api/v1/usuarios/** exige ROLE_USER
                .requestMatchers("/api/v1/usuarios/**").hasRole("USER")
                // 5) /api/v1/proyectos/** exige ROLE_USER
                .requestMatchers("/api/v1/proyectos/**").hasRole("USER")
                // 6) El resto, autenticado
                .anyRequest().authenticated()
        ;
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }







}