package com.boatarde.bilubot.models.gallerydl;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GalleryDlConfig {
    private Extractor extractor;
    private Downloader downloader;
}
