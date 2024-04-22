package com.boatarde.bilubot.models.gallerydl;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "gallerydl")
public class GalleryDlProperties {
    private String binaryPath;
    private String configPath;
    private GalleryDlConfig config;
}
