package com.Perseo.service;

import com.Perseo.dto.response.AuthResponse;
import com.Perseo.dto.request.LoginRequest;
import com.Perseo.dto.request.RegisterRequest;
import com.Perseo.model.User;
import com.Perseo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    public AuthResponse login(LoginRequest login) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword()));

        UserDetails user = userRepository.findByUsername(login.getUsername()).orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        String token = jwtService.getTokenService(user);

        return AuthResponse
                .builder()
                .token(token)
                .role(((User) user).getRole())
                .build();
    }

    public AuthResponse register(RegisterRequest register) {
        User user = User.builder()
                .username(register.getUsername())
                .email(register.getEmail())
                .password(passwordEncoder.encode(register.getPassword()))
                .role(register.getRole())
                .build();

        userRepository.save(user);

        return AuthResponse
                .builder()
                .token(jwtService.getTokenService(user))
                .role(register.getRole())
                .build();
    }
}