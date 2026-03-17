package com.chatbot.ai_chatbot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private AIService aiService;

    @PostMapping
    public String chat(@RequestBody String message) {
        return aiService.getResponse(message);
    }
}
