package com.boatarde.bilubot.flows.gallerydl;

import com.boatarde.bilubot.flows.WorkflowAction;
import com.boatarde.bilubot.flows.WorkflowDataBag;
import com.boatarde.bilubot.flows.WorkflowDataKey;
import com.boatarde.bilubot.flows.WorkflowStep;
import com.boatarde.bilubot.flows.WorkflowStepRegistration;
import com.github.lucasaxm.gallerydl.options.GalleryDlResult;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.ListIterator;

@Slf4j
@WorkflowStepRegistration(WorkflowAction.GET_NEXT_GALLERY_DL_RESULT)
public class GetNextResultStep implements WorkflowStep {

    @Override
    public WorkflowAction run(WorkflowDataBag bag) {
        List<GalleryDlResult> results =
            bag.getGeneric(WorkflowDataKey.GALLERY_DL_RESULTS, List.class, GalleryDlResult.class);
        bag.putIfAbsent(WorkflowDataKey.GALLERY_DL_RESULTS_ITERATOR, results.listIterator());
        ListIterator<GalleryDlResult> galleryDlResultsIterator =
            bag.getGeneric(WorkflowDataKey.GALLERY_DL_RESULTS_ITERATOR, ListIterator.class, GalleryDlResult.class);

        if (!galleryDlResultsIterator.hasNext()) {
            log.info("No more results to send. Total sent: {}", galleryDlResultsIterator.nextIndex());
            return WorkflowAction.NONE;
        }
        bag.put(WorkflowDataKey.GALLERY_DL_CURRENT_RESULT, galleryDlResultsIterator.next());

        return WorkflowAction.GET_NEXT_GALLERY_DL_METADATA;
    }
}