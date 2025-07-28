package id.ac.ui.cs.apap.sceleNG.service;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import id.ac.ui.cs.apap.sceleNG.component.SharedVariable;
import id.ac.ui.cs.apap.sceleNG.dto.response.CommonResponse;
import id.ac.ui.cs.apap.sceleNG.dto.response.CourseDTO;

@Service
public class CourseServiceImpl implements CourseService {
    private final WebClient webClient;

    @Autowired
    private SharedVariable sharedVariable;

    public CourseServiceImpl(WebClient.Builder webClientBuilder){
        this.webClient = webClientBuilder.baseUrl("http://localhost:8085").build();
    }
        @Override
    public List<CourseDTO> findAll() {
        List<CourseDTO> response = webClient
                .get()
                .uri("api/courses")
                .header("Authorization", "Bearer" + sharedVariable.getAdminToken())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<CourseDTO>>() {
                })
                .block();

        return response;
    }

    @Override
    public CourseDTO findById(UUID id) {

    CommonResponse<CourseDTO> response = webClient
            .get()
            .uri(String.format("apicCourses/%s", id.toString()))
            .header("Authorization", "Bearer " + sharedVariable.getAdminToken())
            .retrieve()
            .bodyToMono(new ParameterizedTypeReference<CommonResponse<CourseDTO>>() {})
            .block();

    if (response == null || response.getData() == null) {
        return null;
    }
    return response.getData(); 
    }

}
