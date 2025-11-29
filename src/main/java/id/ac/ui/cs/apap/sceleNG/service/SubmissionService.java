package id.ac.ui.cs.apap.sceleNG.service;
import java.util.List;
import java.util.UUID;
import id.ac.ui.cs.apap.sceleNG.model.Submission;

public interface SubmissionService {

    List<Submission> getAllSubmissions();

    void postSubmission(Submission submission);

    Submission getSubmissionById(UUID id);

    Submission putModifySubmission(Submission submission);

    void deleteSubmission(UUID id);
}
