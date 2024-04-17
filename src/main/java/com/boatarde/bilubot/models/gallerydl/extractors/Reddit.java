package com.boatarde.bilubot.models.gallerydl.extractors;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Reddit {
    private String clientId;
    private String clientSecret;
    private String userAgent;
}