package com.backend.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

@Service
public class RepoService {

    public void summarizeRepositoryWithProgress(
            String repoUrl,
            BiConsumer<Integer, String> progressCallback
    ) {
        // Dummy repo files (replace with GitHub API fetching later)
        String[] files = {
                "src/main/java/App.java",
                "src/main/resources/application.properties",
                "README.md"
        };

        int total = files.length;

        for (int i = 0; i < total; i++) {
            String file = files[i];
            String summary = summarizeFile(file, "Dummy content");

            // Report progress
            int percent = (int) (((i + 1) / (double) total) * 100);
            progressCallback.accept(percent, "Summarized " + file);
        }
    }

    public Map<String, Object> summarizeRepository(String repoUrl) {
        Map<String, Object> result = new HashMap<>();

        summarizeRepositoryWithProgress(repoUrl, (progress, note) -> {
            result.put("progress", progress);
            result.put("note", note);
        });

        result.put("summary", "Final repo summary placeholder");
        return result;
    }

    private String summarizeFile(String fileName, String content) {
        // TODO: integrate OpenAI later
        return "Summary for " + fileName;
    }
}
