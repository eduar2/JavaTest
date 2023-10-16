package com.pichincha.prueba.service;

import java.util.List;

public interface ICrudService<T> {
    T create(T dto) throws Exception;

    List<T> read() throws Exception;
    T update(T dto) throws Exception;
    void delete(Integer id);
}
