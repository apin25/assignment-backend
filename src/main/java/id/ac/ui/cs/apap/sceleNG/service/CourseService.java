package id.ac.ui.cs.apap.sceleNG.service;

import id.ac.ui.cs.apap.sceleNG.dto.response.CourseDTO;
import java.util.UUID;
import java.util.List;

public interface CourseService {
    List<CourseDTO> findAll();
    CourseDTO findById(UUID id);
}
