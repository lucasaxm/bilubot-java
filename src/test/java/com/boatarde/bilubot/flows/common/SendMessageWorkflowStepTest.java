package com.boatarde.bilubot.flows.common;

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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SendMessageWorkflowStepTest {
    @Mock
    private BiluBot biluBot;

    @Mock
    private SendMessage sendMessage;

    private SendMessageStep step;

    @BeforeEach
    void setUp() {
        step = new SendMessageStep();
    }

    @Test
    void testSentSuccessfully() throws Exception {
        WorkflowDataBag workflowDataBag = new WorkflowDataBag();
        workflowDataBag.put(WorkflowDataKey.SEND_MESSAGE, sendMessage);
        workflowDataBag.put(WorkflowDataKey.BILUBOT, biluBot);
        when(biluBot.execute(sendMessage)).thenReturn(TelegramTestFactory.buildTextMessage("anything"));

        WorkflowAction nextStep = step.run(workflowDataBag);

        verify(biluBot).execute(sendMessage);

        assertEquals(WorkflowAction.NONE, nextStep);
    }
}