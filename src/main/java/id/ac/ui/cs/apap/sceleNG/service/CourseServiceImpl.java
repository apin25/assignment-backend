package id.ac.ui.cs.apap.sceleNG.service;

import java.util.List;
import java.util.UUID;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import id.ac.ui.cs.apap.sceleNG.dto.response.CommonResponse;
import id.ac.ui.cs.apap.sceleNG.dto.response.CourseDTO;

@Service
public class CourseServiceImpl implements CourseService {
    private final WebClient webClient;

    public CourseServiceImpl(WebClient.Builder webClientBuilder){
        this.webClient = webClientBuilder.baseUrl("http://localhost:1190").build();
    }

    private String getCurrentToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getCredentials() instanceof String) {
            return (String) authentication.getCredentials();
        }
        return null;
    }

    @Override
    public List<CourseDTO> findAll() {
        List<CourseDTO> response = webClient
                .get()
                .uri("/api/courses")
                .header("Authorization", "Bearer " + getCurrentToken())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<CourseDTO>>() {})
                .block();

        return response;
    }

    @Override
    public CourseDTO findById(UUID id) {
        CommonResponse<CourseDTO> response = webClient
                .get()
                .uri(String.format("/api/courses/%s", id))
                .header("Authorization", "Bearer " + getCurrentToken())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<CommonResponse<CourseDTO>>() {})
                .block();

        if (response == null || response.getData() == null) {
            return null;
        }
        return response.getData(); 
    }
}
