package com.pichincha.prueba.service.interfaces;

import com.pichincha.prueba.model.dto.ReportDTO;
import com.pichincha.prueba.model.dto.TransactionsDTO;

import java.sql.Date;

public interface ITransactionService extends ICrudService<TransactionsDTO> {
    Iterable<ReportDTO> getTransactions(Date initialDate, Date finalDate, Integer clientId);
}
