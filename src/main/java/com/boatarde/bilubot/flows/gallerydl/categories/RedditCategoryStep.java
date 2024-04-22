package com.boatarde.bilubot.flows.gallerydl.categories;

import com.boatarde.bilubot.flows.WorkflowAction;
import com.boatarde.bilubot.flows.WorkflowDataBag;
import com.boatarde.bilubot.flows.WorkflowDataKey;
import com.boatarde.bilubot.flows.WorkflowStep;
import com.boatarde.bilubot.flows.WorkflowStepRegistration;
import com.github.lucasaxm.gallerydl.metadata.Metadata;
import com.github.lucasaxm.gallerydl.metadata.reddit.Submission;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.send.SendMediaGroup;
import org.telegram.telegrambots.meta.api.objects.media.InputMedia;

import java.util.Optional;

@Slf4j
@WorkflowStepRegistration(WorkflowAction.REDDIT_CATEGORY)
public class RedditCategoryStep implements WorkflowStep {

    @Override
    public WorkflowAction run(WorkflowDataBag bag) {
        Metadata metadata = bag.get(WorkflowDataKey.GALLERY_DL_CURRENT_METADATA, Metadata.class);
        SendMediaGroup sendMediaGroup = bag.get(WorkflowDataKey.SEND_MEDIA_GROUP, SendMediaGroup.class);
        int lastMediaIndex = sendMediaGroup.getMedias().size() - 1;
        InputMedia lastMedia = sendMediaGroup.getMedias().get(lastMediaIndex);

        if (metadata instanceof Submission submission) {
            lastMedia.setCaption(String.format("%s(%s)\n%s",
                submission.getAuthor(),
                submission.getSubreddit(),
                Optional.ofNullable(submission.getSelftext()).orElse("")));
        }

        log.info("Updated media index {} with caption: {}", lastMediaIndex, lastMedia.getCaption());
        return WorkflowAction.SEND_MEDIA_GROUP;
    }
}
