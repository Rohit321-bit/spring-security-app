package com.sa1.SecurityApp.services;

import com.sa1.SecurityApp.dto.LoginDTO;
import com.sa1.SecurityApp.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
@RequiredArgsConstructor
@Service
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    public String getToken(LoginDTO loginDTO) {
        Authentication authentication=authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getEmail(),loginDTO.getPassword())
        );
        User user= (User) authentication.getPrincipal();
        return jwtService.getToken(user);
    }
}
