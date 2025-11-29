package id.ac.ui.cs.apap.sceleNG.service;

import java.util.List;
import java.util.UUID;

import id.ac.ui.cs.apap.sceleNG.dto.request.CreateAssignmentDTO;
import id.ac.ui.cs.apap.sceleNG.dto.request.UpdateAssignmentDTO;
import id.ac.ui.cs.apap.sceleNG.dto.response.AssignmentDTO;
import id.ac.ui.cs.apap.sceleNG.model.CourseAssignment;

public interface CourseAssignmentService {

    List<CourseAssignment> getAllAssignments();

    void postAssignment(CourseAssignment courseAssignment);

    CourseAssignment getAssignmentById(UUID id);

    CourseAssignment putModifyAssignment(CourseAssignment courseAssignment);

    void deleteAssignment(UUID id);

    List<CourseAssignment> getFilteredAssignments(UUID course, String title, String owner, String dueStatus);
}