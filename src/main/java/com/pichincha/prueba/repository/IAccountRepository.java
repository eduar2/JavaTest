package com.pichincha.prueba.repository;

import com.pichincha.prueba.model.Account;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface IAccountRepository extends CrudRepository<Account, Integer> {

    @Query("select act "
            + "from Account act, Client c "
            + "where act.client.id = c.id "
            + "and c.id = ?1")
    Iterable<Account> findByClient(Integer clientId);
}
