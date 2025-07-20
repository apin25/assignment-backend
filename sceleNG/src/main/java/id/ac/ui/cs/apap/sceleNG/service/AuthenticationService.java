package id.ac.ui.cs.apap.sceleNG.service;

import org.springframework.http.ResponseEntity;
import id.ac.ui.cs.apap.sceleNG.dto.request.RegisterDTO;

public interface AuthenticationService {
    ResponseEntity<?> register(RegisterDTO registerDTO);
    ResponseEntity<?> getUserByUsername(String username);
}
