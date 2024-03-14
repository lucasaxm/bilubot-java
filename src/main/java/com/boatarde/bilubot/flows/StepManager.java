package com.boatarde.bilubot.flows;

import com.boatarde.bilubot.exception.UnknownActionException;
import com.boatarde.bilubot.flows.hello.steps.ReplyHelloWorldStep;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;

@Component
public class StepManager {

    private final Map<FlowAction, Step> firstStepMap;

    public StepManager(ReplyHelloWorldStep replyHelloWorldStep) {
        this.firstStepMap = new EnumMap<>(FlowAction.class);
        firstStepMap.put(FlowAction.HELLO_WORLD, replyHelloWorldStep);
    }

    public Optional<Step> getFirstStep(FlowAction action) throws UnknownActionException {

        final Step step = firstStepMap.get(action);
        if (null == step) {
            throw new UnknownActionException(action);
        }
        return Optional.of(step);
    }
}
