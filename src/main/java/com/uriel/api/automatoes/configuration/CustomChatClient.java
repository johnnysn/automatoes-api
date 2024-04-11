package com.uriel.api.automatoes.configuration;

import org.springframework.ai.model.function.FunctionCallbackContext;
import org.springframework.ai.openai.OpenAiChatClient;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.support.RetryTemplate;

public class CustomChatClient extends OpenAiChatClient {

    public CustomChatClient(OpenAiApi openAiApi, OpenAiChatOptions options, FunctionCallbackContext functionCallbackContext, RetryTemplate retryTemplate) {
        super(openAiApi, options, functionCallbackContext, retryTemplate);
    }

    @Override
    protected ResponseEntity<OpenAiApi.ChatCompletion> doChatCompletion(OpenAiApi.ChatCompletionRequest request) {
        for (int i = 0; i < request.messages().size(); i++) {
            var message = request.messages().get(i);
            request.messages().set(i, new OpenAiApi.ChatCompletionMessage(
                    message.content() == null ? "" : message.content(), message.role(), null, message.toolCallId(), message.toolCalls()
            ));
        }

        return super.doChatCompletion(request);
    }
}
