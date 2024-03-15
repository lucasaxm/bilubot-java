package com.boatarde.bilubot;

import com.boatarde.bilubot.bots.HelloBot;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@SpringBootApplication
public class BilubotApplication {

    private final HelloBot helloBot;

    public BilubotApplication(HelloBot helloBot) {
        this.helloBot = helloBot;
    }

    public static void main(String[] args) {
        SpringApplication.run(BilubotApplication.class, args);
    }

    @PostConstruct
    public void registerHelloBotAbilities() {
        try {
            // Create the TelegramBotsApi object to register your bots
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);

            // Register your newly created AbilityBot
            botsApi.registerBot(helloBot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
