package com.boatarde.bilubot.factory;

import com.github.lucasaxm.gallerydl.options.GalleryDlOptions;
import com.github.lucasaxm.gallerydl.options.GalleryDlResult;

public class GalleryDlTestFactory {

    private static final String GALLERYDL_PATH = "path/to/gallerydl";

    public static GalleryDlOptions getDefaultGalleryDlOptions() {
        return GalleryDlOptions.builder()
            .config(GALLERYDL_PATH)
            .configIgnore(true)
            .verbose(true)
            .build();
    }

    public static GalleryDlResult buildResult(String url) {
        GalleryDlResult expected = new GalleryDlResult();
        expected.setUrl(url);
        return expected;
    }
}
