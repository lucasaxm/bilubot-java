package com.boatarde.bilubot.flows.gallerydl;

import com.boatarde.bilubot.bots.BiluBot;
import com.boatarde.bilubot.flows.WorkflowAction;
import com.boatarde.bilubot.flows.WorkflowDataBag;
import com.boatarde.bilubot.flows.WorkflowDataKey;
import com.boatarde.bilubot.flows.WorkflowStep;
import com.boatarde.bilubot.flows.WorkflowStepRegistration;
import com.boatarde.bilubot.util.TelegramUtils;
import com.github.lucasaxm.gallerydl.metadata.Metadata;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.send.SendMediaBotMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMediaGroup;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;
import java.util.ListIterator;

@Slf4j
@WorkflowStepRegistration(WorkflowAction.SEND_MEDIA_GROUP)
public class SendMediaGroupStep implements WorkflowStep {

    @Override
    public WorkflowAction run(WorkflowDataBag bag) {
        BiluBot biluBot = bag.get(WorkflowDataKey.BILUBOT, BiluBot.class);
        SendMediaGroup sendMediaGroup = bag.get(WorkflowDataKey.SEND_MEDIA_GROUP, SendMediaGroup.class);
        ListIterator<Metadata> metadataIterator =
            bag.getGeneric(WorkflowDataKey.GALLERY_DL_CURRENT_RESULT_METADATA_ITERATOR, ListIterator.class,
                Metadata.class);

        if (metadataIterator.hasNext() && sendMediaGroup.getMedias().size() < 10) {
            return WorkflowAction.GET_NEXT_GALLERY_DL_METADATA;
        }

        try {
            List<Message> response;
            if (sendMediaGroup.getMedias().size() == 1) {
                SendMediaBotMethod<Message> media = TelegramUtils.inputMediaToSendMedia(sendMediaGroup, 0);
                response = List.of(TelegramUtils.executeSendMediaBotMethod(biluBot, media));
            } else {
                response = biluBot.execute(sendMediaGroup);
            }
            log.info("Response: {}", TelegramUtils.toJson(response));
        } catch (TelegramApiException e) {
            e.printStackTrace();
            return WorkflowAction.NONE;
        }

        bag.remove(WorkflowDataKey.SEND_MEDIA_GROUP);

        return WorkflowAction.GET_NEXT_GALLERY_DL_METADATA;
    }
}
