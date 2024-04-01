package com.boatarde.bilubot.flows.gallerydl.steps;

import com.boatarde.bilubot.bots.HelloBot;
import com.boatarde.bilubot.flows.FlowContext;
import com.boatarde.bilubot.flows.Step;
import com.github.lucasaxm.gallerydl.metadata.Metadata;
import com.github.lucasaxm.gallerydl.metadata.reddit.Submission;
import com.github.lucasaxm.gallerydl.metadata.twitter.Tweet;
import com.github.lucasaxm.gallerydl.options.GalleryDlResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Optional;

@Component
@Slf4j
public class SendGalleryDlResultStep implements Step {

    @Override
    public Optional<Step> run(FlowContext context) {
        log.info("Running SendGalleryDlResultStep");
        Update update = context.getUpdate();
        HelloBot hellobot = (HelloBot) context.getBot();

        for (GalleryDlResult result : context.getGalleryDlResults()) {
            for (Metadata metadata : result.getMetadata()) {
                String text;
                if (metadata instanceof Tweet) {
                    text = String.format("%s(%s)\n%s", ((Tweet) metadata).getAuthor().getName(),
                        ((Tweet) metadata).getAuthor().getNick(), ((Tweet) metadata).getDescription());
                } else if (metadata instanceof Submission) {
                    text = String.format("%s(%s)\n%s", ((Submission) metadata).getAuthor(),
                        ((Submission) metadata).getSubreddit(), ((Submission) metadata).getSelftext());
                } else {
                    text = String.format("%s(%s)", metadata.getCategory(), metadata.getSubcategory());
                }
                SendMessage message = SendMessage.builder()
                    .chatId(update.getMessage().getChatId())
                    .replyToMessageId(update.getMessage().getMessageId())
                    .allowSendingWithoutReply(true)
                    .text(text)
                    .build();
                try {
                    Message response = hellobot.execute(message);
                    log.info("SendGalleryDlResultStep finished. Response: {}", response);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                    break;
                }
            }
        }
        return Optional.empty();

    }
}
