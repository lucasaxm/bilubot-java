package com.boatarde.bilubot.flows.gallerydl;

import com.boatarde.bilubot.bots.BiluBot;
import com.boatarde.bilubot.factory.GalleryDlTestFactory;
import com.boatarde.bilubot.factory.TelegramTestFactory;
import com.boatarde.bilubot.flows.WorkflowAction;
import com.boatarde.bilubot.flows.WorkflowDataBag;
import com.boatarde.bilubot.flows.WorkflowDataKey;
import com.boatarde.bilubot.models.gallerydl.GalleryDlProperties;
import com.github.lucasaxm.gallerydl.GalleryDl;
import com.github.lucasaxm.gallerydl.exception.GalleryDlException;
import com.github.lucasaxm.gallerydl.options.GalleryDlOptions;
import com.github.lucasaxm.gallerydl.options.GalleryDlResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DownloadStepTest {
    public static final String TEST_URL = "www.test.com";
    public static final String TEST2_URL = "www.test2.com";
    @Mock
    private BiluBot biluBot;

    @Mock
    private GalleryDl galleryDl;

    @Mock
    private GalleryDlProperties galleryDlProperties;

    private DownloadStep step;

    @BeforeEach
    void setUp() {
        step = new DownloadStep(galleryDl, galleryDlProperties);
    }

    @Test
    void testDownloadSingleUrl() {
        WorkflowDataBag bag = new WorkflowDataBag();
        Update update = TelegramTestFactory.buildUrlTextMessageUpdate(List.of(TEST_URL));
        bag.put(WorkflowDataKey.TELEGRAM_UPDATE, update);
        bag.put(WorkflowDataKey.BILUBOT, biluBot);
        GalleryDlOptions defaultGalleryDlOptions = GalleryDlTestFactory.getDefaultGalleryDlOptions();
        when(galleryDlProperties.getConfigPath()).thenReturn(defaultGalleryDlOptions.getConfig());
        List<GalleryDlResult> expected = List.of(GalleryDlTestFactory.buildResult(TEST_URL));
        when(galleryDl.download(TEST_URL, defaultGalleryDlOptions)).thenReturn(expected.getFirst());

        WorkflowAction nextStep = step.run(bag);

        verify(galleryDl).download(TEST_URL, defaultGalleryDlOptions);

        assertEquals(WorkflowAction.GET_NEXT_GALLERY_DL_RESULT, nextStep);

        List<GalleryDlResult> results =
            bag.getGeneric(WorkflowDataKey.GALLERY_DL_RESULTS, List.class, GalleryDlResult.class);
        assertThat(results)
            .isEqualTo(expected);
    }


    @Test
    void testDownloadManyUrl() {
        WorkflowDataBag bag = new WorkflowDataBag();
        Update update = TelegramTestFactory.buildUrlTextMessageUpdate(List.of(TEST_URL, TEST2_URL));
        bag.put(WorkflowDataKey.TELEGRAM_UPDATE, update);
        bag.put(WorkflowDataKey.BILUBOT, biluBot);
        List<GalleryDlResult> expected = List.of(GalleryDlTestFactory.buildResult(TEST_URL),
            GalleryDlTestFactory.buildResult(TEST2_URL));

        when(galleryDl.download(anyString(), any(GalleryDlOptions.class))).thenAnswer(invocation -> {
            String url = (String) invocation.getArguments()[0];
            return GalleryDlTestFactory.buildResult(url);
        });

        WorkflowAction nextStep = step.run(bag);

        verify(galleryDl).download(eq(TEST_URL), any(GalleryDlOptions.class));
        verify(galleryDl).download(eq(TEST2_URL), any(GalleryDlOptions.class));

        assertEquals(WorkflowAction.GET_NEXT_GALLERY_DL_RESULT, nextStep);

        List<GalleryDlResult> results =
            bag.getGeneric(WorkflowDataKey.GALLERY_DL_RESULTS, List.class, GalleryDlResult.class);
        assertThat(results)
            .isEqualTo(expected);
    }

    @Test
    void testGalleryDlFailure() {
        WorkflowDataBag bag = new WorkflowDataBag();
        Update update = TelegramTestFactory.buildUrlTextMessageUpdate(List.of(TEST_URL));
        bag.put(WorkflowDataKey.TELEGRAM_UPDATE, update);
        bag.put(WorkflowDataKey.BILUBOT, biluBot);
        when(galleryDl.download(any(), any())).thenThrow(new GalleryDlException("gallery-dl failure"));

        WorkflowAction nextStep = step.run(bag);

        assertEquals(WorkflowAction.NONE, nextStep);

        List<GalleryDlResult> results =
            bag.getGeneric(WorkflowDataKey.GALLERY_DL_RESULTS, List.class, GalleryDlResult.class);
        assertNull(results);
    }
}