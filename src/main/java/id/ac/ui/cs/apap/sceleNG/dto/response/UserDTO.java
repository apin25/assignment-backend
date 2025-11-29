package id.ac.ui.cs.apap.sceleNG.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private UUID id;
    private String username;
    private String password;
    private String email;
    private String name;
    private String role;
    private LocalDateTime createdAt;
    private LocalDateTime deletedAt;
}
