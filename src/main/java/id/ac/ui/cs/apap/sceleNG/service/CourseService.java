package id.ac.ui.cs.apap.sceleNG.service;

import java.util.List;
import java.util.UUID;

import id.ac.ui.cs.apap.sceleNG.dto.response.CourseDTO;

public interface CourseService {
    List<CourseDTO> findAll();
    CourseDTO findById(UUID id);
}