package com.sa1.SecurityApp.controllers;

import com.sa1.SecurityApp.dto.LoginDTO;
import com.sa1.SecurityApp.dto.SignUpDTO;
import com.sa1.SecurityApp.dto.UserDTO;
import com.sa1.SecurityApp.services.AuthService;
import com.sa1.SecurityApp.services.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;
    private final AuthService authService;
    @PostMapping("/signup")
    public ResponseEntity<UserDTO> signUp(@RequestBody SignUpDTO signUpDTO)
    {
        UserDTO userDTO=userService.signUp(signUpDTO);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }
    @GetMapping("/login")
    public ResponseEntity<String> getLoginToken(@RequestBody LoginDTO loginDTO, HttpServletResponse httpServletResponse){
        String token=authService.getToken(loginDTO);
        Cookie cookie=new Cookie("token",token);
        cookie.setHttpOnly(true);
        httpServletResponse.addCookie(cookie);
        return new ResponseEntity<>(authService.getToken(loginDTO),HttpStatus.OK);
    }
}
