package com.businessrules.matrix.v1.domain.port.in;

import com.businessrules.matrix.v1.application.dto.DecisionRequest;
import com.businessrules.matrix.v1.application.dto.DecisionResponse;

public interface EvaluateDecisionUseCase {
    DecisionResponse evaluate(DecisionRequest request);
}

