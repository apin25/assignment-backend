package id.ac.ui.cs.apap.sceleNG.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssignmentDTO {
    private UUID id;
    private String title;
    private CourseDTO course;
    private String owner;
    private ResourceDTO resource;
    private String text;
    private Instant createdAt;
    private Instant modifiedAt;
    private Instant dueDate;
    private boolean isDeleted;
}