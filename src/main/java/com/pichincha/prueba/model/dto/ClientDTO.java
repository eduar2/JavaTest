package com.pichincha.prueba.model.dto;

import com.pichincha.prueba.model.Client;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class ClientDTO extends PersonDTO {
    private Integer clientId;
    private String password;
    private Boolean status;

    public void parse(Client cli) {

        this.setPassword(cli.getPassword());
        this.setStatus(cli.getStatus());
        this.setName(cli.getName());
        this.setGender(cli.getGender());
        this.setAddress(cli.getAddress());
        this.setAge(cli.getAge());
        this.setIdentification(cli.getIdentification());
        this.setPhone(cli.getPhone());
        this.setClientId(cli.getId());
    }

    public Client parse(ClientDTO dto) {
        Client client = new Client();
        if(dto.getClientId() != null && dto.getClientId() > 0){
            client.setId(dto.getClientId());
        }
        client.setPassword(dto.getPassword());
        client.setStatus(dto.getStatus());
        client.setName(dto.getName());
        client.setGender(dto.getGender());
        client.setAddress(dto.getAddress());
        client.setAge(dto.getAge());
        client.setIdentification(dto.getIdentification());
        client.setPhone(dto.getPhone());
        return client;
    }
}
