package com.businessrules.matrix.domain.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Customer {
    Long id;
    String name;
    String customerType;
    Integer creditScore;
}
