package com.backend.service;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.*;

/**
 * Minimal OpenAI Chat Completions client using WebClient.
 * Uses env var OPENAI_API_KEY.
 */
@Component
public class OpenAIClient {

    private final WebClient wc;
    private final String apiKey;

    public OpenAIClient() {
        this.apiKey = System.getenv("OPENAI_API_KEY");
        if (this.apiKey == null || this.apiKey.isBlank()) {
            System.err.println("Warning: OPENAI_API_KEY not set. OpenAI calls will fail.");
        }
        this.wc = WebClient.builder()
                .baseUrl("https://api.openai.com/v1")
                .defaultHeader("Authorization", "Bearer " + this.apiKey)
                .build();
    }

    /**
     * Calls chat completions and returns the assistant's content string.
     */
    public String chatComplete(String systemPrompt, String userPrompt, int maxTokens) {
        Map<String, Object> body = new HashMap<>();
        body.put("model", "gpt-3.5-turbo");
        List<Map<String, String>> messages = new ArrayList<>();
        if (systemPrompt != null) messages.add(Map.of("role", "system", "content", systemPrompt));
        messages.add(Map.of("role", "user", "content", userPrompt));
        body.put("messages", messages);
        body.put("max_tokens", maxTokens);
        body.put("temperature", 0.2);

        try {
            Mono<Map> mono = wc.post()
                    .uri("/chat/completions")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(body)
                    .retrieve()
                    .bodyToMono(Map.class);

            Map resp = mono.block();
            if (resp == null) return "OpenAI returned null response";
            List choices = (List) resp.get("choices");
            if (choices != null && !choices.isEmpty()) {
                Map first = (Map) choices.get(0);
                Map message = (Map) first.get("message");
                if (message != null) {
                    Object content = message.get("content");
                    return content == null ? "" : content.toString().trim();
                }
            }
            return "No choices in OpenAI response";
        } catch (Exception e) {
            e.printStackTrace();
            return "OpenAI call failed: " + e.getMessage();
        }
    }
}
