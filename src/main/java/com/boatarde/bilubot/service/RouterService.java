package com.boatarde.bilubot.service;

import com.boatarde.bilubot.flows.WorkflowAction;
import com.boatarde.bilubot.flows.WorkflowDataBag;
import com.boatarde.bilubot.flows.WorkflowDataKey;
import com.boatarde.bilubot.flows.WorkflowManager;
import com.boatarde.bilubot.flows.WorkflowStep;
import com.boatarde.bilubot.routes.Route;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.TelegramBot;

import java.util.List;
import java.util.Optional;

@Service
public class RouterService {
    private final WorkflowManager workflowManager;
    private final List<Route> routes;

    public RouterService(WorkflowManager workflowManager, List<Route> routes) {
        this.workflowManager = workflowManager;
        this.routes = routes;
    }

    public void route(Update update, TelegramBot bot) {
        routes.forEach(route -> route.test(update, bot)
            .ifPresent(firstStep -> startFlow(update, bot, firstStep)));
    }

    private void startFlow(Update update, TelegramBot bot, WorkflowAction firstStep) {
        WorkflowAction workflowAction = firstStep;
        Optional<WorkflowStep> nextStep;

        WorkflowDataBag workflowDataBag = new WorkflowDataBag();
        workflowDataBag.put(WorkflowDataKey.BILUBOT, bot);
        workflowDataBag.put(WorkflowDataKey.TELEGRAM_UPDATE, update);

        while ((nextStep = workflowManager.getStepByEnum(workflowAction)).isPresent()) {
            workflowAction = nextStep.get().run(workflowDataBag);
        }
    }
}
