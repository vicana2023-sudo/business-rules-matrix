package com.businessrules.matrix.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class DecisionResponse {
    @JsonProperty("decision")
    String decision;
    @JsonProperty("detail")
    String detail;
    @JsonProperty("ruleApplied")
    String ruleApplied;
    @JsonProperty("customerType")
    String customerType;
    @JsonProperty("accountStatus")
    String accountStatus;
    @JsonProperty("productType")
    String productType;
}
