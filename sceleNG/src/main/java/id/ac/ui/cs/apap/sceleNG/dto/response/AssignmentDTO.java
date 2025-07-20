package id.ac.ui.cs.apap.sceleNG.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssignmentDTO {
    private UUID id;
    private String title;
    private String course;
    private String owner;
    private Instant createdAt;
    private Instant modifiedAt;
    private Instant dueDate;
}