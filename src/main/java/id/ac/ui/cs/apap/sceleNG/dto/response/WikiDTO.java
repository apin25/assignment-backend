package id.ac.ui.cs.apap.sceleNG.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WikiDTO {
    private UUID id;
    private String title;
    private String content;
    private String resourceId;
    private String courseId;
    private UUID author;
    private Instant createdAt;
    private Instant modifiedAt;
    private Instant deletedAt;
}
