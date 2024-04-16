package com.boatarde.bilubot.flows.ping;

import com.boatarde.bilubot.flows.WorkflowAction;
import com.boatarde.bilubot.flows.WorkflowDataBag;
import com.boatarde.bilubot.flows.WorkflowDataKey;
import com.boatarde.bilubot.flows.WorkflowStep;
import com.boatarde.bilubot.flows.WorkflowStepRegistration;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.time.Instant;

@Slf4j
@WorkflowStepRegistration(WorkflowAction.BUILD_PONG_MESSAGE)
public class BuildPongMessageStep implements WorkflowStep {

    @Override
    public WorkflowAction run(WorkflowDataBag bag) {
        Update update = bag.get(WorkflowDataKey.TELEGRAM_UPDATE, Update.class);

        bag.put(WorkflowDataKey.SEND_MESSAGE, SendMessage.builder()
            .chatId(update.getMessage().getChatId())
            .replyToMessageId(update.getMessage().getMessageId())
            .allowSendingWithoutReply(true)
            .text(String.format("pong! (%ds)", Instant.now().getEpochSecond() - update.getMessage().getDate()))
            .build());

        return WorkflowAction.SEND_MESSAGE;
    }
}
