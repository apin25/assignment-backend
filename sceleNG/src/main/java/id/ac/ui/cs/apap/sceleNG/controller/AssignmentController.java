package id.ac.ui.cs.apap.sceleNG.controller;

import id.ac.ui.cs.apap.sceleNG.dto.AssignmentMapper;
import id.ac.ui.cs.apap.sceleNG.dto.request.CreateAssignmentDTO;
import id.ac.ui.cs.apap.sceleNG.dto.request.UpdateAssignmentDTO;
import id.ac.ui.cs.apap.sceleNG.dto.response.AssignmentDTO;
import id.ac.ui.cs.apap.sceleNG.model.CourseAssignment;
import id.ac.ui.cs.apap.sceleNG.service.CourseAssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class AssignmentController {

    @Autowired
    private CourseAssignmentService courseAssignmentService;

    @GetMapping("/assignments")
    public List<AssignmentDTO> retrieveAllAssignments() {
        return courseAssignmentService.getAllAssignments();
    }

    @PostMapping("/assignments")
    public AssignmentDTO createAssignment(@RequestBody CreateAssignmentDTO request) {
        return courseAssignmentService.postAssignment(request);
    }

    @PutMapping("/assignments/{id}")
    public AssignmentDTO updateAssignment(
            @PathVariable UUID id,
            @RequestBody UpdateAssignmentDTO request
    ) {
        return courseAssignmentService.putModifyAssignment(id, request);
    }

    @DeleteMapping("/assignments/{id}")
    public ResponseEntity<String> softDeleteAssignment(@PathVariable UUID id) {
        courseAssignmentService.deleteAssignment(id);
        return ResponseEntity.ok("Assignment soft-deleted");
    }
}
