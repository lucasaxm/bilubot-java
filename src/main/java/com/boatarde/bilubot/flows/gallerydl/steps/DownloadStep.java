package com.boatarde.bilubot.flows.gallerydl.steps;

import com.boatarde.bilubot.flows.FlowContext;
import com.boatarde.bilubot.flows.Step;
import com.github.lucasaxm.gallerydl.GalleryDl;
import com.github.lucasaxm.gallerydl.options.GalleryDlOptions;
import com.github.lucasaxm.gallerydl.options.GalleryDlResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class DownloadStep implements Step {

    private final GalleryDl galleryDl;
    private final SendGalleryDlResultStep sendGalleryDlResultStep;

    public DownloadStep(GalleryDl galleryDl,
                        SendGalleryDlResultStep sendGalleryDlResultStep) {
        this.galleryDl = galleryDl;
        this.sendGalleryDlResultStep = sendGalleryDlResultStep;
    }

    @Override
    public Optional<Step> run(FlowContext context) {
        Update update = context.getUpdate();

        List<GalleryDlResult> results = update.getMessage().getEntities().stream()
            .filter(entity -> "url".equalsIgnoreCase(entity.getType()))
            .map(MessageEntity::getText)
            .map(url -> galleryDl.download(url, GalleryDlOptions.builder()
                .verbose(true)
                .build())
            ).toList();
        context.setGalleryDlResults(results);
        return Optional.of(sendGalleryDlResultStep);
    }
}
