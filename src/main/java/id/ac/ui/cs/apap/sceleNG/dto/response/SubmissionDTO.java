package id.ac.ui.cs.apap.sceleNG.dto.response;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubmissionDTO {
    private UUID id;
    private AssignmentDTO assignment;
    private List<UserDTO> users;
    private String answerText;
    private ResourceDTO answerFile;
    private Instant createdAt;
    private Instant deletedAt;
}
