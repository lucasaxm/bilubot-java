package com.boatarde.bilubot.flows;

import com.github.lucasaxm.gallerydl.options.GalleryDlResult;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.TelegramBot;

import java.util.List;

@AllArgsConstructor
@Getter
public class FlowContext {
    private TelegramBot bot;
    private Update update;
    @Setter
    private List<GalleryDlResult> galleryDlResults;

    public FlowContext(TelegramBot bot, Update update) {
        this.bot = bot;
        this.update = update;
    }

}
