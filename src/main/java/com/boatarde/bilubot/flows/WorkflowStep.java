package com.boatarde.bilubot.flows;

public interface WorkflowStep {
    WorkflowAction run(WorkflowDataBag bag);
}
