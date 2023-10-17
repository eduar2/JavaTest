package com.pichincha.prueba.model.dto;

import com.pichincha.prueba.model.Account;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDTO {

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    private Integer accountId;
    private String number;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getInitialBalance() {
        return initialBalance;
    }

    public void setInitialBalance(BigDecimal initialBalance) {
        this.initialBalance = initialBalance;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public ClientDTO getClientDTO() {
        return clientDTO;
    }

    public void setClientDTO(ClientDTO clientDTO) {
        this.clientDTO = clientDTO;
    }

    private String type;
    private BigDecimal initialBalance;
    private BigDecimal currentBalance;
    private Boolean status;
    private ClientDTO clientDTO;

    public void parse(Account account) {
        this.setAccountId(account.getId());
        this.setNumber(account.getAccountNumber());
        this.setType(account.getType());
        this.setInitialBalance(account.getInitialBalance());
        this.setCurrentBalance(account.getCurrentBalance());
        this.setStatus(account.getStatus());
    }

    public Account parse(AccountDTO dto){
        Account account = new Account();
        if(dto.getAccountId() != null && dto.getAccountId() > 0){
            account.setId(dto.getAccountId());
        }
        account.setStatus(dto.getStatus());
        account.setAccountNumber(dto.getNumber());
        account.setType(dto.getType());
        account.setInitialBalance(dto.getInitialBalance());
        account.setCurrentBalance(dto.getInitialBalance());
        return account;
    }
}
