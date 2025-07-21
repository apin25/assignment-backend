package id.ac.ui.cs.apap.sceleNG.repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import id.ac.ui.cs.apap.sceleNG.model.CourseAssignment;

@Repository
@Transactional
public interface AssignmentDb extends JpaRepository<CourseAssignment, UUID> {

    @Query("SELECT a FROM CourseAssignment a WHERE a.isDeleted = false")
    List<CourseAssignment> getAllAssignments();

    @Query("SELECT a FROM CourseAssignment a WHERE a.id = :id AND a.isDeleted = false")
    Optional<CourseAssignment> findByIdAndNotDeleted(@Param("id") UUID id);

    @Query("SELECT a FROM CourseAssignment a WHERE a.course = :course AND a.isDeleted = false")
    List<CourseAssignment> findByCourse(@Param("course") String course);

    @Query("SELECT a FROM CourseAssignment a WHERE a.owner = :owner AND a.isDeleted = false")
    List<CourseAssignment> findByOwner(@Param("owner") String owner);

    @Query("SELECT a FROM CourseAssignment a WHERE a.dueDate < :now AND a.isDeleted = false")
    List<CourseAssignment> findDueAssignments(@Param("now") Instant now);

    @Query("SELECT a FROM CourseAssignment a WHERE a.dueDate > :now AND a.isDeleted = false")
    List<CourseAssignment> findOngoingAssignments(@Param("now") Instant now);

    @Query("SELECT a FROM CourseAssignment a WHERE LOWER(a.title) LIKE LOWER(CONCAT('%', :query, '%')) AND a.isDeleted = false")
    List<CourseAssignment> findAssignmentsContaining(@Param("query") String query);

}

