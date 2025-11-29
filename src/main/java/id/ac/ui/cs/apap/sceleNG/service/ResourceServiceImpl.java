package id.ac.ui.cs.apap.sceleNG.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import id.ac.ui.cs.apap.sceleNG.dto.response.CommonResponse;
import id.ac.ui.cs.apap.sceleNG.dto.response.ResourceDTO;
import reactor.core.publisher.Mono;

@Service
public class ResourceServiceImpl implements ResourceService {
    private static final Logger log = LoggerFactory.getLogger(ResourceServiceImpl.class);
    private final WebClient webClient;

    public ResourceServiceImpl(WebClient.Builder webClientBuilder){
        this.webClient = webClientBuilder.baseUrl("http://localhost:5914/api/resources/").build();
    }
    
    private String getCurrentToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getCredentials() instanceof String) {
            return (String) authentication.getCredentials();
        }
        return null;
    }
    
    @Override
    public ResourceDTO findById(UUID id) {
        try {
            CommonResponse<ResourceDTO> response = webClient
                    .get()
                    .uri(String.format("files/%s", id.toString()))
                    .header("Authorization", "Bearer " + getCurrentToken())
                    .retrieve()
                    .onStatus(HttpStatus.FORBIDDEN::equals, clientResponse -> {
                        return Mono.empty(); 
                    })
                    .bodyToMono(new ParameterizedTypeReference<CommonResponse<ResourceDTO>>() {})
                    .block();

            if (response == null || response.getData() == null) {
                return null;
            }
            return response.getData();
            
        } catch (WebClientResponseException.Forbidden e) {
            return null;
            
        } catch (WebClientResponseException e) {
            return null;
            
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<ResourceDTO> findAll() {
        try {
            List<ResourceDTO> response = webClient
                    .get()
                    .uri("files")
                    .header("Authorization", "Bearer " + getCurrentToken())
                    .retrieve()
                    .onStatus(HttpStatus.FORBIDDEN::equals, clientResponse -> {
                        return Mono.empty();
                    })
                    .bodyToMono(new ParameterizedTypeReference<List<ResourceDTO>>() {})
                    .block();
            
            return response != null ? response : new ArrayList<>();
            
        } catch (WebClientResponseException.Forbidden e) {
            return new ArrayList<>();
            
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
}