package com.pichincha.prueba.service.interfaces;

import com.pichincha.prueba.model.dto.TransactionsDTO;

import java.sql.Date;

public interface ITransactionService extends ICrudService<TransactionsDTO> {
    Iterable<TransactionsDTO> getTransactions(Date initialDate, Date finalDate, Integer clientId);
}
