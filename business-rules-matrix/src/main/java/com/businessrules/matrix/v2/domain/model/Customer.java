package com.businessrules.matrix.v2.domain.model;
import lombok.*;
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class Customer {
    private Long id;
    private String name;
    private CustomerType customerType;
    private Integer creditScore;
    public enum CustomerType { VIP, REGULAR, NEW }
}
