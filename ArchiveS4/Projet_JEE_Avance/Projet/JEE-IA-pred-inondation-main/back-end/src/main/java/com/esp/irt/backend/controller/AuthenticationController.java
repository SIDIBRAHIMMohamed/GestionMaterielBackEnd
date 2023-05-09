package com.esp.irt.backend.controller;

import com.esp.irt.backend.config.Token;
import com.esp.irt.backend.entities.User;
import com.esp.irt.backend.services.JwtTokenService;
import com.esp.irt.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api")
public class AuthenticationController {

    @Autowired
    UserService userService;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtTokenService jwtTokenService;

    @PostMapping("/signup")
    public ResponseEntity<HttpStatus> signup(@RequestBody User user) {
        try {
            userService.addUser(user);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/authenticate")
    public Token authenticate(@RequestParam String email, @RequestParam String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (final BadCredentialsException ex) {
            ex.printStackTrace();

            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        final UserDetails userDetails = userService.loadUserByUsername(email);
        final Token authenticationResponse = new Token();
        authenticationResponse.setAccessToken(jwtTokenService.generateToken((User) userDetails));
        return authenticationResponse;
    }
}