package id.ac.ui.cs.apap.sceleNG.repository;

import id.ac.ui.cs.apap.sceleNG.model.Authentication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AuthenticationDb extends JpaRepository<Authentication, UUID> {
    Optional<Authentication> findByUsername(String username);
}
