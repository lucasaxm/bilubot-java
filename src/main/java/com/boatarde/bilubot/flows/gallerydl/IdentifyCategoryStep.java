package com.boatarde.bilubot.flows.gallerydl;

import com.boatarde.bilubot.flows.WorkflowAction;
import com.boatarde.bilubot.flows.WorkflowDataBag;
import com.boatarde.bilubot.flows.WorkflowDataKey;
import com.boatarde.bilubot.flows.WorkflowStep;
import com.boatarde.bilubot.flows.WorkflowStepRegistration;
import com.github.lucasaxm.gallerydl.metadata.Metadata;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@WorkflowStepRegistration(WorkflowAction.IDENTIFY_CATEGORY_STEP)
public class IdentifyCategoryStep implements WorkflowStep {
    @Override
    public WorkflowAction run(WorkflowDataBag bag) {
        Metadata metadata = bag.get(WorkflowDataKey.GALLERY_DL_CURRENT_METADATA, Metadata.class);
        String category = metadata.getCategory().toLowerCase();
        return switch (category) {
            case "twitter" -> WorkflowAction.TWITTER_CATEGORY;
            case "reddit" -> WorkflowAction.REDDIT_CATEGORY;
            default -> WorkflowAction.GENERIC_CATEGORY;
        };
    }
}
