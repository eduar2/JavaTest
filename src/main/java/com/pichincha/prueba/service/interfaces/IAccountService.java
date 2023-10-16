package com.pichincha.prueba.service.interfaces;

import com.pichincha.prueba.model.dto.AccountDTO;

public interface IAccountService extends ICrudService<AccountDTO> {
    Iterable<AccountDTO> getByClientId(Integer clientId);
}
