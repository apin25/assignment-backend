package id.ac.ui.cs.apap.sceleNG.dto.response;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResourceDTO {
    private UUID id;
    private String filename;
    private String content_b64;
    private String owner;
    private Instant createdAt;
    private Instant deletedAt;
    private Set<String> visibilityRole = new HashSet<>();
}
