package com.ai.springai;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

    private final ChatModel chatmodel;

    public ChatService(ChatModel chatmodel) {
        this.chatmodel = chatmodel;
    }

    public String getResponse(String prompt) {
        return chatmodel.call(prompt);
    }

    public String getResponseOptions(String prompt) {
        ChatResponse response = chatmodel.call(
            new Prompt(
                prompt,
                OpenAiChatOptions.builder()
                    .withModel("gpt4-o") // Ensure this is the right model name for your use case
                    .withTemperature(0.4f) // Use float directly, no need to cast
                    .build()
            ));
        return response.getResult().getOutput().getContent();
    }
}
