package com.pichincha.prueba.repository;

import com.pichincha.prueba.model.Client;
import org.springframework.data.repository.CrudRepository;

public interface IClientRepository extends CrudRepository<Client, Integer> {
}
