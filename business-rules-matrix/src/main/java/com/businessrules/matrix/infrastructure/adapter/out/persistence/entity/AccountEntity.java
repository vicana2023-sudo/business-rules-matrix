package com.businessrules.matrix.infrastructure.adapter.out.persistence.entity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "account")
public class AccountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "account_number", nullable = false, unique = true)
    private String accountNumber;
    @Column(name = "customer_id", nullable = false)
    private Long customerId;
    @Column(name = "account_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private AccountStatusEnum accountStatus;
    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal balance;
    @Column(name = "debt_ratio", nullable = false, precision = 5, scale = 4)
    private BigDecimal debtRatio;
    public enum AccountStatusEnum {
        ACTIVE, SUSPENDED, CLOSED
    }
}
