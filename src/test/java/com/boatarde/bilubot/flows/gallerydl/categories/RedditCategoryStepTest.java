package com.boatarde.bilubot.flows.gallerydl.categories;

import com.boatarde.bilubot.flows.WorkflowAction;
import com.boatarde.bilubot.flows.WorkflowDataBag;
import com.boatarde.bilubot.flows.WorkflowDataKey;
import com.github.lucasaxm.gallerydl.metadata.reddit.Submission;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.methods.send.SendMediaGroup;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class RedditCategoryStepTest {

    private RedditCategoryStep step;

    @BeforeEach
    void setUp() {
        step = new RedditCategoryStep();
    }

    @Test
    void testRunHasSelfText() {
        WorkflowDataBag bag = new WorkflowDataBag();
        Submission metadata = new Submission();
        metadata.setAuthor("author");
        metadata.setSubreddit("subreddit");
        metadata.setSelftext("selftext");
        bag.put(WorkflowDataKey.GALLERY_DL_CURRENT_METADATA, metadata);
        SendMediaGroup sendMediaGroup = SendMediaGroup.builder()
            .chatId("1234")
            .medias(List.of(InputMediaPhoto.builder()
                .media("fileId1")
                .build()))
            .build();
        bag.put(WorkflowDataKey.SEND_MEDIA_GROUP, sendMediaGroup);

        WorkflowAction nextStep = step.run(bag);

        assertEquals(WorkflowAction.SEND_MEDIA_GROUP, nextStep);
        assertEquals("author(subreddit)\nselftext", sendMediaGroup.getMedias().getLast().getCaption());
    }

    @Test
    void testRunSelfTextIsNull() {
        WorkflowDataBag bag = new WorkflowDataBag();
        Submission metadata = new Submission();
        metadata.setAuthor("author");
        metadata.setSubreddit("subreddit");
        bag.put(WorkflowDataKey.GALLERY_DL_CURRENT_METADATA, metadata);
        SendMediaGroup sendMediaGroup = SendMediaGroup.builder()
            .chatId("1234")
            .medias(List.of(InputMediaPhoto.builder()
                .media("fileId1")
                .build()))
            .build();
        bag.put(WorkflowDataKey.SEND_MEDIA_GROUP, sendMediaGroup);

        WorkflowAction nextStep = step.run(bag);

        assertEquals(WorkflowAction.SEND_MEDIA_GROUP, nextStep);
        assertEquals("author(subreddit)\n", sendMediaGroup.getMedias().getLast().getCaption());
    }
}
