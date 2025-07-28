package id.ac.ui.cs.apap.sceleNG.service;

import java.util.List;
import java.util.UUID;

import id.ac.ui.cs.apap.sceleNG.dto.request.CreateWikiDTO;
import id.ac.ui.cs.apap.sceleNG.dto.request.UpdateWikiDTO;
import id.ac.ui.cs.apap.sceleNG.dto.response.WikiDTO;


public interface WikiService {
    List<WikiDTO> getAllWikis();
    
    WikiDTO postWiki(CreateWikiDTO in);

    WikiDTO getWikiById(UUID id);

    WikiDTO putModifyWiki(UUID id, UpdateWikiDTO in);

    void deleteWiki(UUID id);
}
