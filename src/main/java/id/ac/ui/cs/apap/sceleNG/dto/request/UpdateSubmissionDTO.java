package id.ac.ui.cs.apap.sceleNG.dto.request;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateSubmissionDTO {
    private UUID assignment;
    private List<UUID> users;
    private String answerText;
    private UUID answerFile;
}
