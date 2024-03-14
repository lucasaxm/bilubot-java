package com.boatarde.bilubot.exception;

import com.boatarde.bilubot.flows.FlowAction;

public class UnknownActionException extends Exception {
    private final FlowAction action;

    public UnknownActionException(final FlowAction action) {
        super("UnknownActionException");
        this.action = action;
    }

    @Override
    public String getMessage() {
        return String.format("No steps found for action: %s", action);
    }
}
