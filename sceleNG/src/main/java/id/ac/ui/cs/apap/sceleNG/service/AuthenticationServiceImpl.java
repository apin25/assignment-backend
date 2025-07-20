package id.ac.ui.cs.apap.sceleNG.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import id.ac.ui.cs.apap.sceleNG.dto.request.RegisterDTO;
import id.ac.ui.cs.apap.sceleNG.model.Authentication;
import id.ac.ui.cs.apap.sceleNG.repository.AuthenticationDb;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private AuthenticationDb authenticationDb;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public ResponseEntity<?> register(RegisterDTO registerDTO) {
        Optional<Authentication> existingUser = authenticationDb.findByUsername(registerDTO.getUsername());
        if (existingUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already taken");
        }

        Authentication newUser = new Authentication();
        newUser.setName(registerDTO.getName());
        newUser.setUsername(registerDTO.getUsername());
        newUser.setPassword(passwordEncoder.encode(registerDTO.getPassword()));

        authenticationDb.save(newUser);
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
    }
}
