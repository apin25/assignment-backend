package id.ac.ui.cs.apap.sceleNG.controller;

import id.ac.ui.cs.apap.sceleNG.dto.request.LoginRequestDTO;
import id.ac.ui.cs.apap.sceleNG.dto.response.LoginResponseDTO;
import id.ac.ui.cs.apap.sceleNG.service.AuthenticationService;
import id.ac.ui.cs.apap.sceleNG.util.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import id.ac.ui.cs.apap.sceleNG.dto.request.RegisterDTO;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Autowired
    private AuthenticationService authenticationService;
    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginRequestDTO.getUsername(),
                    loginRequestDTO.getPassword()
                )
            );
            String token = jwtUtil.generateToken(loginRequestDTO.getUsername());
            return ResponseEntity.ok(new LoginResponseDTO(token));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username/password");
        }
    }
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDTO registerDTO) {
        return authenticationService.register(registerDTO);
    }

}