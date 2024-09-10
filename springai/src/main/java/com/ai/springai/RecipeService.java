package com.ai.springai;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.stereotype.Service;

@Service
public class RecipeService {

    private final ChatModel chatmodel;
    private static final Logger logger = LoggerFactory.getLogger(RecipeService.class);

    public RecipeService(ChatModel chatmodel) {
        this.chatmodel = chatmodel;
    }

    public String createRecipe(String ingredients, String cuisine, String dietaryRestrictions) {

        // Improved prompt template
        var template = """
                I want to create a recipe using the following ingredients: {ingredients}.
                The cuisine type I prefer is {cuisine}.
                Please consider the following dietary restrictions: {dietaryRestrictions}.
                Provide a detailed recipe, including a title, list of ingredients, and cooking instructions.
                """;

        // If dietary restrictions or cuisine type are not provided, handle defaults.
        if (cuisine == null || cuisine.isEmpty()) {
            cuisine = "any";
        }
        if (dietaryRestrictions == null || dietaryRestrictions.isEmpty()) {
            dietaryRestrictions = "none";
        }

        PromptTemplate promptTemplate = new PromptTemplate(template);
        Map<String, Object> params = Map.of(
                "ingredients", ingredients,
                "cuisine", cuisine,
                "dietaryRestrictions", dietaryRestrictions
        );

        Prompt prompt = promptTemplate.create(params);
        
        try {
            var response = chatmodel.call(prompt);
            String content = response.getResult().getOutput().getContent();
            logger.info("Generated recipe successfully");
            return content;
        } catch (Exception e) {
            logger.error("Error generating recipe", e);
            return "Error generating the recipe, please try again.";
        }
    }
}
