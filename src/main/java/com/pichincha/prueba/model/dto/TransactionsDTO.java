package com.pichincha.prueba.model.dto;

import com.pichincha.prueba.model.Transactions;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionsDTO {

    private Integer id;
    private Date transactionDate;
    private String type;
    private BigDecimal initialBalance;
    private BigDecimal amount;
    private BigDecimal finalBalance;

    private Boolean status;

    public void parse(Transactions transact){
        this.id = transact.getId();
        this.transactionDate = transact.getTransactionDate();
        this.type = transact.getType();
        this.amount = transact.getAmount();
        this.finalBalance = transact.getFinalBalance();
    }

    public Transactions parse(TransactionsDTO dto){
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date date = new java.util.Date();

        try{
            date = format.parse(String.valueOf(dto.getTransactionDate()));
        }catch(Exception ex){
            ex.printStackTrace();
        }

        Date f = new Date(date.getTime());
        Transactions transact = new Transactions();
        if(dto.getId() != null && dto.getId() > 0){
            transact.setId(dto.getId());
        }
        transact.setTransactionDate(f);
        transact.setType(dto.getType());
        transact.setAmount(dto.getAmount());
        transact.setFinalBalance(dto.getFinalBalance());
        return transact;
    }
}
