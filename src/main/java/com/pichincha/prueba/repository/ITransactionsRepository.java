package com.pichincha.prueba.repository;

import com.pichincha.prueba.model.Transactions;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

public interface ITransactionsRepository extends CrudRepository<Transactions,Integer> {

    @Query("select tran "
            + "from Transactions tran, Account act, Client c "
            + "where tran.account.id = act.id "
            + "and act.client.id = c.id "
            + "and tran.transactionDate >= ?1 "
            + "and tran.transactionDate <= ?2 "
            + "and c.id = ?3"
    )
    Iterable<Transactions> getTransactions(Date initialDate, Date fialDate, Integer clientId);

    @Query("select sum(tran.amount) "
            + "from Transactions tran, Account act, Client c "
            + "where tran.account.id = act.id "
            + "and act.client.id = c.id "
            + "and tran.transactionDate = ?1 "
            + "and act.id = ?2 "
            + "and tran.type = ?3"
    )
    BigDecimal getDailyTotal(Date date, Integer accountId, String transactionType);

    @Query("select tran.finalBalance "
            + "from Transactions tran, Account act, Client c "
            + "where tran.account.id = act.id "
            + "and act.client.id = c.id "
            + "and c.id = ?1 "
            + "and act.id = ?2 "
            + "order by tran.id desc"
    )
    List<BigDecimal> getBalances(Integer clientId, Integer accountId);
}
