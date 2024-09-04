package com.stellantis.chatbot_kpi.services;

import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@BrowserCallable //on peut appeler les methodes de cette classe directement via le frontend(vaadin)
@AnonymousAllowed
public class ChatAIService {

    private ChatClient chatClient; //c'est une interface independante du LLM utilisé

    public ChatAIService(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }
    public String ragChat(String question) {
        return chatClient.prompt()
                .user(question)
                .call()//envoyer la requête (question) au modèle de langage et attendre une réponse.
                .content(); //obtenir le contenu de la réponse du modèle de langage.
    }
}
