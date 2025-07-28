package id.ac.ui.cs.apap.sceleNG.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import id.ac.ui.cs.apap.sceleNG.dto.request.CreateWikiDTO;
import id.ac.ui.cs.apap.sceleNG.dto.request.UpdateWikiDTO;
import id.ac.ui.cs.apap.sceleNG.dto.response.WikiDTO;
import id.ac.ui.cs.apap.sceleNG.service.WikiService;
import id.ac.ui.cs.apap.sceleNG.dto.response.CommonResponse;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class WikiController {

    @Autowired
    private WikiService wikiService;

    @PostMapping("/wiki")
    public ResponseEntity<CommonResponse<WikiDTO>> createWiki(@RequestBody CreateWikiDTO request) {
        WikiDTO created = wikiService.postWiki(request);
        return ResponseEntity.ok(new CommonResponse<>(true, created, null));
    }

    @PutMapping("/wiki/{id}")
    public ResponseEntity<CommonResponse<WikiDTO>> updateWiki(
            @PathVariable UUID id,
            @RequestBody UpdateWikiDTO request
    ) {
        WikiDTO updated = wikiService.putModifyWiki(id, request);
        CommonResponse<WikiDTO> response = new CommonResponse<>(true, updated, null);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/wiki/{id}/delete")
    public ResponseEntity<CommonResponse<String>> softDeleteWiki(@PathVariable UUID id) {
        wikiService.deleteWiki(id);
        CommonResponse<String> response = new CommonResponse<>(true, "Wiki soft-deleted", null);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/wiki/{id}")
    public ResponseEntity<CommonResponse<WikiDTO>> getWikiDetail(@PathVariable UUID id) {
        WikiDTO wiki = wikiService.getWikiById(id);

        if (wiki == null) {
            CommonResponse<WikiDTO> notFoundResponse = new CommonResponse<>(false, null, "Wiki not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(notFoundResponse);
        }
        CommonResponse<WikiDTO> successResponse = new CommonResponse<>(true, wiki, null);
        return ResponseEntity.ok(successResponse);
    }
}
