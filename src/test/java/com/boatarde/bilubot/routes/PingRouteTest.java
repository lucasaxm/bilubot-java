package com.boatarde.bilubot.routes;

import com.boatarde.bilubot.bots.BiluBot;
import com.boatarde.bilubot.factory.TelegramTestFactory;
import com.boatarde.bilubot.flows.WorkflowAction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.TelegramBot;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PingRouteTest {
    @Mock
    private BiluBot biluBot;

    private PingRoute pingRoute;

    @BeforeEach
    void setUp() {
        pingRoute = new PingRoute();
    }

    @Test
    void shouldRouteCommandToBuildPongMessageStep() {
        Update update = TelegramTestFactory.buildCommandTextMessageUpdate("/ping");
        Optional<WorkflowAction> maybeAction = pingRoute.test(update, biluBot);
        assertThat(maybeAction)
            .isPresent()
            .get()
            .isEqualTo(WorkflowAction.BUILD_PONG_MESSAGE);
    }

    @Test
    void shouldRouteCommandWithBotUsernameToBuildPongMessageStep() {
        when(biluBot.getBotUsername()).thenReturn("testuserbot");
        Update update = TelegramTestFactory.buildCommandTextMessageUpdate("/ping@testuserbot");
        Optional<WorkflowAction> maybeAction = pingRoute.test(update, biluBot);
        assertThat(maybeAction)
            .isPresent()
            .get()
            .isEqualTo(WorkflowAction.BUILD_PONG_MESSAGE);
    }

    @Test
    void shouldNotRouteDifferentBot() {
        Update update = TelegramTestFactory.buildTextMessageUpdate("hello");
        Optional<WorkflowAction> maybeAction = pingRoute.test(update, mock(TelegramBot.class));
        assertThat(maybeAction).isNotPresent();
    }

    @Test
    void shouldNotRouteNoMessage() {
        Optional<WorkflowAction> maybeAction = pingRoute.test(new Update(), biluBot);
        assertThat(maybeAction).isNotPresent();
    }
}