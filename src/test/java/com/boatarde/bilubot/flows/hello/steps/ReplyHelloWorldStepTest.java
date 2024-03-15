package com.boatarde.bilubot.flows.hello.steps;

import com.boatarde.bilubot.bots.HelloBot;
import com.boatarde.bilubot.factory.TelegramTestFactory;
import com.boatarde.bilubot.flows.FlowContext;
import com.boatarde.bilubot.flows.Step;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
class ReplyHelloWorldStepTest {
    @Mock
    private HelloBot helloBot;

    @Captor
    private ArgumentCaptor<SendMessage> sendMessageCaptor;

    private ReplyHelloWorldStep step;

    @BeforeEach
    void setUp() {
        step = new ReplyHelloWorldStep();
    }

    @Test
    void testReplySuccessfully() throws Exception {
        Update update = TelegramTestFactory.buildTextMessageUpdate("hello");
        FlowContext flowContext = new FlowContext(helloBot, update);

        Optional<Step> result = step.run(flowContext);

        verify(helloBot).execute(sendMessageCaptor.capture());
        SendMessage sentMessage = sendMessageCaptor.getValue();

        assertEquals("world!", sentMessage.getText());
        assertEquals(update.getMessage().getChatId().toString(), sentMessage.getChatId());
        assertEquals(update.getMessage().getMessageId(), sentMessage.getReplyToMessageId());
        verifyNoMoreInteractions(helloBot);

        assertEquals(Optional.empty(), result);
    }
}