package com.businessrules.matrix.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "customer")
@Getter
@Setter
public class CustomerEntity {

    @Id
    private Long id;

    private String name;

    @Column(name = "customer_type")
    private String customerType;

    @Column(name = "credit_score")
    private Integer creditScore;
}
