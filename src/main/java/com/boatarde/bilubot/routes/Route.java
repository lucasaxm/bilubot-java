package com.boatarde.bilubot.routes;

import com.boatarde.bilubot.flows.WorkflowAction;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.TelegramBot;

import java.util.Optional;

public interface Route {
    Optional<WorkflowAction> test(Update update, TelegramBot bot);
}
