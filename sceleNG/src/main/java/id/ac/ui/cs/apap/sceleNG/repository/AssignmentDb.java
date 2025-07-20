package id.ac.ui.cs.apap.sceleNG.repository;

import id.ac.ui.cs.apap.sceleNG.model.CourseAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Transactional
public interface AssignmentDb extends JpaRepository<CourseAssignment, UUID> {

    @Query("SELECT a FROM CourseAssignment a WHERE a.isDeleted = false")
    List<CourseAssignment> getAllAssignments();

    @Query("SELECT a FROM CourseAssignment a WHERE a.course = :course AND a.isDeleted = false")
    List<CourseAssignment> findByCourse(@Param("course") String course);

    @Query("SELECT a FROM CourseAssignment a WHERE a.owner = :owner AND a.isDeleted = false")
    List<CourseAssignment> findByOwner(@Param("owner") String owner);

    @Query("SELECT a FROM CourseAssignment a WHERE a.dueDate < :now AND a.deletedAt IS NULL")
    List<CourseAssignment> findDueAssignments(@Param("now") Instant now);

    @Query("SELECT a FROM CourseAssignment a WHERE a.dueDate > :now AND a.deletedAt IS NULL")
    List<CourseAssignment> findOngoingAssignments(@Param("now") Instant now);

    @Query("SELECT a FROM CourseAssignment a WHERE LOWER(a.title) LIKE LOWER(CONCAT('%', :query, '%')) AND a.deletedAt IS NULL")
    List<CourseAssignment> findAssignmentsContaining(@Param("query") String query);

}

