package id.ac.ui.cs.apap.sceleNG.service;

import java.util.List;
import java.util.UUID;

import id.ac.ui.cs.apap.sceleNG.dto.request.LoginRequestDTO;
import id.ac.ui.cs.apap.sceleNG.dto.response.LoginResponseDTO;
import id.ac.ui.cs.apap.sceleNG.dto.response.UserDTO;

public interface AuthService {
    LoginResponseDTO login(LoginRequestDTO loginRequestDTO);
    List<UserDTO> findAll();
    UserDTO findById(UUID id);
}