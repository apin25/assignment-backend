package id.ac.ui.cs.apap.sceleNG.dto.response;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CourseDTO {
    private UUID id;
    private String code;
    private String name;
    private String description;
    private Instant createdAt;
    private Instant deletedAt;
}
