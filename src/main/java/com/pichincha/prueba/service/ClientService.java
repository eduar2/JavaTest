package com.pichincha.prueba.service;

import com.pichincha.prueba.model.Client;
import com.pichincha.prueba.model.dto.ClientDTO;
import com.pichincha.prueba.repository.IClientRepository;
import com.pichincha.prueba.service.interfaces.IClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class ClientService implements IClientService {

    @Autowired
    private IClientRepository repository;
    @Override
    public ClientDTO create(ClientDTO dto){
        Client cli = dto.parse(dto);
        repository.save(cli);
        dto.setClientId(cli.getId());
        return dto;
    }

    @Override
    public Iterable<ClientDTO> read()  {
        List<ClientDTO> allClients = new ArrayList<>();
        Iterable<Client> clients = repository.findAll();
        clients.forEach(cli -> {
            ClientDTO dto = new ClientDTO();
            dto.parse(cli);
            allClients.add(dto);
        });
        return allClients;
    }

    @Override
    public ClientDTO update(ClientDTO dto) {
        repository.save(repository.save(dto.parse(dto)));
        return dto;
    }

    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }
}
