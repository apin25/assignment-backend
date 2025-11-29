package id.ac.ui.cs.apap.sceleNG.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import id.ac.ui.cs.apap.sceleNG.model.Submission;
import id.ac.ui.cs.apap.sceleNG.repository.SubmissionDb;

@Service
public class SubmissionServiceImpl implements SubmissionService {
    @Autowired
    private SubmissionDb submissionDb;

    @Override
    public List<Submission> getAllSubmissions() {
        return submissionDb.getAllSubmissions();
    }

    @Override
    public void postSubmission(Submission submission) {
        submissionDb.save(submission);
    }

    @Override
    public Submission putModifySubmission(Submission submission) {
        submissionDb.save(submission);
        return submission;
    }

    @Override
    public void deleteSubmission(UUID id) {
        Submission submission = submissionDb.findById(id)
            .orElseThrow(() -> new RuntimeException("Submission not found"));

        submission.setDeleted(true);
        submissionDb.save(submission);
    }

    @Override
    public Submission getSubmissionById(UUID id) {
        return submissionDb.findById(id).orElse(null);
    }
}
