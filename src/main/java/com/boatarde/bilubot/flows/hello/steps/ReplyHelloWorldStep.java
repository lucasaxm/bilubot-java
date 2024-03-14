package com.boatarde.bilubot.flows.hello.steps;

import com.boatarde.bilubot.bots.HelloBot;
import com.boatarde.bilubot.flows.FlowContext;
import com.boatarde.bilubot.flows.Step;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Optional;

@Slf4j
@Component
public class ReplyHelloWorldStep implements Step {

    @Override
    public Optional<Step> run(FlowContext context) {
        log.info("Running ReplyHelloWorldStep");
        Update update = context.getUpdate();
        HelloBot hellobot = (HelloBot) context.getBot();

        SendMessage message = SendMessage.builder()
            .chatId(update.getMessage().getChatId())
            .replyToMessageId(update.getMessage().getMessageId())
            .allowSendingWithoutReply(true)
            .text("world!")
            .build();
        try {
            Message response = hellobot.execute(message);
            log.info("ReplyHelloWorldStep finished. Response: {}", response);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
