package com.uriel.api.automatoes.configuration;

import com.uriel.api.automatoes.automation.functions.WeatherFunction;
import com.uriel.api.automatoes.service.RetrieveCompletionExecutor;
import com.uriel.api.automatoes.service.RetrieveCompletionHLExecutor;
import org.springframework.ai.autoconfigure.openai.OpenAiChatProperties;
import org.springframework.ai.model.function.FunctionCallback;
import org.springframework.ai.model.function.FunctionCallbackContext;
import org.springframework.ai.model.function.FunctionCallbackWrapper;
import org.springframework.ai.openai.OpenAiChatClient;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.util.CollectionUtils;

import java.util.List;

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
    public RetrieveCompletionExecutor retrieveCompletionExecutor(AiConfiguration aiConfiguration, OpenAiChatClient client) {
        return new RetrieveCompletionHLExecutor(aiConfiguration, client);
    }

    @Bean
    public FunctionCallback weatherFunctionWrapper() {

        return FunctionCallbackWrapper.builder(new WeatherFunction())
                .withResponseConverter(response -> response.temperature() + "C")
                .withInputType(WeatherFunction.Request.class)
                .withDescription("Returns the weather forecast in a given city for a number of days in the future")
                .withName("weatherFunction")
                .build();
    }

    @Bean
    @Primary
    public OpenAiChatClient openAiChatClient(OpenAiApi openAiApi,
                                             OpenAiChatProperties chatProperties,
                                             List<FunctionCallback> toolFunctionCallbacks, FunctionCallbackContext functionCallbackContext,
                                             RetryTemplate retryTemplate) {

        if (!CollectionUtils.isEmpty(toolFunctionCallbacks)) {
            chatProperties.getOptions().getFunctionCallbacks().addAll(toolFunctionCallbacks);
        }

        return new CustomChatClient(openAiApi, chatProperties.getOptions(), functionCallbackContext, retryTemplate);
    }
}
