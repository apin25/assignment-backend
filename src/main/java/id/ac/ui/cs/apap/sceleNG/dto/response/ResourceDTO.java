package id.ac.ui.cs.apap.sceleNG.dto.response;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

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
}
