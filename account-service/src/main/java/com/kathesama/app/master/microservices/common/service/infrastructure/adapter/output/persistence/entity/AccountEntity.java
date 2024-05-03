package com.kathesama.app.master.microservices.common.service.infrastructure.adapter.output.persistence.entity;

import com.kathesama.app.master.microservices.common.infrastructure.adapter.output.persistence.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "accounts")
public class AccountEntity extends BaseEntity {
    @Column(name="customer_id")
    private Long customerId;

    @Column(name="account_number")
    @Id
    private Long accountNumber;

    @Column(name="account_type")
    private String accountType;

    @Column(name="branch_address")
    private String branchAddress;

}
