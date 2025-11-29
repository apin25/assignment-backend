package id.ac.ui.cs.apap.sceleNG.dto;

import id.ac.ui.cs.apap.sceleNG.dto.request.CreateWikiDTO;
import id.ac.ui.cs.apap.sceleNG.dto.request.UpdateWikiDTO;
import id.ac.ui.cs.apap.sceleNG.dto.response.CourseDTO;
import id.ac.ui.cs.apap.sceleNG.dto.response.WikiDTO;
import id.ac.ui.cs.apap.sceleNG.dto.response.ResourceDTO;
import id.ac.ui.cs.apap.sceleNG.dto.response.UserDTO;
import id.ac.ui.cs.apap.sceleNG.model.Wiki;

import java.util.UUID;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface WikiMapper {
    Wiki createWikiRequestDTOToWiki(CreateWikiDTO createWikiDTO);
    Wiki updateWikiRequestDTOToWiki(UpdateWikiDTO updateWikiDTO);
    UpdateWikiDTO WikiToUpdateWikiRequestDTO(Wiki wiki);
    Wiki wikiToReadWikiResponseDTO(Wiki wiki);
    void updateWikiFromDTO(UpdateWikiDTO dto, @MappingTarget Wiki wiki);
    WikiDTO wikiToWikiDTO(Wiki wiki);

    default CourseDTO mapCourse(UUID courseId) {  
        if (courseId == null) return null;
        CourseDTO dto = new CourseDTO();
        dto.setId(courseId);
        return dto;
    }

    default UserDTO mapUser(UUID userId) {  
        if (userId == null) return null;
        UserDTO dto = new UserDTO();
        dto.setId(userId);
        return dto;
    }
    
    default ResourceDTO mapResource(UUID resourceId) {
        if (resourceId == null) return null;
        ResourceDTO dto = new ResourceDTO();
        dto.setId(resourceId);
        return dto;
    }
}