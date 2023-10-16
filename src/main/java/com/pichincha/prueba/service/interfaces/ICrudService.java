package com.pichincha.prueba.service.interfaces;

public interface ICrudService<T> {
    T create(T dto) throws Exception;

    Iterable<T> read() throws Exception;
    T update(T dto) throws Exception;
    void delete(Integer id);
}
