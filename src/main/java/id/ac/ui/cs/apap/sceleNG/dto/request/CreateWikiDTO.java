package id.ac.ui.cs.apap.sceleNG.dto.request;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateWikiDTO {
    private String title;
    private String content;
    private UUID resource;
    private UUID course;
    private UUID author;
}