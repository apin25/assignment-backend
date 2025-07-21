package id.ac.ui.cs.apap.sceleNG.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAssignmentDTO {
    private String title;
    private String course;
    private String owner;
    private Instant dueDate;
}