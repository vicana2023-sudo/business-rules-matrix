package com.businessrules.matrix.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "account")
@Getter
@Setter
public class AccountEntity {

    @Id
    private Long id;

    @Column(name = "account_number")
    private String accountNumber;

    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "account_status")
    private String accountStatus;

    private BigDecimal balance;

    @Column(name = "debt_ratio")
    private BigDecimal debtRatio;
}
