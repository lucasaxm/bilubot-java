package com.boatarde.bilubot.routes;

import com.boatarde.bilubot.bots.BiluBot;
import com.boatarde.bilubot.flows.WorkflowAction;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.EntityType;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.TelegramBot;

import java.util.Optional;

@Component
public class GalleryDlRoute implements Route {
    @Override
    public Optional<WorkflowAction> test(Update update, TelegramBot bot) {
        if (bot instanceof BiluBot
            && update.hasMessage()
            && update.getMessage().hasEntities()
            && update.getMessage().getEntities().stream()
            .anyMatch(entity -> EntityType.URL.equalsIgnoreCase(entity.getType()))) {
            return Optional.of(WorkflowAction.DOWNLOAD);
        }
        return Optional.empty();
    }
}
