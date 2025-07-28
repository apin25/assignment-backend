package id.ac.ui.cs.apap.sceleNG.service;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import id.ac.ui.cs.apap.sceleNG.dto.WikiMapper;
import id.ac.ui.cs.apap.sceleNG.dto.request.CreateWikiDTO;
import id.ac.ui.cs.apap.sceleNG.dto.request.UpdateWikiDTO;
import id.ac.ui.cs.apap.sceleNG.dto.response.WikiDTO;
import id.ac.ui.cs.apap.sceleNG.model.Wiki;
import id.ac.ui.cs.apap.sceleNG.repository.WikiDb;

@Service
public class WikiServiceImpl implements WikiService {
    @Autowired
    private WikiDb wikiDb;

    @Autowired
    private WikiMapper wikiMapper;

    @Override
    public List<WikiDTO> getAllWikis() {
        return wikiDb.getAllWikis().stream()
                .map(wikiMapper::wikiToReadWikiResponseDTO)
                .collect(Collectors.toList());
    }
    @Override
    public WikiDTO postWiki(CreateWikiDTO in) {
        Wiki wiki = wikiMapper.createWikiRequestDTOToWiki(in);
        Wiki saved = wikiDb.save(wiki);
        return wikiMapper.wikiToReadWikiResponseDTO(saved);
    }

    @Override
    public WikiDTO putModifyWiki(UUID id, UpdateWikiDTO in) {
        Wiki existing = wikiDb.findById(id)
                .orElseThrow(() -> new RuntimeException("Wiki not found"));

        wikiMapper.updateWikiFromDTO(in, existing);

        Wiki saved = wikiDb.save(existing);
        return wikiMapper.wikiToReadWikiResponseDTO(saved);
    }

    @Override
    public WikiDTO getWikiById(UUID id) {
        Wiki wiki = wikiDb.findById(id)
            .orElseThrow(() -> new RuntimeException("Wiki not found"));
        return wikiMapper.wikiToReadWikiResponseDTO(wiki);
    }

    @Override
    public void deleteWiki(UUID id) {
        Wiki wiki = wikiDb.findById(id)
                .orElseThrow(() -> new RuntimeException("Wiki not found"));

        wiki.setDeleted(true);
        wikiDb.save(wiki);
    }
}
