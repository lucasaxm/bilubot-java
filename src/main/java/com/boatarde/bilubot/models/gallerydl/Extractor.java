package com.boatarde.bilubot.models.gallerydl;

import com.boatarde.bilubot.models.gallerydl.extractors.Reddit;
import com.boatarde.bilubot.models.gallerydl.extractors.Twitter;
import com.boatarde.bilubot.models.gallerydl.extractors.Ytdl;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Extractor {
    private String filename;
    private Reddit reddit;
    private Twitter twitter;
    private Ytdl ytdl;
}
