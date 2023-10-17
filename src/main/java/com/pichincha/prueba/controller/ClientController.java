package com.pichincha.prueba.controller;

import com.pichincha.prueba.model.dto.ClientDTO;
import com.pichincha.prueba.response.GenericResponse;
import com.pichincha.prueba.service.interfaces.IClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clientes")
public class ClientController {
    @Autowired
    private IClientService clientService;

    @GetMapping
    ResponseEntity<Iterable<ClientDTO>> getClients() throws Exception {
        return ResponseEntity.ok(clientService.read());
    }

    @PostMapping
    ResponseEntity<GenericResponse> postClient(@RequestBody ClientDTO clientDTO){
        GenericResponse response = new GenericResponse();
        try{
            clientService.create(clientDTO);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Cliente creado correctamente");

        }catch(Exception ex){
            response.setStatus(HttpStatus.EXPECTATION_FAILED.value());
            response.setMessage(ex.getMessage());
        }
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    ResponseEntity<GenericResponse> putClient(@RequestBody ClientDTO clientDTO, @PathVariable("id") Integer id){
        GenericResponse response = new GenericResponse();
        try{
            clientService.update(clientDTO);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Cliente actualizado correctamente");
        }catch(Exception ex){
            response.setStatus(HttpStatus.EXPECTATION_FAILED.value());
            response.setMessage(ex.getMessage());
        }
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    ResponseEntity deleteClient(@PathVariable("id") Integer id){
        clientService.delete(id);
        return ResponseEntity.ok(id);
    }
}
