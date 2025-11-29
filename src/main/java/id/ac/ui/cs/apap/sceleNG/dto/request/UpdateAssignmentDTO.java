package id.ac.ui.cs.apap.sceleNG.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAssignmentDTO {
    private String title;
    private UUID course;
    private String owner;
    private UUID resource;
    private String text;
    private Instant dueDate;
}