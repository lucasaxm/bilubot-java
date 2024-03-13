package com.boatarde.bilubot.abilities;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.objects.Locality;
import org.telegram.abilitybots.api.objects.Privacy;
import org.telegram.abilitybots.api.sender.MessageSender;
import org.telegram.abilitybots.api.sender.SilentSender;

@Component
public
class HelloBot extends AbilityBot {

    private final Integer creatorId;

    public HelloBot(@Value("${telegram.bots.hellobot.token}") String token,
                    @Value("${telegram.bots.hellobot.username}") String username,
                    @Value("${telegram.creator.id}") Integer creatorId) {
        super(token, username);
        this.creatorId = creatorId;
    }

    public Ability sayHelloWorld() {
        return Ability
            .builder()
            .name("hello")
            .info("Replies with 'world!'")
            .locality(Locality.ALL)
            .privacy(Privacy.PUBLIC)
            .action(ctx -> silent.send("world!", ctx.chatId()))
            .build();
    }

    @Override
    public long creatorId() {
        return creatorId;
    }

    @Override
    public MessageSender sender() {
        return super.sender();
    }

    @Override
    public SilentSender silent() {
        return super.silent();
    }
}
