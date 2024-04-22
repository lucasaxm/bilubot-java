package com.boatarde.bilubot.models.gallerydl.extractors;

import com.boatarde.bilubot.models.gallerydl.extractors.ytdl.Facebook;
import com.boatarde.bilubot.models.gallerydl.extractors.ytdl.Steam;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Ytdl {
    private boolean enabled;
    private String format;
    private boolean logging;
    private String cmdlineArgs;
    private String module;
    private Facebook facebook;
    private Steam steam;
}
