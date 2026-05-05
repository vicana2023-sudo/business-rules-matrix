package com.businessrules.matrix.v1.application.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
@Data @Builder @NoArgsConstructor @AllArgsConstructor
@Schema(description = "Request to evaluate a business decision (v1 - Decision Matrix)")
public class DecisionRequest {
    @NotNull @Positive
    @Schema(description = "ID of the customer", example = "1")
    private Long customerId;
    @NotNull @Positive
    @Schema(description = "ID of the account", example = "1")
    private Long accountId;
    @NotBlank
    @Schema(description = "Type of product (LOAN, INVESTMENT, INSURANCE)", example = "LOAN")
    private String productType;
    @NotNull @DecimalMin("0.01")
    @Schema(description = "Transaction amount", example = "10000.00")
    private BigDecimal amount;
}
