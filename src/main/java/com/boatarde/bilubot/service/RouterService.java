package com.boatarde.bilubot.service;

import com.boatarde.bilubot.exception.UnknownActionException;
import com.boatarde.bilubot.flows.FlowAction;
import com.boatarde.bilubot.flows.FlowContext;
import com.boatarde.bilubot.flows.Step;
import com.boatarde.bilubot.flows.StepManager;
import com.boatarde.bilubot.routes.Route;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.TelegramBot;

import java.util.List;
import java.util.Optional;

@Service
public class RouterService {
    private final StepManager stepManager;
    private final List<Route> routes;

    public RouterService(StepManager stepManager, List<Route> routes) {
        this.stepManager = stepManager;
        this.routes = routes;
    }

    public void route(Update update, TelegramBot bot) {
        routes.forEach(route -> route.test(update, bot)
            .ifPresent(action -> startFlow(update, bot, action)));
    }

    private void startFlow(Update update, TelegramBot bot, FlowAction action) {
        try {
            Optional<Step> nextStep = stepManager.getFirstStep(action);
            FlowContext flowContext = new FlowContext(bot, update);
            while (nextStep.isPresent()) {
                nextStep = nextStep.get().run(flowContext);
            }
        } catch (UnknownActionException e) {
            e.printStackTrace();
        }
    }


}
