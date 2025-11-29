package id.ac.ui.cs.apap.sceleNG.dto;

import id.ac.ui.cs.apap.sceleNG.dto.request.UpdateSubmissionDTO;
import id.ac.ui.cs.apap.sceleNG.model.CourseAssignment;
import id.ac.ui.cs.apap.sceleNG.model.Submission;
import id.ac.ui.cs.apap.sceleNG.repository.AssignmentDb;
import org.mapstruct.*;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface SubmissionMapper {

    @Mapping(target = "assignment", source = "assignment", qualifiedByName = "mapAssignmentToUUID")
    UpdateSubmissionDTO SubmissionToUpdateSubmissionRequestDTO(Submission submission);

    @Mapping(target = "assignment", source = "assignment", qualifiedByName = "mapUUIDToAssignment")
    void updateSubmissionFromDTO(UpdateSubmissionDTO dto, @MappingTarget Submission submission, @Context AssignmentDb assignmentDb);

    @Named("mapAssignmentToUUID")
    static UUID mapAssignmentToUUID(CourseAssignment assignment) {
        return assignment != null ? assignment.getId() : null;
    }

    @Named("mapUUIDToAssignment")
    static CourseAssignment mapUUIDToAssignment(UUID id, @Context AssignmentDb assignmentDb) {
        return id != null ? assignmentDb.findById(id).orElse(null) : null;
    }
}
