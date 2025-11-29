package id.ac.ui.cs.apap.sceleNG.controller;

import org.springframework.beans.factory.annotation.Autowired;
import id.ac.ui.cs.apap.sceleNG.service.SubmissionService;
import id.ac.ui.cs.apap.sceleNG.service.AuthService;

public class SubmissionController {
    @Autowired
    private AuthService authService;

    @Autowired
    private SubmissionService submissionService;
}
