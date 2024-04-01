package com.boatarde.bilubot.routes;

import com.boatarde.bilubot.bots.HelloBot;
import com.boatarde.bilubot.flows.FlowAction;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.TelegramBot;

import java.util.Optional;

@Component
public class GalleryDlRoute implements Route {
    @Override
    public Optional<FlowAction> test(Update update, TelegramBot bot) {
        if (bot instanceof HelloBot
            && update.hasMessage()
            && update.getMessage().hasEntities()
            && update.getMessage().getEntities().stream().anyMatch(entity -> "url".equalsIgnoreCase(entity.getType()))) {
            return Optional.of(FlowAction.DOWNLOAD);
        }
        return Optional.empty();
    }
}
