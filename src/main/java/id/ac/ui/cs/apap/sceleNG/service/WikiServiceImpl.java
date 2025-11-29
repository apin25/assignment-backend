package id.ac.ui.cs.apap.sceleNG.service;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import id.ac.ui.cs.apap.sceleNG.model.Wiki;
import id.ac.ui.cs.apap.sceleNG.repository.WikiDb;

@Service
public class WikiServiceImpl implements WikiService {

    @Autowired
    private WikiDb wikiDb;

    @Override
    public List<Wiki> getAllWikis() {
        return wikiDb.getAllWikis();
    }

    @Override
    public void postWiki(Wiki wiki) {
        wikiDb.save(wiki);
    }

    @Override
    public Wiki putModifyWiki(Wiki wiki) {
        wikiDb.save(wiki);
        return wiki;
    }

    @Override
    public void deleteWiki(UUID id) {
        Wiki wiki = wikiDb.findById(id)
            .orElseThrow(() -> new RuntimeException("Wiki not found"));

        wiki.setDeleted(true);
        wikiDb.save(wiki);
    }

    @Override
    public Wiki getWikiById(UUID id) {
        return wikiDb.findById(id).orElse(null);
    }

    @Override
    public List<Wiki> getFilteredWikis(UUID course, String title, UUID author) {
        List<Wiki> allWikis = wikiDb.getAllWikis();

        return allWikis.stream()
            .filter(w -> course == null || w.getCourse().equals(course))
            .filter(w -> title == null || w.getTitle().toLowerCase().contains(title.toLowerCase()))
            .filter(w -> author == null || w.getAuthor().equals(author))
            .toList();
    }
}

