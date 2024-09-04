package com.stellantis.chatbot_kpi.web;


import com.stellantis.chatbot_kpi.services.ChatAIService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chat")
public class ChatRestController {

    private ChatAIService chatAIService;

    public ChatRestController(ChatAIService chatAIService) {
        this.chatAIService = chatAIService;
    }
    @GetMapping("/ask")
    public String ask(String question) {
        return chatAIService.ragChat(question);
    }

}
