package id.ac.ui.cs.apap.sceleNG.service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import id.ac.ui.cs.apap.sceleNG.dto.AssignmentMapper;
import id.ac.ui.cs.apap.sceleNG.dto.request.CreateAssignmentDTO;
import id.ac.ui.cs.apap.sceleNG.dto.request.UpdateAssignmentDTO;
import id.ac.ui.cs.apap.sceleNG.dto.response.AssignmentDTO;
import id.ac.ui.cs.apap.sceleNG.model.CourseAssignment;
import id.ac.ui.cs.apap.sceleNG.repository.AssignmentDb;

@Service
public class CourseAssignmentServiceImpl implements CourseAssignmentService {

    @Autowired
    private AssignmentDb assignmentDb;

    @Autowired
    private AssignmentMapper assignmentMapper;

    @Override
    public List<AssignmentDTO> getAllAssignments() {
        return assignmentDb.getAllAssignments().stream()
                .map(assignmentMapper::assignmentToReadAssignmentResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<AssignmentDTO> getAssignmentByCourse(String course) {
        return assignmentDb.findByCourse(course).stream()
                .map(assignmentMapper::assignmentToReadAssignmentResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<AssignmentDTO> getAssignmentByOwner(String owner) {
        return assignmentDb.findByOwner(owner).stream()
                .map(assignmentMapper::assignmentToReadAssignmentResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<AssignmentDTO> getDueAssignments() {
        Instant now = Instant.now();
        return assignmentDb.findDueAssignments(now).stream()
                .map(assignmentMapper::assignmentToReadAssignmentResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<AssignmentDTO> getOngoingAssignments() {
        Instant now = Instant.now();
        return assignmentDb.findOngoingAssignments(now).stream()
                .map(assignmentMapper::assignmentToReadAssignmentResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<AssignmentDTO> getAssignmentsContaining(String q) {
        return assignmentDb.findAssignmentsContaining(q).stream()
                .map(assignmentMapper::assignmentToReadAssignmentResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public AssignmentDTO postAssignment(CreateAssignmentDTO in) {
        CourseAssignment assignment = assignmentMapper.createAssignmentRequestDTOToAssignment(in);
        CourseAssignment saved = assignmentDb.save(assignment);
        return assignmentMapper.assignmentToReadAssignmentResponseDTO(saved);
    }

    @Override
    public AssignmentDTO putModifyAssignment(UUID id, UpdateAssignmentDTO in) {
        CourseAssignment existing = assignmentDb.findById(id)
                .orElseThrow(() -> new RuntimeException("Assignment not found"));

        assignmentMapper.updateAssignmentFromDTO(in, existing);

        CourseAssignment saved = assignmentDb.save(existing);
        return assignmentMapper.assignmentToReadAssignmentResponseDTO(saved);
    }

    @Override
    public void deleteAssignment(UUID id) {
        CourseAssignment assignment = assignmentDb.findById(id)
                .orElseThrow(() -> new RuntimeException("Assignment not found"));

        assignment.setDeleted(true);
        assignmentDb.save(assignment);
    }
    @Override
    public AssignmentDTO getAssignmentById(UUID id) {
        CourseAssignment assignment = assignmentDb.findById(id)
            .orElseThrow(() -> new RuntimeException("Assignment not found"));
        return assignmentMapper.assignmentToReadAssignmentResponseDTO(assignment);
    }
    @Override
    public List<AssignmentDTO> getFilteredAssignments(String course, String title, String owner, String dueStatus) {
        List<CourseAssignment> allAssignments = assignmentDb.getAllAssignments();
        Instant now = Instant.now();

        return allAssignments.stream()
                .filter(a -> course == null || a.getCourse().equalsIgnoreCase(course))
                .filter(a -> title == null || a.getTitle().toLowerCase().contains(title.toLowerCase()))
                .filter(a -> owner == null || a.getOwner().equalsIgnoreCase(owner))
                .filter(a -> {
                    if (dueStatus == null) return true;
                    if (dueStatus.equalsIgnoreCase("onTime")) {
                        return a.getDueDate().isAfter(now) || a.getDueDate().equals(now);
                    } else if (dueStatus.equalsIgnoreCase("overDue")) {
                        return a.getDueDate().isBefore(now);
                    }
                    return true;
                })
                .map(assignmentMapper::assignmentToReadAssignmentResponseDTO)
                .toList();
    }
}

