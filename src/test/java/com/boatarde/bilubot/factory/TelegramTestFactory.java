package com.boatarde.bilubot.factory;

import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

public class TelegramTestFactory {

    private static final String PRIVATE = "private";
    private static final String TEST = "test";
    private static final Long CHAT_ID = 1234L;
    private static final Integer MESSAGE_ID = 5678;

    public static Update buildTextMessageUpdate(String text){
        Chat chat = new Chat();
        chat.setFirstName(TEST);
        chat.setType(PRIVATE);
        chat.setId(CHAT_ID);

        Message message = new Message();
        message.setChat(chat);
        message.setText(text);
        message.setMessageId(MESSAGE_ID);

        Update update = new Update();
        update.setMessage(message);

        return update;
    }
}
