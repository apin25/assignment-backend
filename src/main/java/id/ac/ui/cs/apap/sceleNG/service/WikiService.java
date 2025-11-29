package id.ac.ui.cs.apap.sceleNG.service;

import java.util.List;
import java.util.UUID;
import id.ac.ui.cs.apap.sceleNG.model.Wiki;

public interface WikiService {
    List<Wiki> getAllWikis();
    
    void postWiki(Wiki wiki);

    Wiki getWikiById(UUID id);

    Wiki putModifyWiki(Wiki wiki);

    void deleteWiki(UUID id);
     
    List<Wiki> getFilteredWikis(UUID course, String title, UUID author);
}