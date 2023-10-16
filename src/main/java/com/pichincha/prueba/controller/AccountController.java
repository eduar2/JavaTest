package com.pichincha.prueba.controller;

import com.pichincha.prueba.model.dto.AccountDTO;
import com.pichincha.prueba.response.GenericResponse;
import com.pichincha.prueba.service.interfaces.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
public class AccountController {
    @Autowired
    private IAccountService accountService;

    @GetMapping
    ResponseEntity<Iterable<AccountDTO>> getAccountsByClient(@RequestParam(value = "clientId", required = true) Integer clientId) {
        return ResponseEntity.ok(accountService.getByClientId(clientId));
    }

    @PostMapping
    ResponseEntity<GenericResponse> postAccount(@RequestBody AccountDTO accountDTO){
        GenericResponse response = new GenericResponse();
        try{
            accountService.create(accountDTO);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Cuenta creada correctamente");
        }catch(Exception ex){
            response.setStatus(HttpStatus.EXPECTATION_FAILED.value());
            response.setMessage(ex.getMessage());
        }
        return ResponseEntity.ok(response);
    }

    @PutMapping
    ResponseEntity<GenericResponse> putAccount(@RequestBody AccountDTO accountDTO){
        GenericResponse response = new GenericResponse();
        try{
            accountService.update(accountDTO);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Cuenta actualizada correctamente");
        }catch(Exception ex){
            response.setStatus(HttpStatus.EXPECTATION_FAILED.value());
            response.setMessage(ex.getMessage());
        }
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    ResponseEntity deleteAccount(@PathVariable("id") Integer id){
        accountService.delete(id);
        return ResponseEntity.ok(id);
    }
}
