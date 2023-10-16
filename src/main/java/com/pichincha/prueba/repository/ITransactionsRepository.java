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
            + "where tran.account.accountId = act.AccountId "
            + "and act.client.id = c.id "
            + "and tran.date >= ?1 "
            + "and tran.date <= ?2 "
            + "and c.id = ?3"
    )
    Iterable<Transactions> getTransactions(Date initialDate, Date fialDate, Integer clientId);

    @Query("select sum(tran.valor) "
            + "from Transactions tran, Account act, Client c "
            + "where tran.account.accountId = act.accountId "
            + "and act.client.id = c.id "
            + "and tran.date = ?1 "
            + "and c.id = ?2 "
            + "and act.accountId = ?3 "
            + "and tran.type = ?4"
    )
    BigDecimal getDailyTotal(Date date, Integer clientId, Integer accountId, String transactionType);

    @Query("select tran.balance "
            + "from Transactions tran, Account act, Client c "
            + "where tran.account.accountId = act.accountId "
            + "and act.client.id = c.id "
            + "and c.id = ?1 "
            + "and act.accountId = ?2 "
            + "order by tran.transactionId desc"
    )
    List<BigDecimal> getBalances(Integer clientId, Integer accountId);
}
