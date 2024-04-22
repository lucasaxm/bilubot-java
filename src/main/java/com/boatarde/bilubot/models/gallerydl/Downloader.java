package com.boatarde.bilubot.models.gallerydl;

import com.boatarde.bilubot.models.gallerydl.downloaders.Ytdl;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Downloader {
    private String filesizeMax;
    private Ytdl ytdl;
}
