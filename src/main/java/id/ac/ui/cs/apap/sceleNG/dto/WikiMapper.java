package id.ac.ui.cs.apap.sceleNG.dto;

import id.ac.ui.cs.apap.sceleNG.dto.request.CreateWikiDTO;
import id.ac.ui.cs.apap.sceleNG.dto.request.UpdateWikiDTO;
import id.ac.ui.cs.apap.sceleNG.dto.response.WikiDTO;
import id.ac.ui.cs.apap.sceleNG.model.Wiki;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface WikiMapper {
    Wiki createWikiRequestDTOToWiki(CreateWikiDTO createWikiDTO);
    Wiki updateWikiRequestDTOToWiki(UpdateWikiDTO updateWikiDTO);
    UpdateWikiDTO WikiToUpdateWikiRequestDTO(Wiki courseWiki);
    WikiDTO wikiToReadWikiResponseDTO(Wiki courseWiki);
    void updateWikiFromDTO(UpdateWikiDTO dto, @MappingTarget Wiki courseWiki);
}