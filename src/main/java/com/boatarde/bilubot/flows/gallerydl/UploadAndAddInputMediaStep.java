package com.boatarde.bilubot.flows.gallerydl;

import com.boatarde.bilubot.bots.BiluBot;
import com.boatarde.bilubot.flows.WorkflowAction;
import com.boatarde.bilubot.flows.WorkflowDataBag;
import com.boatarde.bilubot.flows.WorkflowDataKey;
import com.boatarde.bilubot.flows.WorkflowStep;
import com.boatarde.bilubot.flows.WorkflowStepRegistration;
import com.boatarde.bilubot.util.InputMediaType;
import com.github.lucasaxm.gallerydl.metadata.Metadata;
import lombok.extern.slf4j.Slf4j;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;
import net.bramp.ffmpeg.probe.FFmpegStream;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Value;
import org.telegram.telegrambots.meta.api.methods.send.SendAudio;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMediaGroup;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.send.SendVideo;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaAudio;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaDocument;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaVideo;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.io.IOException;

@Slf4j
@WorkflowStepRegistration(WorkflowAction.UPLOAD_AND_ADD_INPUT_MEDIA)
public class UploadAndAddInputMediaStep implements WorkflowStep {

    private final FFprobe ffprobe;
    private final Tika tika;
    private final String uploadChannelId;

    public UploadAndAddInputMediaStep(@Value("${ffprobe.path}") String ffprobePath,
                                      @Value("${telegram.bots.bilubot.upload-channel-id}") String uploadChannelId)
        throws IOException {
        this.ffprobe = new FFprobe(ffprobePath);
        this.uploadChannelId = uploadChannelId;
        this.tika = new Tika();
    }

    @Override
    public WorkflowAction run(WorkflowDataBag bag) {
        Metadata metadata = bag.get(WorkflowDataKey.GALLERY_DL_CURRENT_METADATA, Metadata.class);
        SendMediaGroup sendMediaGroup = bag.get(WorkflowDataKey.SEND_MEDIA_GROUP, SendMediaGroup.class);
        BiluBot biluBot = bag.get(WorkflowDataKey.BILUBOT, BiluBot.class);

        InputMediaType fileType;
        try {
            fileType = getFileType(metadata.getLocalPath());
            switch (fileType) {
                case PHOTO -> {
                    Message response = biluBot.execute(SendPhoto.builder()
                        .chatId(uploadChannelId)
                        .photo(new InputFile(new File(metadata.getLocalPath())))
                        .build());
                    sendMediaGroup.getMedias().add(InputMediaPhoto.builder()
                        .media(response.getPhoto().getLast().getFileId())
                        .build());
                }
                case VIDEO, ANIMATION -> {
                    Message response = biluBot.execute(SendVideo.builder()
                        .chatId(uploadChannelId)
                        .video(new InputFile(new File(metadata.getLocalPath())))
                        .supportsStreaming(true)
                        .build());
                    sendMediaGroup.getMedias().add(InputMediaVideo.builder()
                        .media(response.getVideo().getFileId())
                        .build());
                }
                case AUDIO -> {
                    Message response = biluBot.execute(SendAudio.builder()
                        .chatId(uploadChannelId)
                        .audio(new InputFile(new File(metadata.getLocalPath())))
                        .build());
                    sendMediaGroup.getMedias().add(InputMediaAudio.builder()
                        .media(response.getAudio().getFileId())
                        .build());
                }
                case DOCUMENT -> {
                    Message response = biluBot.execute(SendDocument.builder()
                        .chatId(uploadChannelId)
                        .document(new InputFile(new File(metadata.getLocalPath())))
                        .build());
                    sendMediaGroup.getMedias().add(InputMediaDocument.builder()
                        .media(response.getDocument().getFileId())
                        .build());
                }
            }
        } catch (IOException | TelegramApiException e) {
            log.error(String.format("Error when uploading media: %s", e.getMessage()), e);
            return WorkflowAction.NONE;
        }

        return WorkflowAction.IDENTIFY_CATEGORY_STEP;
    }

    private InputMediaType getFileType(String path) throws IOException {
        String mimeType = tika.detect(path);
        String mainType = mimeType.split("/")[0];
        String subType = mimeType.split("/")[1];

        if (subType.equals("gif")) {
            return InputMediaType.ANIMATION;
        } else if (mainType.equals("image")) {
            return InputMediaType.PHOTO;
        } else if (mainType.equals("audio")) {
            return InputMediaType.AUDIO;
        } else if (mainType.equals("video")) {
            FFmpegProbeResult probeResult = ffprobe.probe(path);
            boolean hasAudio = probeResult.getStreams().stream()
                .anyMatch(stream -> stream.codec_type == FFmpegStream.CodecType.AUDIO);
            if (!hasAudio) {
                return InputMediaType.ANIMATION;
            } else {
                return InputMediaType.VIDEO;
            }
        } else {
            return InputMediaType.DOCUMENT;
        }
    }
}
