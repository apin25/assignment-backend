package id.ac.ui.cs.apap.sceleNG.service;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import id.ac.ui.cs.apap.sceleNG.component.SharedVariable;
import id.ac.ui.cs.apap.sceleNG.dto.response.CommonResponse;
import id.ac.ui.cs.apap.sceleNG.dto.response.ResourceDTO;

@Service
public class ResourceServiceImpl implements ResourceService {
    private final WebClient webClient;

    @Autowired
    private SharedVariable sharedVariable;

    public ResourceServiceImpl(WebClient.Builder webClientBuilder){
        this.webClient = webClientBuilder.baseUrl("http://localhost:8085").build();
    }
    @Override
    public List<ResourceDTO> findAll() {
        List<ResourceDTO> response = webClient
                .get()
                .uri("api/resources")
                .header("Authorization", "Bearer" + sharedVariable.getAdminToken())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<ResourceDTO>>() {
                })
                .block();

        return response;
    }

    @Override
    public ResourceDTO findById(UUID id) {

    CommonResponse<ResourceDTO> response = webClient
            .get()
            .uri(String.format("api/resources/%s", id.toString()))
            .header("Authorization", "Bearer " + sharedVariable.getAdminToken())
            .retrieve()
            .bodyToMono(new ParameterizedTypeReference<CommonResponse<ResourceDTO>>() {})
            .block();

    if (response == null || response.getData() == null) {
        return null;
    }
    return response.getData(); 
    }

}
