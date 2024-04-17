package com.boatarde.bilubot.models.gallerydl.extractors;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Twitter {
    private String cards;
    private String username;
    private String password;
    private boolean textTweets;
    private boolean quoted;
    private boolean retweets;
}