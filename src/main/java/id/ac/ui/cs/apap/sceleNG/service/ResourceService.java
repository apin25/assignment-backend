package id.ac.ui.cs.apap.sceleNG.service;

import java.util.UUID;
import java.util.List;

import id.ac.ui.cs.apap.sceleNG.dto.response.ResourceDTO;

public interface ResourceService {
    List<ResourceDTO> findAll();
    ResourceDTO findById(UUID id);
}
