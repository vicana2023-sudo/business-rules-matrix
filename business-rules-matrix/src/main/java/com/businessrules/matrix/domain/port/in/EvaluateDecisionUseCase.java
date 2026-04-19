package com.businessrules.matrix.domain.port.in;
import com.businessrules.matrix.application.dto.DecisionRequest;
import com.businessrules.matrix.application.dto.DecisionResponse;
public interface EvaluateDecisionUseCase {
    DecisionResponse evaluate(DecisionRequest request);
}
