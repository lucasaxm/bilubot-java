package com.boatarde.bilubot.routes;

import com.boatarde.bilubot.bots.BiluBot;
import com.boatarde.bilubot.flows.WorkflowAction;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.TelegramBot;

import java.util.Optional;

import static com.boatarde.bilubot.util.TelegramUtils.extractCommandContent;

@Component
public class PingRoute implements Route {

    private static final String PING_COMMAND = "/ping";

    @Override
    public Optional<WorkflowAction> test(Update update, TelegramBot bot) {
        if (bot instanceof BiluBot
            && update.hasMessage()
            && "".equals(extractCommandContent(update.getMessage(), PING_COMMAND, bot.getBotUsername()))) {
            return Optional.of(WorkflowAction.BUILD_PONG_MESSAGE);
        }
        return Optional.empty();
    }
}
