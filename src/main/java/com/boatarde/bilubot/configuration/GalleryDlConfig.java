package com.boatarde.bilubot.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.github.lucasaxm.gallerydl.GalleryDl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GalleryDlConfig {

    @Value("${gallerydl.path}")
    private String galleryDlPath;

    @Bean
    public GalleryDl galleryDl() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        return new GalleryDl(galleryDlPath, mapper);
    }
}
