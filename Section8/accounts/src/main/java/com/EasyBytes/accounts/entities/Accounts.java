package com.EasyBytes.accounts.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter@Setter@ToString@NoArgsConstructor@AllArgsConstructor
@Entity
public class Accounts extends BaseEntities {
    @Column(name="customer_idf")
    private long customerIdf;

    @Column(name="account_number")
    @Id
    private Long accountNumber;

    @Column(name="account_type")
    private String accountType;

    @Column(name="branch_address")
    private String branchAddress;

}