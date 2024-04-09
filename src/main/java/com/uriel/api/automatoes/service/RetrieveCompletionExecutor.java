package com.uriel.api.automatoes.service;

import com.uriel.api.automatoes.data.entity.Message;

import java.util.List;

public interface RetrieveCompletionExecutor {

    Message execute(List<Message> messages);

}
