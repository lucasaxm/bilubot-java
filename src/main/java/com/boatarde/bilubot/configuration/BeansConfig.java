package com.boatarde.bilubot.configuration;

import com.github.lucasaxm.gallerydl.GalleryDl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeansConfig {

    @Value("${gallerydl.binary-path}")
    private String galleryDlPath;

    @Bean
    public GalleryDl galleryDl() {
        return new GalleryDl(galleryDlPath);
    }

}
