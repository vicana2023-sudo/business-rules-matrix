package com.businessrules.matrix.application.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class DecisionRequest {

    @NotNull
    @JsonProperty("customerId")
    @JsonAlias("customer_id")
    private Long customerId;

    @NotNull
    @JsonProperty("accountId")
    @JsonAlias("account_id")
    private Long accountId;

    @NotNull
    @Pattern(regexp = "SAVINGS|CREDIT|INVESTMENT", message = "ProductType must be SAVINGS, CREDIT or INVESTMENT")
    @JsonProperty("productType")
    @JsonAlias("product_type")
    private String productType;

    @NotNull
    @DecimalMin(value = "0.01", message = "Amount must be greater than 0")
    @JsonProperty("amount")
    private BigDecimal amount;
}
