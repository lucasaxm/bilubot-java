package com.boatarde.bilubot.flows.gallerydl;

import com.boatarde.bilubot.bots.BiluBot;
import com.boatarde.bilubot.flows.WorkflowAction;
import com.boatarde.bilubot.flows.WorkflowDataBag;
import com.boatarde.bilubot.flows.WorkflowDataKey;
import com.boatarde.bilubot.util.TelegramUtils;
import com.github.lucasaxm.gallerydl.metadata.Unknown;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.methods.send.SendMediaGroup;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.media.InputMedia;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SendMediaGroupStepTest {

    private SendMediaGroupStep step;

    private MockedStatic<TelegramUtils> mockedTelegramUtils;

    @Mock
    private BiluBot biluBot;

    @BeforeEach
    void setUp() {
        step = new SendMediaGroupStep();
        mockedTelegramUtils = mockStatic(TelegramUtils.class);
    }

    @AfterEach
    void tearDown() {
        mockedTelegramUtils.close();
    }

    @Test
    void testSendSingleMedia() {
        WorkflowDataBag bag = new WorkflowDataBag();
        bag.put(WorkflowDataKey.BILUBOT, biluBot);
        bag.put(WorkflowDataKey.GALLERY_DL_CURRENT_RESULT_METADATA_ITERATOR, List.of().listIterator());
        SendMediaGroup sendMediaGroup = SendMediaGroup.builder()
            .chatId("1234")
            .medias(List.of(InputMediaPhoto.builder()
                .media("fileId1")
                .build()))
            .build();
        bag.put(WorkflowDataKey.SEND_MEDIA_GROUP, sendMediaGroup);
        mockedTelegramUtils.when(() -> TelegramUtils.executeSendMediaBotMethod(any(), any())).thenReturn(new Message());

        WorkflowAction nextStep = step.run(bag);

        assertEquals(WorkflowAction.GET_NEXT_GALLERY_DL_METADATA, nextStep);
        assertNull(bag.get(WorkflowDataKey.SEND_MEDIA_GROUP, SendMediaGroup.class));
        mockedTelegramUtils.verify(() -> TelegramUtils.inputMediaToSendMedia(sendMediaGroup, 0));
        mockedTelegramUtils.verify(() -> TelegramUtils.executeSendMediaBotMethod(any(), any()));
    }

    @Test
    void testHasMoreMetadataAndMediaGroupNotFull() {
        WorkflowDataBag bag = new WorkflowDataBag();
        bag.put(WorkflowDataKey.BILUBOT, biluBot);
        bag.put(WorkflowDataKey.GALLERY_DL_CURRENT_RESULT_METADATA_ITERATOR, List.of(new Unknown()).listIterator());
        SendMediaGroup sendMediaGroup = SendMediaGroup.builder()
            .chatId("1234")
            .medias(List.of(InputMediaPhoto.builder()
                .media("fileId1")
                .build(), InputMediaPhoto.builder()
                .media("fileId2")
                .build()))
            .build();
        bag.put(WorkflowDataKey.SEND_MEDIA_GROUP, sendMediaGroup);

        WorkflowAction nextStep = step.run(bag);

        assertEquals(WorkflowAction.GET_NEXT_GALLERY_DL_METADATA, nextStep);
        assertEquals(sendMediaGroup, bag.get(WorkflowDataKey.SEND_MEDIA_GROUP, SendMediaGroup.class));
        verifyNoInteractions(biluBot);
    }

    @Test
    void testHasMoreMetadataButMediaGroupIsFull() throws TelegramApiException {
        WorkflowDataBag bag = new WorkflowDataBag();
        bag.put(WorkflowDataKey.BILUBOT, biluBot);
        bag.put(WorkflowDataKey.GALLERY_DL_CURRENT_RESULT_METADATA_ITERATOR, List.of(new Unknown()).listIterator());
        List<InputMedia> inputMedia = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            inputMedia.add(InputMediaPhoto.builder()
                .media(String.format("fileId%d", i))
                .build());
        }
        SendMediaGroup sendMediaGroup = SendMediaGroup.builder()
            .chatId("1234")
            .medias(inputMedia)
            .build();
        bag.put(WorkflowDataKey.SEND_MEDIA_GROUP, sendMediaGroup);

        WorkflowAction nextStep = step.run(bag);

        assertEquals(WorkflowAction.GET_NEXT_GALLERY_DL_METADATA, nextStep);
        assertNull(bag.get(WorkflowDataKey.SEND_MEDIA_GROUP, SendMediaGroup.class));
        verify(biluBot).execute(sendMediaGroup);
    }


    @Test
    void testHasNoMoreData() throws TelegramApiException {
        WorkflowDataBag bag = new WorkflowDataBag();
        bag.put(WorkflowDataKey.BILUBOT, biluBot);
        bag.put(WorkflowDataKey.GALLERY_DL_CURRENT_RESULT_METADATA_ITERATOR, List.of().listIterator());
        SendMediaGroup sendMediaGroup = SendMediaGroup.builder()
            .chatId("1234")
            .medias(List.of(InputMediaPhoto.builder()
                .media("fileId1")
                .build(), InputMediaPhoto.builder()
                .media("fileId2")
                .build()))
            .build();
        bag.put(WorkflowDataKey.SEND_MEDIA_GROUP, sendMediaGroup);

        WorkflowAction nextStep = step.run(bag);

        assertEquals(WorkflowAction.GET_NEXT_GALLERY_DL_METADATA, nextStep);
        assertNull(bag.get(WorkflowDataKey.SEND_MEDIA_GROUP, SendMediaGroup.class));
        verify(biluBot).execute(sendMediaGroup);
    }

    @Test
    void testTelegramApiException() throws TelegramApiException {
        WorkflowDataBag bag = new WorkflowDataBag();
        bag.put(WorkflowDataKey.BILUBOT, biluBot);
        bag.put(WorkflowDataKey.GALLERY_DL_CURRENT_RESULT_METADATA_ITERATOR, List.of().listIterator());
        SendMediaGroup sendMediaGroup = SendMediaGroup.builder()
            .chatId("1234")
            .medias(List.of(InputMediaPhoto.builder()
                .media("fileId1")
                .build(), InputMediaPhoto.builder()
                .media("fileId2")
                .build()))
            .build();
        bag.put(WorkflowDataKey.SEND_MEDIA_GROUP, sendMediaGroup);

        when(biluBot.execute(any(SendMediaGroup.class))).thenThrow(new TelegramApiException());
        WorkflowAction nextStep = step.run(bag);

        assertEquals(WorkflowAction.NONE, nextStep);
    }

}