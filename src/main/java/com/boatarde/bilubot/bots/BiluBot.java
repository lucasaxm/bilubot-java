package com.boatarde.bilubot.bots;

import com.boatarde.bilubot.service.RouterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@Slf4j
public class BiluBot extends TelegramLongPollingBot {

    private final String username;
    private final RouterService routerService;

    public BiluBot(@Value("${telegram.bots.bilubot.token}") String token,
                   @Value("${telegram.bots.bilubot.username}") String username,
                   RouterService routerService) {
        super(token);
        this.username = username;
        this.routerService = routerService;
    }

    @Override
    public String getBotUsername() {
        return username;
    }

    @Override
    public void onUpdateReceived(Update update) {
        routerService.route(update, this);
    }
}
