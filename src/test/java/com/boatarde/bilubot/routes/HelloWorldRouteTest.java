package com.boatarde.bilubot.routes;

import com.boatarde.bilubot.bots.HelloBot;
import com.boatarde.bilubot.factory.TelegramTestFactory;
import com.boatarde.bilubot.flows.FlowAction;
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

@ExtendWith(MockitoExtension.class)
class HelloWorldRouteTest {
    @Mock
    private HelloBot helloBot;

    private HelloWorldRoute helloWorldRoute;

    @BeforeEach
    void setUp() {
        helloWorldRoute = new HelloWorldRoute();
    }

    @Test
    void shouldRouteToHelloWorldAction() {
        Update update = TelegramTestFactory.buildTextMessageUpdate("hello");
        Optional<FlowAction> maybeAction = helloWorldRoute.test(update, helloBot);
        assertThat(maybeAction)
            .isPresent()
            .get()
            .isEqualTo(FlowAction.HELLO_WORLD);
    }

    @Test
    void shouldRouteToHelloWorldActionCaseInsensitive() {
        Update update = TelegramTestFactory.buildTextMessageUpdate("HELlo");
        Optional<FlowAction> maybeAction = helloWorldRoute.test(update, helloBot);
        assertThat(maybeAction)
            .isPresent()
            .get()
            .isEqualTo(FlowAction.HELLO_WORLD);
    }

    @Test
    void shouldNotRouteDifferentText() {
        Update update = TelegramTestFactory.buildTextMessageUpdate("not hello");
        Optional<FlowAction> maybeAction = helloWorldRoute.test(update, helloBot);
        assertThat(maybeAction).isNotPresent();
    }

    @Test
    void shouldNotRouteDifferentBot() {
        Update update = TelegramTestFactory.buildTextMessageUpdate("hello");
        Optional<FlowAction> maybeAction = helloWorldRoute.test(update, mock(TelegramBot.class));
        assertThat(maybeAction).isNotPresent();
    }

    @Test
    void shouldNotRouteNoMessage() {
        Optional<FlowAction> maybeAction = helloWorldRoute.test(new Update(), helloBot);
        assertThat(maybeAction).isNotPresent();
    }
}