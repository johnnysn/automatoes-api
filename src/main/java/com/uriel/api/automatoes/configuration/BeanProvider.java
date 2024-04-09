package com.uriel.api.automatoes.configuration;

import com.uriel.api.automatoes.service.RetrieveCompletionExecutor;
import com.uriel.api.automatoes.service.RetrieveCompletionHLExecutor;
import org.springframework.ai.openai.OpenAiChatClient;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class BeanProvider {

    @Value("${spring.ai.openai.api-key}")
    private String apiKey;

    @Value("${spring.ai.openai.base-url}")
    private String baseUrl;

    @Bean
    public OpenAiApi openAiApi() {
        return new OpenAiApi(baseUrl, apiKey);
    }

    @Bean
    @Primary
    public RetrieveCompletionExecutor retrieveCompletionExecutor(AiConfiguration aiConfiguration, OpenAiChatClient chatClient) {
        return new RetrieveCompletionHLExecutor(aiConfiguration, chatClient);
    }

}
