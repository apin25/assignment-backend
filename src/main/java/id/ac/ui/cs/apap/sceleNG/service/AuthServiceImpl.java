package id.ac.ui.cs.apap.sceleNG.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import id.ac.ui.cs.apap.sceleNG.dto.request.LoginRequestDTO;
import id.ac.ui.cs.apap.sceleNG.dto.response.CommonResponse;
import id.ac.ui.cs.apap.sceleNG.dto.response.UserDTO;
import id.ac.ui.cs.apap.sceleNG.dto.response.LoginResponseDTO;

@Service
public class AuthServiceImpl implements AuthService {
    private final WebClient webClient;

    public AuthServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:5914/").build();
    }

    
    private String getCurrentToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getCredentials() instanceof String) {
            return (String) authentication.getCredentials();
        }
        return null;
    }

    @Override
    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {
        var response = webClient
                .post()
                .uri("api/auth/login")
                .bodyValue(loginRequestDTO)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<CommonResponse<LoginResponseDTO>>() {
                })
                .block();

        if (response == null) {
            return null;
        }

        return response.getData();
    }
    @Override
    public List<UserDTO> findAll() {
        List<UserDTO> response = webClient
                .get()
                .uri("api/users")
                .header("Authorization", "Bearer " + getCurrentToken())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<UserDTO>>() {
                })
                .block();

        return response;
    }

    @Override
    public UserDTO findById(UUID id) {
        CommonResponse<UserDTO> response = webClient
                .get()
                .uri(String.format("api/users/me"))
                .header("Authorization", "Bearer " + getCurrentToken())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<CommonResponse<UserDTO>>() {})
                .block();

        if (response == null || response.getData() == null) {
            return null;
        }
        return response.getData(); 
        }
}
