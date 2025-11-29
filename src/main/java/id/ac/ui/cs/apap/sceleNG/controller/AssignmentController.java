package id.ac.ui.cs.apap.sceleNG.controller;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import id.ac.ui.cs.apap.sceleNG.dto.response.ResourceDTO;
import id.ac.ui.cs.apap.sceleNG.dto.response.CourseDTO;
import id.ac.ui.cs.apap.sceleNG.dto.AssignmentMapper;
import id.ac.ui.cs.apap.sceleNG.dto.request.CreateAssignmentDTO;
import id.ac.ui.cs.apap.sceleNG.dto.request.UpdateAssignmentDTO;
import id.ac.ui.cs.apap.sceleNG.dto.response.AssignmentDTO;
import id.ac.ui.cs.apap.sceleNG.dto.response.CommonResponse;
import id.ac.ui.cs.apap.sceleNG.model.CourseAssignment;
import id.ac.ui.cs.apap.sceleNG.service.AuthService;
import id.ac.ui.cs.apap.sceleNG.service.CourseAssignmentService;
import id.ac.ui.cs.apap.sceleNG.service.CourseService;
import id.ac.ui.cs.apap.sceleNG.service.ResourceService;

@RestController
@RequestMapping("/api")
public class AssignmentController {

    @Autowired
    private CourseAssignmentService courseAssignmentService;

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private AssignmentMapper assignmentMapper;

    @Autowired
    private AuthService authService;

    @GetMapping("/assignments")
    public ResponseEntity<CommonResponse<List<AssignmentDTO>>> retrieveAllAssignments(
            @RequestParam(required = false) UUID course,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String owner,
            @RequestParam(required = false) String status
    ) {
        List<CourseAssignment> assignments;
        if (course == null && title == null && owner == null && status == null) {
            assignments = courseAssignmentService.getAllAssignments();
        } else {
            assignments = courseAssignmentService.getFilteredAssignments(course, title, owner, status);
        }

        List<AssignmentDTO> assignmentDTO = assignments.stream()
            .filter(assignment -> assignment.getResource() != null) 
            .map(assignment -> {
                ResourceDTO resourceDto = resourceService.findById(assignment.getResource());
                if (resourceDto == null) {
                    return null; 
                }

                AssignmentDTO dto = new AssignmentDTO();
                dto.setId(assignment.getId());
                dto.setTitle(assignment.getTitle());
                dto.setOwner(assignment.getOwner());
                dto.setText(assignment.getText());
                dto.setDueDate(assignment.getDueDate());
                dto.setCreatedAt(assignment.getCreatedAt());
                dto.setModifiedAt(assignment.getModifiedAt());
                dto.setDeleted(assignment.isDeleted());

                CourseDTO courseDto = courseService.findById(assignment.getCourse());
                dto.setCourse(courseDto);
                dto.setResource(resourceDto);

                return dto;
            })
            .filter(Objects::nonNull)
            .collect(Collectors.toList());


        CommonResponse<List<AssignmentDTO>> response = new CommonResponse<>(true, assignmentDTO, null);
        return ResponseEntity.ok(response);
    }


    @PostMapping("/assignments")
    public ResponseEntity<CommonResponse<AssignmentDTO>> createAssignment(@RequestBody CreateAssignmentDTO request) {
        CourseAssignment courseAssignment = assignmentMapper.createAssignmentRequestDTOToAssignment(request);

        UUID courseId = request.getCourse();
        UUID resourceId = request.getResource();

        CourseDTO course = courseService.findById(courseId);
        if (course == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new CommonResponse<>(false, null, "Course not found"));
        }
        courseAssignment.setCourse(courseId);
        courseAssignment.setResource(resourceId);

        courseAssignmentService.postAssignment(courseAssignment);
        AssignmentDTO assignmentDTO = assignmentMapper.assignmentToAssignmentDTO(courseAssignment);
        CommonResponse<AssignmentDTO> response = new CommonResponse<>(true, assignmentDTO, null);


        return ResponseEntity.ok(response);
    }

    @PutMapping("/assignments/{id}")
    public ResponseEntity<CommonResponse<AssignmentDTO>> updateAssignment(
            @PathVariable UUID id,
            @RequestBody UpdateAssignmentDTO request
    ) {
        CourseAssignment oldAssignment = courseAssignmentService.getAssignmentById(id);
        if (oldAssignment == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new CommonResponse<>(false, null, "Assignment not found"));
        }
        UUID courseId = request.getCourse();
        CourseDTO course = courseService.findById(courseId);
        if (course == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new CommonResponse<>(false, null, "Course not found"));
        }

        oldAssignment.setTitle(request.getTitle());
        oldAssignment.setText(request.getText());
        oldAssignment.setDueDate(request.getDueDate());
        oldAssignment.setCourse(courseId);
        oldAssignment.setOwner(request.getOwner());
        oldAssignment.setResource(request.getResource());

        courseAssignmentService.putModifyAssignment(oldAssignment);

        AssignmentDTO assignmentDTO = assignmentMapper.assignmentToAssignmentDTO(oldAssignment);
        return ResponseEntity.ok(new CommonResponse<>(true, assignmentDTO, null));
    }

    @PutMapping("/assignments/{id}/delete")
    public ResponseEntity<CommonResponse<String>> softDeleteAssignment(@PathVariable UUID id) {
        courseAssignmentService.deleteAssignment(id);
        CommonResponse<String> response = new CommonResponse<>(true, "Assignment soft-deleted", null);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/assignments/{id}")
    public ResponseEntity<CommonResponse<AssignmentDTO>> getAssignmentDetail(@PathVariable UUID id) {
        CourseAssignment assignment = courseAssignmentService.getAssignmentById(id);

        if (assignment == null) {
            CommonResponse<AssignmentDTO> notFoundResponse = new CommonResponse<>(false, null, "Assignment not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(notFoundResponse);
        }

        AssignmentDTO dto = new AssignmentDTO();
        dto.setId(assignment.getId());
        dto.setTitle(assignment.getTitle());
        dto.setOwner(assignment.getOwner());
        dto.setText(assignment.getText());
        dto.setDueDate(assignment.getDueDate());
        dto.setCreatedAt(assignment.getCreatedAt());
        dto.setModifiedAt(assignment.getModifiedAt());
        dto.setDeleted(assignment.isDeleted());

        CourseDTO course = courseService.findById(assignment.getCourse());
        dto.setCourse(course);

        ResourceDTO resource = resourceService.findById(assignment.getResource());
        dto.setResource(resource);
        
        CommonResponse<AssignmentDTO> successResponse = new CommonResponse<>(true, dto, null);
        return ResponseEntity.ok(successResponse);
    }
    // @PutMapping("assignments/{id}/answer")
    // public ResponseEntity<CommonResponse<AssignmentDTO>> assignmentAnswer(
    //         @PathVariable UUID id,
    //         @RequestBody AnswerAssignmentDTO answerDTO) {

    //     CourseAssignment oldAssignment = courseAssignmentService.getAssignmentById(id);
    //     if (oldAssignment == null) {
    //         return ResponseEntity.status(HttpStatus.NOT_FOUND)
    //                 .body(new CommonResponse<>(false, null, "Assignment not found"));
    //     }

    //     oldAssignment.setAnswerFile(answerDTO.getAnswerFile());
    //     oldAssignment.setAnswerText(answerDTO.getAnswerText());

    //     List<UUID> users = answerDTO.getUsers().stream()
    //             .filter(userId -> authService.findById(userId) != null)
    //             .collect(Collectors.toList());

    //     oldAssignment.setUsers(users);

    //     AssignmentDTO assignmentDTO = assignmentMapper.assignmentToAssignmentDTO(oldAssignment);
    //     return ResponseEntity.ok(new CommonResponse<>(true, assignmentDTO, null));
    // }
}