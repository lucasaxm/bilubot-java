package com.boatarde.bilubot.flows.gallerydl;

import com.boatarde.bilubot.flows.WorkflowAction;
import com.boatarde.bilubot.flows.WorkflowDataBag;
import com.boatarde.bilubot.flows.WorkflowDataKey;
import com.boatarde.bilubot.flows.WorkflowStep;
import com.boatarde.bilubot.flows.WorkflowStepRegistration;
import com.github.lucasaxm.gallerydl.GalleryDl;
import com.github.lucasaxm.gallerydl.exception.GalleryDlException;
import com.github.lucasaxm.gallerydl.options.GalleryDlOptions;
import com.github.lucasaxm.gallerydl.options.GalleryDlResult;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.objects.EntityType;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Slf4j
@WorkflowStepRegistration(WorkflowAction.DOWNLOAD)
public class DownloadStep implements WorkflowStep {

    private final GalleryDl galleryDl;

    public DownloadStep(GalleryDl galleryDl) {
        this.galleryDl = galleryDl;
    }

    @Override
    public WorkflowAction run(WorkflowDataBag bag) {
        Update update = bag.get(WorkflowDataKey.TELEGRAM_UPDATE, Update.class);

        try {
            List<GalleryDlResult> results = update.getMessage().getEntities().stream()
                .filter(entity -> EntityType.URL.equalsIgnoreCase(entity.getType()))
                .map(MessageEntity::getText)
                .map(url -> galleryDl.download(url, GalleryDlOptions.builder()
                    .verbose(true)
                    .build())
                ).toList();
            bag.put(WorkflowDataKey.GALLERY_DL_RESULTS, results);
        } catch (GalleryDlException e) {
            log.error(String.format("Gallery-dl error message: %s.\nGallery-dl result: %s)", e.getMessage(),
                e.getGalleryDlResult().toString()), e);
            return WorkflowAction.NONE;
        }
        return WorkflowAction.GET_NEXT_GALLERY_DL_RESULT;
    }
}
