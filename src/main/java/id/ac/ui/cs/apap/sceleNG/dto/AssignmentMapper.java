package id.ac.ui.cs.apap.sceleNG.dto;
import id.ac.ui.cs.apap.sceleNG.dto.request.CreateAssignmentDTO;
import id.ac.ui.cs.apap.sceleNG.dto.request.UpdateAssignmentDTO;
import id.ac.ui.cs.apap.sceleNG.dto.response.AssignmentDTO;
import id.ac.ui.cs.apap.sceleNG.model.CourseAssignment;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AssignmentMapper {
    CourseAssignment createAssignmentRequestDTOToAssignment(CreateAssignmentDTO createAssignmentDTO);
    CourseAssignment updateAssignmentRequestDTOToAssignment(UpdateAssignmentDTO updateAssignmentDTO);
    UpdateAssignmentDTO assignmentToUpdateAssignmentRequestDTO(CourseAssignment courseAssignment);
    AssignmentDTO assignmentToReadAssignmentResponseDTO(CourseAssignment courseAssignment);
    void updateAssignmentFromDTO(UpdateAssignmentDTO dto, @MappingTarget CourseAssignment courseAssignment);
}