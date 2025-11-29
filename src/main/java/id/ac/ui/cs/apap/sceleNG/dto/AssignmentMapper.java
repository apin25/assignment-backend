package id.ac.ui.cs.apap.sceleNG.dto;
import id.ac.ui.cs.apap.sceleNG.dto.request.CreateAssignmentDTO;
import id.ac.ui.cs.apap.sceleNG.dto.request.UpdateAssignmentDTO;
import id.ac.ui.cs.apap.sceleNG.dto.response.AssignmentDTO;
import id.ac.ui.cs.apap.sceleNG.dto.response.CourseDTO;
import id.ac.ui.cs.apap.sceleNG.dto.response.ResourceDTO;
import id.ac.ui.cs.apap.sceleNG.dto.response.UserDTO;
import id.ac.ui.cs.apap.sceleNG.model.CourseAssignment;

import java.util.UUID;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AssignmentMapper {
    CourseAssignment createAssignmentRequestDTOToAssignment(CreateAssignmentDTO createAssignmentDTO);
    CourseAssignment updateAssignmentRequestDTOToAssignment(UpdateAssignmentDTO updateAssignmentDTO);
    UpdateAssignmentDTO assignmentToUpdateAssignmentRequestDTO(UpdateAssignmentDTO request);
    CourseAssignment assignmentToReadAssignmentResponseDTO(CourseAssignment courseAssignment);
    AssignmentDTO assignmentToAssignmentDTO(CourseAssignment courseAssignment);
    void updateAssignmentFromDTO(UpdateAssignmentDTO dto, @MappingTarget CourseAssignment courseAssignment);

    default CourseDTO map(UUID courseId) {
        if (courseId == null) return null;
        CourseDTO dto = new CourseDTO();
        dto.setId(courseId);
        return dto;
    }
    default ResourceDTO mapResource(UUID resourceId) {
        if (resourceId == null) return null;
        ResourceDTO dto = new ResourceDTO();
        dto.setId(resourceId);
        return dto;
    }
}
