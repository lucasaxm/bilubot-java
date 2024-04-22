package com.boatarde.bilubot.flows.common;

import com.boatarde.bilubot.bots.BiluBot;
import com.boatarde.bilubot.flows.WorkflowAction;
import com.boatarde.bilubot.flows.WorkflowDataBag;
import com.boatarde.bilubot.flows.WorkflowDataKey;
import com.boatarde.bilubot.flows.WorkflowStep;
import com.boatarde.bilubot.flows.WorkflowStepRegistration;
import com.boatarde.bilubot.util.TelegramUtils;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@WorkflowStepRegistration(WorkflowAction.SEND_MESSAGE)
public class SendMessageStep implements WorkflowStep {

    @Override
    public WorkflowAction run(WorkflowDataBag bag) {
        BiluBot biluBot = bag.get(WorkflowDataKey.BILUBOT, BiluBot.class);
        SendMessage message = bag.get(WorkflowDataKey.SEND_MESSAGE, SendMessage.class);
        try {
            BotApiObject response = biluBot.execute(message);
            log.info("Response: {}", TelegramUtils.toJson(response, true));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        return WorkflowAction.NONE;
    }
}
