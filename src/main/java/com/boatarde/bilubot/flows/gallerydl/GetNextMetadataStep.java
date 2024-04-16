package com.boatarde.bilubot.flows.gallerydl;

import com.boatarde.bilubot.flows.WorkflowAction;
import com.boatarde.bilubot.flows.WorkflowDataBag;
import com.boatarde.bilubot.flows.WorkflowDataKey;
import com.boatarde.bilubot.flows.WorkflowStep;
import com.boatarde.bilubot.flows.WorkflowStepRegistration;
import com.github.lucasaxm.gallerydl.metadata.Metadata;
import com.github.lucasaxm.gallerydl.options.GalleryDlResult;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.send.SendMediaGroup;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.ListIterator;

@Slf4j
@WorkflowStepRegistration(WorkflowAction.GET_NEXT_GALLERY_DL_METADATA)
public class GetNextMetadataStep implements WorkflowStep {

    @Override
    public WorkflowAction run(WorkflowDataBag bag) {
        GalleryDlResult currentResult = bag.get(WorkflowDataKey.GALLERY_DL_CURRENT_RESULT, GalleryDlResult.class);
        Update update = bag.get(WorkflowDataKey.TELEGRAM_UPDATE, Update.class);

        bag.putIfAbsent(WorkflowDataKey.GALLERY_DL_CURRENT_RESULT_METADATA_ITERATOR,
            currentResult.getMetadata().listIterator());
        ListIterator<Metadata> metadataIterator =
            bag.getGeneric(WorkflowDataKey.GALLERY_DL_CURRENT_RESULT_METADATA_ITERATOR, ListIterator.class,
                Metadata.class);

        if (!metadataIterator.hasNext()) {
            log.info("No more metadata to send for the current result. Total sent: {}", metadataIterator.nextIndex());
            bag.remove(WorkflowDataKey.GALLERY_DL_CURRENT_RESULT_METADATA_ITERATOR);
            return WorkflowAction.GET_NEXT_GALLERY_DL_RESULT;
        }
        bag.put(WorkflowDataKey.GALLERY_DL_CURRENT_METADATA, metadataIterator.next());
        bag.putIfAbsent(WorkflowDataKey.SEND_MEDIA_GROUP, SendMediaGroup.builder()
            .allowSendingWithoutReply(true)
            .chatId(update.getMessage().getChatId())
            .replyToMessageId(update.getMessage().getMessageId())
            .medias(new ArrayList<>())
            .build());
        return WorkflowAction.UPLOAD_AND_ADD_INPUT_MEDIA;
    }
}