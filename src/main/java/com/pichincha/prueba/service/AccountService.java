package com.pichincha.prueba.service;

import com.pichincha.prueba.model.Account;
import com.pichincha.prueba.model.Client;
import com.pichincha.prueba.model.dto.AccountDTO;
import com.pichincha.prueba.model.dto.ClientDTO;
import com.pichincha.prueba.repository.IAccountRepository;
import com.pichincha.prueba.repository.IClientRepository;
import com.pichincha.prueba.service.interfaces.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class AccountService implements IAccountService {

    @Autowired
    private IAccountRepository accountRepository;

    @Autowired
    private IClientRepository clientRepository;
    @Override
    public Iterable<AccountDTO> getByClientId(Integer clientId) {
        List<AccountDTO> accounts = new ArrayList<>();
        Iterable<Account> allAccounts = accountRepository.findByClient(clientId);
        allAccounts.forEach(act -> {
            ClientDTO cliDTO = new ClientDTO();
            cliDTO.parse(act.getClient());
            AccountDTO dto = new AccountDTO();
            dto.setClientDTO(cliDTO);
            dto.parse(act);
            accounts.add(dto);
        });
        return accounts;
    }

    @Override
    public AccountDTO create(AccountDTO dto) {
        Account act = dto.parse(dto);
        Client cli = clientRepository.findById(dto.getClientDTO().getClientId()).get();
        act.setClient(cli);
        accountRepository.save(act);
        dto.setAccountId(act.getId());
        return dto;
    }

    @Override
    public Iterable<AccountDTO> read() {
        List<AccountDTO> allAccounts = new ArrayList<>();
        Iterable<Account> accounts = accountRepository.findAll();
        accounts.forEach(act -> {
            AccountDTO dto = new AccountDTO();
            dto.parse(act);
            allAccounts.add(dto);
        });
        return allAccounts;
    }

    @Override
    public AccountDTO update(AccountDTO dto){
        Account act = dto.parse(dto);
        Client cli = clientRepository.findById(dto.getClientDTO().getClientId()).get();
        act.setClient(cli);
        accountRepository.save(act);
        return dto;
    }

    @Override
    public void delete(Integer id) {
        accountRepository.deleteById(id);
    }
}
