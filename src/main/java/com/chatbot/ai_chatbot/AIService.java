package com.chatbot.ai_chatbot;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import java.util.*;

import org.json.JSONObject;
import org.json.JSONArray;

@Service
public class AIService {

    private final String API_URL = "https://openrouter.ai/api/v1/chat/completions";
    private final String API_KEY = "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"; // 🔴 paste your key here

    public String getResponse(String message) {

        RestTemplate restTemplate = new RestTemplate();

        // Headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + API_KEY);
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Body
        Map<String, Object> body = new HashMap<>();
        body.put("model", "openai/gpt-3.5-turbo");

        List<Map<String, String>> messages = new ArrayList<>();

        Map<String, String> msg = new HashMap<>();
        msg.put("role", "user");
        msg.put("content", message);

        messages.add(msg);

        body.put("messages", messages);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<String> response =
                    restTemplate.postForEntity(API_URL, request, String.class);

            // 🔥 Extract clean response
            String json = response.getBody();

            JSONObject obj = new JSONObject(json);
            JSONArray choices = obj.getJSONArray("choices");
            JSONObject messageObj = choices.getJSONObject(0).getJSONObject("message");

            return messageObj.getString("content");

        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}