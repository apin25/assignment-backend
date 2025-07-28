package id.ac.ui.cs.apap.sceleNG.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateWikiDTO {
    private String title;
    private String content;
    private String resourceId;
    private String courseId;
    private UUID author;
}
