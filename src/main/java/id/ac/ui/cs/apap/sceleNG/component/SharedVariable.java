package id.ac.ui.cs.apap.sceleNG.component;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@Getter
@Setter
public class SharedVariable {
    private String adminToken;
    private String dosenToken;
}

