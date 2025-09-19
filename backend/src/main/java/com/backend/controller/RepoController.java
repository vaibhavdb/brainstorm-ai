package com.backend.controller;

import com.backend.service.RepoService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/projects")
public class RepoController {

    private final RepoService repoService;

    public RepoController(RepoService repoService) {
        this.repoService = repoService;
    }

    @PostMapping("/summarize")
    public Map<String, Object> summarizeRepo(@RequestBody Map<String, String> payload) {
        String repoUrl = payload.get("repoUrl");
        try {
            return repoService.summarizeRepository(repoUrl);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}