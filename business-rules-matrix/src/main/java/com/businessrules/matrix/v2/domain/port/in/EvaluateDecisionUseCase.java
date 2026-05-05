package com.businessrules.matrix.v2.domain.port.in;
import com.businessrules.matrix.v2.application.dto.DecisionRequest;
import com.businessrules.matrix.v2.application.dto.DecisionResponse;
public interface EvaluateDecisionUseCase {
    DecisionResponse evaluate(DecisionRequest request);
}
