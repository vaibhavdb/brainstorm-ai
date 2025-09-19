package com.backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
@Configuration
public class GeminiConfig {

    @Value("${gemini.api.key}")
    private String geminiApiKey;

    @Value("${gemini.api.endpoint}")
    private String geminiEndpoint;

    public String getGeminiApiKey() {
        return geminiApiKey;
    }

    public String getGeminiEndpoint() {
        return geminiEndpoint;
    }
}
