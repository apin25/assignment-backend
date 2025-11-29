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

import id.ac.ui.cs.apap.sceleNG.dto.WikiMapper;
import id.ac.ui.cs.apap.sceleNG.dto.request.CreateWikiDTO;
import id.ac.ui.cs.apap.sceleNG.dto.request.UpdateWikiDTO;
import id.ac.ui.cs.apap.sceleNG.dto.response.WikiDTO;
import id.ac.ui.cs.apap.sceleNG.model.Wiki;
import id.ac.ui.cs.apap.sceleNG.service.AuthService;
import id.ac.ui.cs.apap.sceleNG.service.CourseService;
import id.ac.ui.cs.apap.sceleNG.service.ResourceService;
import id.ac.ui.cs.apap.sceleNG.service.WikiService;
import id.ac.ui.cs.apap.sceleNG.dto.response.CommonResponse;
import id.ac.ui.cs.apap.sceleNG.dto.response.CourseDTO;
import id.ac.ui.cs.apap.sceleNG.dto.response.ResourceDTO;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import id.ac.ui.cs.apap.sceleNG.dto.response.UserDTO;

@RestController
@RequestMapping("/api")
public class WikiController {

    @Autowired
    private AuthService authService;

    @Autowired
    private WikiService wikiService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private WikiMapper wikiMapper;

    @PostMapping("/wikis")
    public ResponseEntity<CommonResponse<WikiDTO>> createWiki(@RequestBody CreateWikiDTO request) {
        Wiki wiki = wikiMapper.createWikiRequestDTOToWiki(request);

        UUID courseId = request.getCourse();
        UUID resourceId = request.getResource();

        wiki.setCourse(courseId); 
        wiki.setResource(resourceId);  

        wikiService.postWiki(wiki);
        WikiDTO wikiDTO = wikiMapper.wikiToWikiDTO(wiki);
        CommonResponse<WikiDTO> response = new CommonResponse<>(true, wikiDTO, null);

        return ResponseEntity.ok(response);
    }

   @GetMapping("/wikis")
    public ResponseEntity<CommonResponse<List<WikiDTO>>> retrieveAllWikis(
            @RequestParam(required = false) UUID course,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) UUID author
    ) {
        List<Wiki> wikis;
        if (course == null && title == null && author == null) {
            wikis = wikiService.getAllWikis();
        } else {
            wikis = wikiService.getFilteredWikis(course, title, author);
        }

        List<WikiDTO> wikiDTO = wikis.stream()
            .filter(wiki -> wiki.getResource() != null)
            .map(wiki -> {
                ResourceDTO resource = resourceService.findById(wiki.getResource());
                if (resource == null) return null;

                WikiDTO dto = new WikiDTO();
                dto.setId(wiki.getId());
                dto.setTitle(wiki.getTitle());
                dto.setContent(wiki.getContent());
                dto.setCreatedAt(wiki.getCreatedAt());
                dto.setModifiedAt(wiki.getModifiedAt());

                CourseDTO courseId = courseService.findById(wiki.getCourse());
                dto.setCourse(courseId);

                dto.setResource(resource);

                UserDTO user = authService.findById(wiki.getAuthor());
                dto.setAuthor(user);

                return dto;
            })
            .filter(Objects::nonNull) 
            .collect(Collectors.toList());

        CommonResponse<List<WikiDTO>> response = new CommonResponse<>(true, wikiDTO, null);
        return ResponseEntity.ok(response);
    }


       @PutMapping("/wikis/{id}")
    public ResponseEntity<CommonResponse<WikiDTO>> updateWiki(
            @PathVariable UUID id,
            @RequestBody UpdateWikiDTO request
    ) {
        Wiki oldWiki = wikiService.getWikiById(id);
        if (oldWiki == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new CommonResponse<>(false, null, "Wiki not found"));
        }

        UUID courseId = request.getCourse();
        UUID resourceId = request.getResource();
        UUID userId = request.getAuthor();

        oldWiki.setTitle(request.getTitle());
        oldWiki.setContent(request.getContent());
        oldWiki.setCourse(courseId);
        oldWiki.setResource(resourceId);
        oldWiki.setAuthor(userId);

        wikiService.putModifyWiki(oldWiki);

        WikiDTO wikiDTO = wikiMapper.wikiToWikiDTO(oldWiki);
        CommonResponse<WikiDTO> response = new CommonResponse<>(true, wikiDTO, null);

        return ResponseEntity.ok(response);
    }
    
    @PutMapping("/wikis/{id}/delete")
    public ResponseEntity<CommonResponse<String>> softDeleteWiki(@PathVariable UUID id) {
        wikiService.deleteWiki(id);
        CommonResponse<String> response = new CommonResponse<>(true, "Wiki soft-deleted", null);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/wikis/{id}")
    public ResponseEntity<CommonResponse<WikiDTO>> getWikiDetail(@PathVariable UUID id) {
        Wiki wiki = wikiService.getWikiById(id);

        if (wiki == null) {
            CommonResponse<WikiDTO> notFoundResponse = new CommonResponse<>(false, null, "Wiki not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(notFoundResponse);
        }

        WikiDTO dto = new WikiDTO();
        dto.setId(wiki.getId());
        dto.setTitle(wiki.getTitle());
        dto.setContent(wiki.getContent());
        dto.setCreatedAt(wiki.getCreatedAt());
        dto.setModifiedAt(wiki.getModifiedAt());

        CourseDTO course = courseService.findById(wiki.getCourse());
        dto.setCourse(course);

        ResourceDTO resource = resourceService.findById(wiki.getResource());
        dto.setResource(resource);

        UserDTO user = authService.findById(wiki.getAuthor());
        dto.setAuthor(user);
        
        CommonResponse<WikiDTO> successResponse = new CommonResponse<>(true, dto, null);
        return ResponseEntity.ok(successResponse);
    }
}