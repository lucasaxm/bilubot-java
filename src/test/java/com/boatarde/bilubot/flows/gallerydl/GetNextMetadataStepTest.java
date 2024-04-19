package com.boatarde.bilubot.flows.gallerydl;

import com.boatarde.bilubot.factory.GalleryDlTestFactory;
import com.boatarde.bilubot.factory.TelegramTestFactory;
import com.boatarde.bilubot.flows.WorkflowAction;
import com.boatarde.bilubot.flows.WorkflowDataBag;
import com.boatarde.bilubot.flows.WorkflowDataKey;
import com.github.lucasaxm.gallerydl.metadata.Metadata;
import com.github.lucasaxm.gallerydl.metadata.Unknown;
import com.github.lucasaxm.gallerydl.options.GalleryDlResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.methods.send.SendMediaGroup;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.ListIterator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class GetNextMetadataStepTest {

    private GetNextMetadataStep step;

    @BeforeEach
    void setUp() {
        step = new GetNextMetadataStep();
    }

    @Test
    void testFirstMetadata() {
        WorkflowDataBag bag = new WorkflowDataBag();

        List<Metadata> metadataList = getMetadataList();
        GalleryDlResult galleryDlResult = GalleryDlTestFactory.buildResult("www.test.com");
        galleryDlResult.setMetadata(metadataList);
        bag.put(WorkflowDataKey.GALLERY_DL_CURRENT_RESULT, galleryDlResult);

        Update update = TelegramTestFactory.buildUrlTextMessageUpdate(List.of("www.test.com"));
        bag.put(WorkflowDataKey.TELEGRAM_UPDATE, update);

        WorkflowAction nextStep = step.run(bag);

        assertEquals(WorkflowAction.UPLOAD_AND_ADD_INPUT_MEDIA, nextStep);

        ListIterator<Metadata> metadataIterator =
            bag.getGeneric(WorkflowDataKey.GALLERY_DL_CURRENT_RESULT_METADATA_ITERATOR, ListIterator.class,
                Metadata.class);
        assertEquals(1, metadataIterator.nextIndex());
        assertTrue(metadataIterator.hasNext());

        assertEquals(metadataList.getFirst(), bag.get(WorkflowDataKey.GALLERY_DL_CURRENT_METADATA, Metadata.class));

        assertThat(bag.get(WorkflowDataKey.SEND_MEDIA_GROUP, SendMediaGroup.class))
            .hasFieldOrPropertyWithValue("chatId", "1234")
            .hasFieldOrPropertyWithValue("replyToMessageId", 5678);
    }

    @Test
    void testNextMetadata() {
        WorkflowDataBag bag = new WorkflowDataBag();

        List<Metadata> metadataList = getMetadataList();
        GalleryDlResult galleryDlResult = GalleryDlTestFactory.buildResult("www.test.com");
        galleryDlResult.setMetadata(metadataList);
        ListIterator<Metadata> iterator = metadataList.listIterator();
        iterator.next();
        bag.put(WorkflowDataKey.GALLERY_DL_CURRENT_RESULT_METADATA_ITERATOR, iterator);
        bag.put(WorkflowDataKey.GALLERY_DL_CURRENT_RESULT, galleryDlResult);

        Update update = TelegramTestFactory.buildUrlTextMessageUpdate(List.of("www.test.com"));
        bag.put(WorkflowDataKey.TELEGRAM_UPDATE, update);

        WorkflowAction nextStep = step.run(bag);

        assertEquals(WorkflowAction.UPLOAD_AND_ADD_INPUT_MEDIA, nextStep);

        iterator = bag.getGeneric(WorkflowDataKey.GALLERY_DL_CURRENT_RESULT_METADATA_ITERATOR, ListIterator.class,
            Metadata.class);
        assertEquals(2, iterator.nextIndex());
        assertFalse(iterator.hasNext());

        assertEquals(metadataList.getLast(), bag.get(WorkflowDataKey.GALLERY_DL_CURRENT_METADATA, Metadata.class));

        assertThat(bag.get(WorkflowDataKey.SEND_MEDIA_GROUP, SendMediaGroup.class))
            .hasFieldOrPropertyWithValue("chatId", "1234")
            .hasFieldOrPropertyWithValue("replyToMessageId", 5678);
    }

    @Test
    void testNoMoreMetadata() {
        WorkflowDataBag bag = new WorkflowDataBag();

        List<Metadata> metadataList = getMetadataList();
        GalleryDlResult galleryDlResult = GalleryDlTestFactory.buildResult("www.test.com");
        galleryDlResult.setMetadata(metadataList);
        ListIterator<Metadata> iterator = metadataList.listIterator();
        iterator.next();
        iterator.next();
        bag.put(WorkflowDataKey.GALLERY_DL_CURRENT_RESULT_METADATA_ITERATOR, iterator);
        bag.put(WorkflowDataKey.GALLERY_DL_CURRENT_RESULT, galleryDlResult);

        Update update = TelegramTestFactory.buildUrlTextMessageUpdate(List.of("www.test.com"));
        bag.put(WorkflowDataKey.TELEGRAM_UPDATE, update);

        WorkflowAction nextStep = step.run(bag);

        assertEquals(WorkflowAction.GET_NEXT_GALLERY_DL_RESULT, nextStep);

        assertNull(bag.getGeneric(WorkflowDataKey.GALLERY_DL_CURRENT_RESULT_METADATA_ITERATOR, ListIterator.class,
            Metadata.class));
        assertNull(bag.get(WorkflowDataKey.GALLERY_DL_CURRENT_METADATA, Metadata.class));
        assertNull(bag.get(WorkflowDataKey.SEND_MEDIA_GROUP, SendMediaGroup.class));
    }

    private List<Metadata> getMetadataList() {
        List<Metadata> metadataList = List.of(new Unknown(), new Unknown());
        metadataList.getFirst().setCategory("metadata1");
        metadataList.getLast().setCategory("metadata2");
        return metadataList;
    }
}