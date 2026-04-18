package com.businessrules.matrix.domain.rule;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class RuleCoordinate {
    String customerType;
    String accountStatus;
    String productType;
}
