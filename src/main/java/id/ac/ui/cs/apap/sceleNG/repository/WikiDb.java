package id.ac.ui.cs.apap.sceleNG.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import id.ac.ui.cs.apap.sceleNG.model.Wiki;

@Repository
@Transactional
public interface WikiDb extends JpaRepository<Wiki, UUID> {
    @Query("SELECT w FROM Wiki w WHERE w.isDeleted = false")
    List<Wiki> getAllWikis();

    @Query("SELECT w FROM Wiki w WHERE w.id = :id AND w.isDeleted = false")
    Optional<Wiki> findByIdAndNotDeleted(@Param("id") UUID id);
}