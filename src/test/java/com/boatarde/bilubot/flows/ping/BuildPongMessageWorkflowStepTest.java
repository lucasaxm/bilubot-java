package com.boatarde.bilubot.flows.ping;

import com.boatarde.bilubot.bots.BiluBot;
import com.boatarde.bilubot.factory.TelegramTestFactory;
import com.boatarde.bilubot.flows.WorkflowAction;
import com.boatarde.bilubot.flows.WorkflowDataBag;
import com.boatarde.bilubot.flows.WorkflowDataKey;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verifyNoInteractions;

@ExtendWith(MockitoExtension.class)
class BuildPongMessageWorkflowStepTest {
    @Mock
    private BiluBot biluBot;

    private BuildPongMessageStep step;

    @BeforeEach
    void setUp() {
        step = new BuildPongMessageStep();
    }

    @Test
    void testBuildMessageSuccessfully() {
        WorkflowDataBag workflowDataBag = new WorkflowDataBag();
        Update update = TelegramTestFactory.buildCommandTextMessageUpdate("/ping");
        workflowDataBag.put(WorkflowDataKey.TELEGRAM_UPDATE, update);
        workflowDataBag.put(WorkflowDataKey.BILUBOT, biluBot);
        WorkflowAction result = step.run(workflowDataBag);

        SendMessage sendMessage = workflowDataBag.get(WorkflowDataKey.SEND_MESSAGE, SendMessage.class);

        assertThat(sendMessage.getText()).matches("pong! \\(\\d+s\\)");
        assertEquals(update.getMessage().getChatId().toString(), sendMessage.getChatId());
        assertEquals(update.getMessage().getMessageId(), sendMessage.getReplyToMessageId());
        verifyNoInteractions(biluBot);

        assertEquals(WorkflowAction.SEND_MESSAGE, result);
    }
}