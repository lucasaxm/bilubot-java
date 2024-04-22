package com.boatarde.bilubot;

import com.boatarde.bilubot.bots.BiluBot;
import com.boatarde.bilubot.models.gallerydl.GalleryDlProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.nio.file.Files;
import java.nio.file.Paths;

@SpringBootApplication
@Slf4j
public class BilubotApplication {

    private final BiluBot biluBot;
    private final ObjectMapper objectMapper;
    private final GalleryDlProperties galleryDlProperties;

    public BilubotApplication(BiluBot biluBot, ObjectMapper objectMapper, GalleryDlProperties galleryDlProperties) {
        this.biluBot = biluBot;
        this.objectMapper = objectMapper;
        this.galleryDlProperties = galleryDlProperties;
    }

    public static void main(String[] args) {
        SpringApplication.run(BilubotApplication.class, args);
    }

    @PostConstruct
    public void onStartUpInit() {
        registerHelloBotAbilities();
        saveGalleryDlConfigAsJson();
    }

    private void registerHelloBotAbilities() {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(biluBot);
        } catch (TelegramApiException e) {
            log.error(String.format("Error registering bots: %s", e.getMessage()), e);
        }
    }

    private void saveGalleryDlConfigAsJson() {
        try {
            String json = objectMapper.writeValueAsString(galleryDlProperties.getConfig());
            Files.write(Paths.get(galleryDlProperties.getConfigPath()), json.getBytes());
        } catch (Exception e) {
            log.error(String.format("Error writing the configuration to file: %s", e.getMessage()), e);
        }
    }
}
