package id.ac.ui.cs.apap.sceleNG.service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import id.ac.ui.cs.apap.sceleNG.model.CourseAssignment;
import id.ac.ui.cs.apap.sceleNG.repository.AssignmentDb;

@Service
public class CourseAssignmentServiceImpl implements CourseAssignmentService {

    @Autowired
    private AssignmentDb assignmentDb;

    @Override
    public List<CourseAssignment> getAllAssignments() {
        return assignmentDb.getAllAssignments();
    }

    @Override
    public void postAssignment(CourseAssignment courseAssignment) {
        assignmentDb.save(courseAssignment);
    }

    @Override
    public CourseAssignment putModifyAssignment(CourseAssignment courseAssignment) {
        assignmentDb.save(courseAssignment);
        return courseAssignment;
    }

    @Override
    public void deleteAssignment(UUID id) {
        CourseAssignment assignment = assignmentDb.findById(id)
                .orElseThrow(() -> new RuntimeException("Assignment not found"));

        assignment.setDeleted(true);
        assignmentDb.save(assignment);
    }

    @Override
    public CourseAssignment getAssignmentById(UUID id) {
        return assignmentDb.findById(id).orElse(null);
    }

    @Override
    public List<CourseAssignment> getFilteredAssignments(UUID course, String title, String owner, String dueStatus) {
        List<CourseAssignment> allAssignments = assignmentDb.getAllAssignments();
        Instant now = Instant.now();

        return allAssignments.stream()
            .filter(a -> course == null || a.getCourse().equals(course))
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
            .toList();
    }

}

