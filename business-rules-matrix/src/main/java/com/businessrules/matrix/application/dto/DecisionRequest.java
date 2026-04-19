package com.businessrules.matrix.application.dto;
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
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request to evaluate a business decision")
public class DecisionRequest {
    @NotNull(message = "Customer ID is required")
    @Positive(message = "Customer ID must be positive")
    @Schema(description = "ID of the customer", example = "1")
    private Long customerId;
    @NotNull(message = "Account ID is required")
    @Positive(message = "Account ID must be positive")
    @Schema(description = "ID of the account", example = "1")
    private Long accountId;
    @NotBlank(message = "Product type is required")
    @Schema(description = "Type of product (e.g. LOAN, INVESTMENT, INSURANCE)", example = "LOAN")
    private String productType;
    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.01", message = "Amount must be greater than zero")
    @Schema(description = "Transaction amount", example = "10000.00")
    private BigDecimal amount;
}
