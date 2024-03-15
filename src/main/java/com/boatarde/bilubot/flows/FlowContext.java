package com.boatarde.bilubot.flows;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.TelegramBot;

@AllArgsConstructor
@Getter
public class FlowContext {
    private TelegramBot bot;
    private Update update;
}
