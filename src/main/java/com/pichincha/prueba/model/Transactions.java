package com.pichincha.prueba.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transactions implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Date transactionDate;
    private String type;
    private BigDecimal initialBalance;
    private BigDecimal amount;
    private BigDecimal finalBalance;

    private Boolean status;

    @ManyToOne
    @JoinColumn(name = "accountId")
    private Account account;
}
