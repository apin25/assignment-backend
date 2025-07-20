package id.ac.ui.cs.apap.sceleNG.service;

import id.ac.ui.cs.apap.sceleNG.dto.response.AssignmentDTO;
import id.ac.ui.cs.apap.sceleNG.dto.request.CreateAssignmentDTO;
import id.ac.ui.cs.apap.sceleNG.dto.request.UpdateAssignmentDTO;

import java.util.List;
import java.util.UUID;

public interface CourseAssignmentService {

    List<AssignmentDTO> getAllAssignments();

    List<AssignmentDTO> getAssignmentByCourse(String course);

    List<AssignmentDTO> getAssignmentByOwner(String owner);

    List<AssignmentDTO> getDueAssignments();

    List<AssignmentDTO> getOngoingAssignments();

    List<AssignmentDTO> getAssignmentsContaining(String q);

    AssignmentDTO postAssignment(CreateAssignmentDTO in);

    AssignmentDTO putModifyAssignment(UUID id, UpdateAssignmentDTO in);

    void deleteAssignment(UUID id);
}