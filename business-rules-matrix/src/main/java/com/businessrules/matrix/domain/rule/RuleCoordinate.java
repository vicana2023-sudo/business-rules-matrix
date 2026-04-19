package com.businessrules.matrix.domain.rule;
import com.businessrules.matrix.domain.model.Account.AccountStatus;
import com.businessrules.matrix.domain.model.Customer.CustomerType;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class RuleCoordinate {
    private CustomerType customerType;
    private AccountStatus accountStatus;
    private String productType;
    @Override
    public String toString() {
        return "RuleCoordinate{" +
                "customerType=" + customerType +
                ", accountStatus=" + accountStatus +
                ", productType='" + productType + '\'' +
                '}';
    }
}
