package id.ac.ui.cs.apap.sceleNG.repository;
import id.ac.ui.cs.apap.sceleNG.model.Submission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface SubmissionDb extends JpaRepository<Submission, UUID> {
    @Query("SELECT s FROM Submission s WHERE s.isDeleted = false")
    List<Submission> getAllSubmissions();

    @Query("SELECT s FROM Submission s WHERE s.id = :id AND s.isDeleted = false")
    Optional<Submission> findByIdAndNotDeleted(@Param("id") UUID id);
}
