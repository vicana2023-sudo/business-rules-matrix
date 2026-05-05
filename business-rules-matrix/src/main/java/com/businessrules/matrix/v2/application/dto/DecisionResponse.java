package com.businessrules.matrix.v2.application.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.math.BigDecimal;
@Data @Builder @NoArgsConstructor @AllArgsConstructor
@Schema(description = "Response from business decision evaluation (v2 - Drools Decision Tree)")
public class DecisionResponse {
    @Schema(description = "Decision result: APPROVED or REJECTED", example = "APPROVED")
    private String decision;
    @Schema(description = "Detailed explanation of the decision")
    private String detail;
    @Schema(description = "Name of the rule that was applied", example = "TREE_LOAN_VIP_CREDIT_APPROVAL")
    private String ruleApplied;
    @Schema(description = "Customer type", example = "VIP")
    private String customerType;
    @Schema(description = "Account status", example = "ACTIVE")
    private String accountStatus;
    @Schema(description = "Product type", example = "LOAN")
    private String productType;
    @Schema(description = "Calculated value from the applied rule")
    private BigDecimal calculatedValue;
}
