package com.ai.springai;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GenAIController {

    private final ChatService chatservice;
    private final ImageService imageservice;
    private final RecipeService recipeservice;

    public GenAIController(ChatService chatservice, ImageService imageservice, RecipeService recipeservice) {
        this.chatservice = chatservice;
        this.imageservice = imageservice;
        this.recipeservice = recipeservice;
    }

    // Endpoint to get a basic response from AI
    @GetMapping("/ask-ai")
    public String getResponse(@RequestParam String prompt) {
        // Validate that the prompt is not empty or null
        if (prompt == null || prompt.trim().isEmpty()) {
            return "Prompt cannot be empty!";
        }
        return chatservice.getResponse(prompt);
    }

    // Endpoint to get response options from AI with configurations
    @GetMapping("/ask-ai-options")
    public String getResponseOptions(@RequestParam String prompt) {
        if (prompt == null || prompt.trim().isEmpty()) {
            return "Prompt cannot be empty!";
        }
        return chatservice.getResponseOptions(prompt);
    }

    // Endpoint to generate images using a prompt
    @GetMapping("/generate-image")
    public List<String> generateImages(@RequestParam String prompt,
                                       @RequestParam(defaultValue = "hd") String quality,
                                       @RequestParam(defaultValue = "1") int n,
                                       @RequestParam(defaultValue = "1024") int width,
                                       @RequestParam(defaultValue = "1024") int height) {
        // Ensure that the prompt is provided
        if (prompt == null || prompt.trim().isEmpty()) {
            throw new IllegalArgumentException("Prompt cannot be empty!");
        }
        // Generate images based on the provided parameters
        return imageservice.generateImage(prompt, quality, n, width, height)
                .getResults().stream()
                .map(result -> result.getOutput().getUrl())
                .toList();
    }

    // Endpoint to create a recipe based on ingredients and preferences
    @GetMapping("/recipe-creator")
    public String recipeCreator(@RequestParam String ingredients,
                                @RequestParam(defaultValue = "any") String cuisine,
                                @RequestParam(defaultValue = "") String dietaryRestrictions) {
        // Ensure ingredients are provided
        if (ingredients == null || ingredients.trim().isEmpty()) {
            return "Ingredients cannot be empty!";
        }
        return recipeservice.createRecipe(ingredients, cuisine, dietaryRestrictions);
    }
}
