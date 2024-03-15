package com.boatarde.bilubot.service;

import com.boatarde.bilubot.exception.UnknownActionException;
import com.boatarde.bilubot.flows.FlowAction;
import com.boatarde.bilubot.flows.Step;
import com.boatarde.bilubot.flows.StepManager;
import com.boatarde.bilubot.routes.Route;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.TelegramBot;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RouterServiceTest {

    @Mock
    private StepManager stepManager;

    @Mock
    private Update update;

    @Mock
    private TelegramBot bot;

    @Test
    void shouldRouteToOneAction() throws UnknownActionException {
        Route route1 = mock(Route.class);
        Route route2 = mock(Route.class);
        when(route1.test(update, bot)).thenReturn(Optional.of(FlowAction.HELLO_WORLD));
        when(route2.test(update, bot)).thenReturn(Optional.empty());

        when(stepManager.getFirstStep(FlowAction.HELLO_WORLD)).thenReturn(Optional.empty());

        RouterService routerService = new RouterService(stepManager, List.of(route1, route2));
        routerService.route(update, bot);
        verify(stepManager).getFirstStep(FlowAction.HELLO_WORLD);
    }

    @Test
    void shouldNotRoute() {
        Route route1 = mock(Route.class);
        Route route2 = mock(Route.class);
        when(route1.test(update, bot)).thenReturn(Optional.empty());
        when(route2.test(update, bot)).thenReturn(Optional.empty());

        RouterService routerService = new RouterService(stepManager, List.of(route1, route2));
        routerService.route(update, bot);
        verifyNoInteractions(stepManager);
    }

    @Test
    void shouldRouteTwoTimes() throws UnknownActionException {
        Route route1 = mock(Route.class);
        Route route2 = mock(Route.class);
        when(route1.test(update, bot)).thenReturn(Optional.of(FlowAction.HELLO_WORLD));
        when(route2.test(update, bot)).thenReturn(Optional.of(FlowAction.HELLO_WORLD));

        when(stepManager.getFirstStep(FlowAction.HELLO_WORLD)).thenReturn(Optional.empty());

        RouterService routerService = new RouterService(stepManager, List.of(route1, route2));
        routerService.route(update, bot);
        verify(stepManager, times(2)).getFirstStep(FlowAction.HELLO_WORLD);
    }

    @Test
    void shouldRouteOneTimeAndExecuteNextStep() throws UnknownActionException {
        Route route1 = mock(Route.class);
        Route route2 = mock(Route.class);
        when(route1.test(update, bot)).thenReturn(Optional.empty());
        when(route2.test(update, bot)).thenReturn(Optional.of(FlowAction.HELLO_WORLD));

        Step firstStep = mock(Step.class);
        Step nextStep = mock(Step.class);
        when(stepManager.getFirstStep(FlowAction.HELLO_WORLD)).thenReturn(Optional.of(firstStep));
        when(firstStep.run(any())).thenReturn(Optional.of(nextStep));
        when(nextStep.run(any())).thenReturn(Optional.empty());
        RouterService routerService = new RouterService(stepManager, List.of(route1, route2));
        routerService.route(update, bot);

        verify(stepManager).getFirstStep(FlowAction.HELLO_WORLD);
        verify(firstStep).run(any());
        verify(nextStep).run(any());
    }
}