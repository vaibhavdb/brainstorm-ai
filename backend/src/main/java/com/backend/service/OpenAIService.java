package com.backend.service;

import com.backend.config.GeminiConfig;
import org.springframework.stereotype.Service;

@Service
public class OpenAIService {

    private final String apiKey;

    public OpenAIService(GeminiConfig geminiConfig) {
        this.apiKey = geminiConfig.getGeminiApiKey();
    }

    public String summarizeCode(String code) {
        // Here you’d call the OpenAI API with the apiKey
        // (pseudo-code for now)
        if (apiKey == null || apiKey.isEmpty()) {
            throw new RuntimeException("OpenAI API key is not set!");
        }

        // TODO: Replace with actual HTTP call to OpenAI
        return "Summary of: " + code;
    }
}
