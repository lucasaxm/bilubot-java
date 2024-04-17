package com.boatarde.bilubot.models.gallerydl.downloaders;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Ytdl {
    private String cmdlineArgs;
    private String module;
}
