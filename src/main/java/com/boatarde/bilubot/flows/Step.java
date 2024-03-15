package com.boatarde.bilubot.flows;

import java.util.Optional;

public interface Step {
    Optional<Step> run(FlowContext context);
}
