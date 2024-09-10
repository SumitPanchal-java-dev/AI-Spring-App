package com.ai.springai;

import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.ai.openai.OpenAiImageModel;
import org.springframework.ai.openai.OpenAiImageOptions;
import org.springframework.stereotype.Service;

@Service
public class ImageService {

    private final OpenAiImageModel imagemodel;

    public ImageService(OpenAiImageModel imagemodel) {
        this.imagemodel = imagemodel;
    }

    public ImageResponse generateImage(String prompt, String quality, int n, int width, int height) {
        return imagemodel.call(new ImagePrompt(prompt,
            OpenAiImageOptions.builder()
                .withModel("dall-e-2")
                .withQuality(quality)
                .withN(n)
                .withHeight(height)
                .withWidth(width)
                .build()
        ));
    }
}

