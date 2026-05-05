package com.businessrules.matrix.v1.infrastructure.adapter.out.persistence.entity;
import jakarta.persistence.*;
import lombok.*;
@Data @Builder @NoArgsConstructor @AllArgsConstructor
@Entity(name = "V1CustomerEntity")
@Table(name = "customer")
public class CustomerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(name = "customer_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private CustomerTypeEnum customerType;
    @Column(name = "credit_score", nullable = false)
    private Integer creditScore;
    public enum CustomerTypeEnum {
        VIP, REGULAR, NEW
    }
}
