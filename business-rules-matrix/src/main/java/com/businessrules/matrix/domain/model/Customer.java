package com.businessrules.matrix.domain.model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    private Long id;
    private String name;
    private CustomerType customerType;
    private Integer creditScore;
    public enum CustomerType {
        VIP, REGULAR, NEW
    }
}
