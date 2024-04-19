package com.boatarde.bilubot.flows.gallerydl;

import com.boatarde.bilubot.flows.WorkflowAction;
import com.boatarde.bilubot.flows.WorkflowDataBag;
import com.boatarde.bilubot.flows.WorkflowDataKey;
import com.github.lucasaxm.gallerydl.metadata.Unknown;
import com.github.lucasaxm.gallerydl.metadata.reddit.Submission;
import com.github.lucasaxm.gallerydl.metadata.twitter.Tweet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class IdentifyCategoryStepTest {

    private IdentifyCategoryStep step;

    @BeforeEach
    void setUp() {
        step = new IdentifyCategoryStep();
    }

    @Test
    void testTwitter() {
        WorkflowDataBag bag = new WorkflowDataBag();
        Tweet metadata = new Tweet();
        metadata.setCategory("twitter");
        bag.put(WorkflowDataKey.GALLERY_DL_CURRENT_METADATA, metadata);

        WorkflowAction nextStep = step.run(bag);

        assertEquals(WorkflowAction.TWITTER_CATEGORY, nextStep);
    }

    @Test
    void testReddit() {
        WorkflowDataBag bag = new WorkflowDataBag();
        Submission metadata = new Submission();
        metadata.setCategory("reddit");
        bag.put(WorkflowDataKey.GALLERY_DL_CURRENT_METADATA, metadata);

        WorkflowAction nextStep = step.run(bag);

        assertEquals(WorkflowAction.REDDIT_CATEGORY, nextStep);
    }

    @Test
    void testDefault() {
        WorkflowDataBag bag = new WorkflowDataBag();
        Unknown metadata = new Unknown();
        metadata.setCategory("unknown_category");
        bag.put(WorkflowDataKey.GALLERY_DL_CURRENT_METADATA, metadata);

        WorkflowAction nextStep = step.run(bag);

        assertEquals(WorkflowAction.GENERIC_CATEGORY, nextStep);
    }
}