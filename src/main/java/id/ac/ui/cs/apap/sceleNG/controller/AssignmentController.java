package id.ac.ui.cs.apap.sceleNG.controller;

import id.ac.ui.cs.apap.sceleNG.dto.AssignmentMapper;
import id.ac.ui.cs.apap.sceleNG.dto.request.CreateAssignmentDTO;
import id.ac.ui.cs.apap.sceleNG.dto.request.UpdateAssignmentDTO;
import id.ac.ui.cs.apap.sceleNG.dto.response.AssignmentDTO;
import id.ac.ui.cs.apap.sceleNG.model.CourseAssignment;
import id.ac.ui.cs.apap.sceleNG.response.CommonResponse;
import id.ac.ui.cs.apap.sceleNG.service.CourseAssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<CommonResponse<List<AssignmentDTO>>> retrieveAllAssignments(
            @RequestParam(required = false) String course,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String owner,
            @RequestParam(required = false) String dueStatus // "onTime" / "overDue" / null
    ) {
        List<AssignmentDTO> assignments = courseAssignmentService.getFilteredAssignments(course, title, owner, dueStatus);
        CommonResponse<List<AssignmentDTO>> response = new CommonResponse<>(true, assignments, null);
        return ResponseEntity.ok(response);
    }



    @PostMapping("/assignments")
    public ResponseEntity<CommonResponse<AssignmentDTO>> createAssignment(@RequestBody CreateAssignmentDTO request) {
        AssignmentDTO created = courseAssignmentService.postAssignment(request);
        return ResponseEntity.ok(new CommonResponse<>(true, created, null));
    }


    @PutMapping("/assignments/{id}")
    public ResponseEntity<CommonResponse<AssignmentDTO>> updateAssignment(
            @PathVariable UUID id,
            @RequestBody UpdateAssignmentDTO request
    ) {
        AssignmentDTO updated = courseAssignmentService.putModifyAssignment(id, request);
        CommonResponse<AssignmentDTO> response = new CommonResponse<>(true, updated, null);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/assignments/{id}/delete")
    public ResponseEntity<CommonResponse<String>> softDeleteAssignment(@PathVariable UUID id) {
        courseAssignmentService.deleteAssignment(id);
        CommonResponse<String> response = new CommonResponse<>(true, "Assignment soft-deleted", null);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/assignments/{id}")
    public ResponseEntity<CommonResponse<AssignmentDTO>> getAssignmentDetail(@PathVariable UUID id) {
        AssignmentDTO assignment = courseAssignmentService.getAssignmentById(id);

        if (assignment == null) {
            CommonResponse<AssignmentDTO> notFoundResponse = new CommonResponse<>(false, null, "Assignment not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(notFoundResponse);
        }
        CommonResponse<AssignmentDTO> successResponse = new CommonResponse<>(true, assignment, null);
        return ResponseEntity.ok(successResponse);
    }


}
