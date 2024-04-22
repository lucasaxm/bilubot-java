package com.boatarde.bilubot.flows.gallerydl;

import com.boatarde.bilubot.factory.GalleryDlTestFactory;
import com.boatarde.bilubot.flows.WorkflowAction;
import com.boatarde.bilubot.flows.WorkflowDataBag;
import com.boatarde.bilubot.flows.WorkflowDataKey;
import com.github.lucasaxm.gallerydl.options.GalleryDlResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.ListIterator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class GetNextResultStepTest {

    private GetNextResultStep step;

    @BeforeEach
    void setUp() {
        step = new GetNextResultStep();
    }

    @Test
    void testFirstResult() {
        WorkflowDataBag bag = new WorkflowDataBag();

        List<GalleryDlResult> resultList = getGalleryDlResultList();
        bag.put(WorkflowDataKey.GALLERY_DL_RESULTS, resultList);

        WorkflowAction nextStep = step.run(bag);

        assertEquals(WorkflowAction.GET_NEXT_GALLERY_DL_METADATA, nextStep);

        ListIterator<GalleryDlResult> resultsIterator =
            bag.getGeneric(WorkflowDataKey.GALLERY_DL_RESULTS_ITERATOR, ListIterator.class, GalleryDlResult.class);

        assertEquals(1, resultsIterator.nextIndex());
        assertTrue(resultsIterator.hasNext());

        assertEquals(resultList.getFirst(), bag.get(WorkflowDataKey.GALLERY_DL_CURRENT_RESULT, GalleryDlResult.class));
    }

    @Test
    void testNextMetadata() {
        WorkflowDataBag bag = new WorkflowDataBag();

        List<GalleryDlResult> resultList = getGalleryDlResultList();
        ListIterator<GalleryDlResult> iterator = resultList.listIterator();
        iterator.next();
        bag.put(WorkflowDataKey.GALLERY_DL_RESULTS, resultList);
        bag.put(WorkflowDataKey.GALLERY_DL_RESULTS_ITERATOR, iterator);

        WorkflowAction nextStep = step.run(bag);

        assertEquals(WorkflowAction.GET_NEXT_GALLERY_DL_METADATA, nextStep);

        iterator = bag.getGeneric(WorkflowDataKey.GALLERY_DL_RESULTS_ITERATOR, ListIterator.class,
            GalleryDlResult.class);
        assertEquals(2, iterator.nextIndex());
        assertFalse(iterator.hasNext());

        assertEquals(resultList.getLast(), bag.get(WorkflowDataKey.GALLERY_DL_CURRENT_RESULT, GalleryDlResult.class));
    }

    @Test
    void testNoMoreResults() {
        WorkflowDataBag bag = new WorkflowDataBag();

        List<GalleryDlResult> resultList = getGalleryDlResultList();
        ListIterator<GalleryDlResult> iterator = resultList.listIterator();
        iterator.next();
        iterator.next();
        bag.put(WorkflowDataKey.GALLERY_DL_RESULTS, resultList);
        bag.put(WorkflowDataKey.GALLERY_DL_RESULTS_ITERATOR, iterator);

        WorkflowAction nextStep = step.run(bag);

        assertEquals(WorkflowAction.NONE, nextStep);

        assertNull(bag.get(WorkflowDataKey.GALLERY_DL_CURRENT_RESULT, GalleryDlResult.class));
    }

    private List<GalleryDlResult> getGalleryDlResultList() {
        return List.of(GalleryDlTestFactory.buildResult("www.test.com"),
            GalleryDlTestFactory.buildResult("www.test2.com"));
    }
}