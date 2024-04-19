package com.boatarde.bilubot.flows.gallerydl;

import com.boatarde.bilubot.bots.BiluBot;
import com.boatarde.bilubot.flows.WorkflowAction;
import com.boatarde.bilubot.flows.WorkflowDataBag;
import com.boatarde.bilubot.flows.WorkflowDataKey;
import com.github.lucasaxm.gallerydl.metadata.Unknown;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;
import net.bramp.ffmpeg.probe.FFmpegStream;
import org.apache.tika.Tika;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedConstruction;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.methods.send.SendAudio;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMediaGroup;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.send.SendVideo;
import org.telegram.telegrambots.meta.api.objects.Audio;
import org.telegram.telegrambots.meta.api.objects.Document;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Video;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaAudio;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaDocument;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaVideo;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockConstruction;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UploadAndAddInputMediaStepTest {

    @Mock
    private BiluBot biluBot;

    @Test
    void testPhoto() throws Exception {
        try (MockedConstruction<FFprobe> ffprobeMockedConstruction = mockConstruction(FFprobe.class);
             MockedConstruction<Tika> tikaMockedConstruction = mockConstruction(Tika.class)) {
            UploadAndAddInputMediaStep step = new UploadAndAddInputMediaStep("mockPath", "mockChannelId");
            FFprobe ffprobe = ffprobeMockedConstruction.constructed().getFirst();
            Tika tika = tikaMockedConstruction.constructed().getFirst();

            WorkflowDataBag bag = new WorkflowDataBag();

            Unknown metadata = new Unknown();
            metadata.setLocalPath("photo.jpg");
            bag.put(WorkflowDataKey.GALLERY_DL_CURRENT_METADATA, metadata);
            bag.put(WorkflowDataKey.SEND_MEDIA_GROUP, SendMediaGroup.builder()
                .chatId("1234")
                .medias(new ArrayList<>())
                .build());
            bag.put(WorkflowDataKey.BILUBOT, biluBot);

            Message message = new Message();
            PhotoSize photoSize = new PhotoSize();
            photoSize.setFileId("fileId");
            message.setPhoto(List.of(new PhotoSize(), new PhotoSize(), photoSize));

            when(biluBot.execute(any(SendPhoto.class))).thenReturn(message);
            when(tika.detect("photo.jpg")).thenReturn("image/jpeg");

            WorkflowAction nextStep = step.run(bag);

            verifyNoInteractions(ffprobe);
            assertEquals(WorkflowAction.IDENTIFY_CATEGORY_STEP, nextStep);

            SendMediaGroup sendMediaGroup = bag.get(WorkflowDataKey.SEND_MEDIA_GROUP, SendMediaGroup.class);

            assertThat(sendMediaGroup.getMedias())
                .singleElement()
                .isOfAnyClassIn(InputMediaPhoto.class)
                .hasFieldOrPropertyWithValue("media", "fileId");
        }
    }

    @Test
    void testVideo() throws Exception {
        try (MockedConstruction<FFprobe> ffprobeMockedConstruction = mockConstruction(FFprobe.class);
             MockedConstruction<Tika> tikaMockedConstruction = mockConstruction(Tika.class)) {
            UploadAndAddInputMediaStep step = new UploadAndAddInputMediaStep("mockPath", "mockChannelId");
            FFprobe ffprobe = ffprobeMockedConstruction.constructed().getFirst();
            Tika tika = tikaMockedConstruction.constructed().getFirst();

            WorkflowDataBag bag = new WorkflowDataBag();

            Unknown metadata = new Unknown();
            metadata.setLocalPath("video.mp4");
            bag.put(WorkflowDataKey.GALLERY_DL_CURRENT_METADATA, metadata);
            bag.put(WorkflowDataKey.SEND_MEDIA_GROUP, SendMediaGroup.builder()
                .chatId("1234")
                .medias(new ArrayList<>())
                .build());
            bag.put(WorkflowDataKey.BILUBOT, biluBot);

            Message message = new Message();

            Video video = new Video();
            video.setFileId("fileId");

            message.setVideo(video);

            when(biluBot.execute(any(SendVideo.class))).thenReturn(message);
            when(tika.detect("video.mp4")).thenReturn("video/mp4");

            FFmpegProbeResult mockProbeResult = mock(FFmpegProbeResult.class);
            FFmpegStream fFmpegStream = new FFmpegStream();
            fFmpegStream.codec_type = FFmpegStream.CodecType.AUDIO;
            when(mockProbeResult.getStreams()).thenReturn(List.of(fFmpegStream));
            when(ffprobe.probe("video.mp4")).thenReturn(mockProbeResult);

            WorkflowAction nextStep = step.run(bag);

            assertEquals(WorkflowAction.IDENTIFY_CATEGORY_STEP, nextStep);

            SendMediaGroup sendMediaGroup = bag.get(WorkflowDataKey.SEND_MEDIA_GROUP, SendMediaGroup.class);

            assertThat(sendMediaGroup.getMedias())
                .singleElement()
                .isOfAnyClassIn(InputMediaVideo.class)
                .hasFieldOrPropertyWithValue("media", "fileId");
        }
    }

    @Test
    void testVideoNoAudio() throws Exception {
        try (MockedConstruction<FFprobe> ffprobeMockedConstruction = mockConstruction(FFprobe.class);
             MockedConstruction<Tika> tikaMockedConstruction = mockConstruction(Tika.class)) {
            UploadAndAddInputMediaStep step = new UploadAndAddInputMediaStep("mockPath", "mockChannelId");
            FFprobe ffprobe = ffprobeMockedConstruction.constructed().getFirst();
            Tika tika = tikaMockedConstruction.constructed().getFirst();

            WorkflowDataBag bag = new WorkflowDataBag();

            Unknown metadata = new Unknown();
            metadata.setLocalPath("video.mp4");
            bag.put(WorkflowDataKey.GALLERY_DL_CURRENT_METADATA, metadata);
            bag.put(WorkflowDataKey.SEND_MEDIA_GROUP, SendMediaGroup.builder()
                .chatId("1234")
                .medias(new ArrayList<>())
                .build());
            bag.put(WorkflowDataKey.BILUBOT, biluBot);

            Message message = new Message();

            Video video = new Video();
            video.setFileId("fileId");

            message.setVideo(video);

            when(biluBot.execute(any(SendVideo.class))).thenReturn(message);
            when(tika.detect("video.mp4")).thenReturn("video/mp4");

            FFmpegProbeResult mockProbeResult = mock(FFmpegProbeResult.class);
            when(mockProbeResult.getStreams()).thenReturn(List.of());
            when(ffprobe.probe("video.mp4")).thenReturn(mockProbeResult);

            WorkflowAction nextStep = step.run(bag);

            assertEquals(WorkflowAction.IDENTIFY_CATEGORY_STEP, nextStep);

            SendMediaGroup sendMediaGroup = bag.get(WorkflowDataKey.SEND_MEDIA_GROUP, SendMediaGroup.class);

            assertThat(sendMediaGroup.getMedias())
                .singleElement()
                .isOfAnyClassIn(InputMediaVideo.class)
                .hasFieldOrPropertyWithValue("media", "fileId");
        }
    }

    @Test
    void testGif() throws Exception {
        try (MockedConstruction<FFprobe> ffprobeMockedConstruction = mockConstruction(FFprobe.class);
             MockedConstruction<Tika> tikaMockedConstruction = mockConstruction(Tika.class)) {
            UploadAndAddInputMediaStep step = new UploadAndAddInputMediaStep("mockPath", "mockChannelId");
            FFprobe ffprobe = ffprobeMockedConstruction.constructed().getFirst();
            Tika tika = tikaMockedConstruction.constructed().getFirst();

            WorkflowDataBag bag = new WorkflowDataBag();

            Unknown metadata = new Unknown();
            metadata.setLocalPath("photo.gif");
            bag.put(WorkflowDataKey.GALLERY_DL_CURRENT_METADATA, metadata);
            bag.put(WorkflowDataKey.SEND_MEDIA_GROUP, SendMediaGroup.builder()
                .chatId("1234")
                .medias(new ArrayList<>())
                .build());
            bag.put(WorkflowDataKey.BILUBOT, biluBot);

            Message message = new Message();
            Video video = new Video();
            video.setFileId("fileId");

            message.setVideo(video);

            when(biluBot.execute(any(SendVideo.class))).thenReturn(message);
            when(tika.detect("photo.gif")).thenReturn("image/gif");

            WorkflowAction nextStep = step.run(bag);

            verifyNoInteractions(ffprobe);
            assertEquals(WorkflowAction.IDENTIFY_CATEGORY_STEP, nextStep);

            SendMediaGroup sendMediaGroup = bag.get(WorkflowDataKey.SEND_MEDIA_GROUP, SendMediaGroup.class);

            assertThat(sendMediaGroup.getMedias())
                .singleElement()
                .isOfAnyClassIn(InputMediaVideo.class)
                .hasFieldOrPropertyWithValue("media", "fileId");
        }
    }

    @Test
    void testAudio() throws Exception {
        try (MockedConstruction<FFprobe> ffprobeMockedConstruction = mockConstruction(FFprobe.class);
             MockedConstruction<Tika> tikaMockedConstruction = mockConstruction(Tika.class)) {
            UploadAndAddInputMediaStep step = new UploadAndAddInputMediaStep("mockPath", "mockChannelId");
            FFprobe ffprobe = ffprobeMockedConstruction.constructed().getFirst();
            Tika tika = tikaMockedConstruction.constructed().getFirst();

            WorkflowDataBag bag = new WorkflowDataBag();

            Unknown metadata = new Unknown();
            metadata.setLocalPath("audio.mp3");
            bag.put(WorkflowDataKey.GALLERY_DL_CURRENT_METADATA, metadata);
            bag.put(WorkflowDataKey.SEND_MEDIA_GROUP, SendMediaGroup.builder()
                .chatId("1234")
                .medias(new ArrayList<>())
                .build());
            bag.put(WorkflowDataKey.BILUBOT, biluBot);

            Message message = new Message();
            Audio audio = new Audio();
            audio.setFileId("fileId");

            message.setAudio(audio);

            when(biluBot.execute(any(SendAudio.class))).thenReturn(message);
            when(tika.detect("audio.mp3")).thenReturn("audio/mp3");

            WorkflowAction nextStep = step.run(bag);

            verifyNoInteractions(ffprobe);
            assertEquals(WorkflowAction.IDENTIFY_CATEGORY_STEP, nextStep);

            SendMediaGroup sendMediaGroup = bag.get(WorkflowDataKey.SEND_MEDIA_GROUP, SendMediaGroup.class);

            assertThat(sendMediaGroup.getMedias())
                .singleElement()
                .isOfAnyClassIn(InputMediaAudio.class)
                .hasFieldOrPropertyWithValue("media", "fileId");
        }
    }

    @Test
    void testDocument() throws Exception {
        try (MockedConstruction<FFprobe> ffprobeMockedConstruction = mockConstruction(FFprobe.class);
             MockedConstruction<Tika> tikaMockedConstruction = mockConstruction(Tika.class)) {
            UploadAndAddInputMediaStep step = new UploadAndAddInputMediaStep("mockPath", "mockChannelId");
            FFprobe ffprobe = ffprobeMockedConstruction.constructed().getFirst();
            Tika tika = tikaMockedConstruction.constructed().getFirst();

            WorkflowDataBag bag = new WorkflowDataBag();

            Unknown metadata = new Unknown();
            metadata.setLocalPath("document.zip");
            bag.put(WorkflowDataKey.GALLERY_DL_CURRENT_METADATA, metadata);
            bag.put(WorkflowDataKey.SEND_MEDIA_GROUP, SendMediaGroup.builder()
                .chatId("1234")
                .medias(new ArrayList<>())
                .build());
            bag.put(WorkflowDataKey.BILUBOT, biluBot);

            Message message = new Message();
            Document document = new Document();
            document.setFileId("fileId");

            message.setDocument(document);

            when(biluBot.execute(any(SendDocument.class))).thenReturn(message);
            when(tika.detect("document.zip")).thenReturn("application/x-zip");

            WorkflowAction nextStep = step.run(bag);

            verifyNoInteractions(ffprobe);
            assertEquals(WorkflowAction.IDENTIFY_CATEGORY_STEP, nextStep);

            SendMediaGroup sendMediaGroup = bag.get(WorkflowDataKey.SEND_MEDIA_GROUP, SendMediaGroup.class);

            assertThat(sendMediaGroup.getMedias())
                .singleElement()
                .isOfAnyClassIn(InputMediaDocument.class)
                .hasFieldOrPropertyWithValue("media", "fileId");
        }
    }

    @Test
    void testTelegramError() throws Exception {
        try (MockedConstruction<FFprobe> ffprobeMockedConstruction = mockConstruction(FFprobe.class);
             MockedConstruction<Tika> tikaMockedConstruction = mockConstruction(Tika.class)) {
            UploadAndAddInputMediaStep step = new UploadAndAddInputMediaStep("mockPath", "mockChannelId");
            FFprobe ffprobe = ffprobeMockedConstruction.constructed().getFirst();
            Tika tika = tikaMockedConstruction.constructed().getFirst();

            WorkflowDataBag bag = new WorkflowDataBag();

            Unknown metadata = new Unknown();
            metadata.setLocalPath("document.zip");
            bag.put(WorkflowDataKey.GALLERY_DL_CURRENT_METADATA, metadata);
            bag.put(WorkflowDataKey.SEND_MEDIA_GROUP, SendMediaGroup.builder()
                .chatId("1234")
                .medias(new ArrayList<>())
                .build());
            bag.put(WorkflowDataKey.BILUBOT, biluBot);

            when(biluBot.execute(any(SendDocument.class))).thenThrow(new TelegramApiException());
            when(tika.detect("document.zip")).thenReturn("application/x-zip");

            WorkflowAction nextStep = step.run(bag);

            verifyNoInteractions(ffprobe);
            assertEquals(WorkflowAction.NONE, nextStep);

            SendMediaGroup sendMediaGroup = bag.get(WorkflowDataKey.SEND_MEDIA_GROUP, SendMediaGroup.class);

            assertThat(sendMediaGroup.getMedias())
                .isEmpty();
        }
    }

    @Test
    void testIOError() throws Exception {
        try (MockedConstruction<FFprobe> ffprobeMockedConstruction = mockConstruction(FFprobe.class);
             MockedConstruction<Tika> tikaMockedConstruction = mockConstruction(Tika.class)) {
            UploadAndAddInputMediaStep step = new UploadAndAddInputMediaStep("mockPath", "mockChannelId");
            FFprobe ffprobe = ffprobeMockedConstruction.constructed().getFirst();
            Tika tika = tikaMockedConstruction.constructed().getFirst();

            WorkflowDataBag bag = new WorkflowDataBag();

            Unknown metadata = new Unknown();
            metadata.setLocalPath("video.mp4");
            bag.put(WorkflowDataKey.GALLERY_DL_CURRENT_METADATA, metadata);
            bag.put(WorkflowDataKey.SEND_MEDIA_GROUP, SendMediaGroup.builder()
                .chatId("1234")
                .medias(new ArrayList<>())
                .build());
            bag.put(WorkflowDataKey.BILUBOT, biluBot);

            when(tika.detect("video.mp4")).thenReturn("video/mp4");

            when(ffprobe.probe("video.mp4")).thenThrow(new IOException());

            WorkflowAction nextStep = step.run(bag);

            assertEquals(WorkflowAction.NONE, nextStep);

            SendMediaGroup sendMediaGroup = bag.get(WorkflowDataKey.SEND_MEDIA_GROUP, SendMediaGroup.class);

            assertThat(sendMediaGroup.getMedias())
                .isEmpty();
        }
    }

}