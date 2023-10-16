package com.pichincha.prueba.controller;

import com.pichincha.prueba.model.dto.TransactionsDTO;
import com.pichincha.prueba.response.GenericResponse;
import com.pichincha.prueba.service.interfaces.ITransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

@RestController
@RequestMapping("/transactions")
public class TransactionsController {
    @Autowired
    private ITransactionService transactionService;

    @GetMapping("/reportes")
    ResponseEntity<Iterable<TransactionsDTO>> getTransactions(@RequestParam(value = "initialDate", required = true) String initialDate,
                                                              @RequestParam(value = "finalDate", required = true) String finalDate,
                                                              @RequestParam(value = "clientId", required = true) Integer clientId) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = new Date(0);
        Date lastDate = new Date(0);
        try {
            java.util.Date sd = format.parse(initialDate);
            java.util.Date ld = format.parse(finalDate);
            startDate = new Date(sd.getTime());
            lastDate = new Date(ld.getTime());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return ResponseEntity.ok(transactionService.getTransactions(startDate, lastDate, clientId));
    }

    @PostMapping
    ResponseEntity<GenericResponse> postTransaction(@RequestBody TransactionsDTO transactionDTO) {
        GenericResponse response = new GenericResponse();
        BigDecimal amount;

        // Se valida el signo del amount
        if ("deposito".equalsIgnoreCase(transactionDTO.getType())) {
            if (transactionDTO.getAmount().doubleValue() < 0) {
                amount = transactionDTO.getAmount();
                amount = amount.multiply(new BigDecimal(-1));
                transactionDTO.setAmount(amount);
            }
        } else {
            if (transactionDTO.getAmount().doubleValue() > 0) {
                amount = transactionDTO.getAmount();
                amount = amount.multiply(new BigDecimal(-1));
                transactionDTO.setAmount(amount);
            }
        }

        try {
            transactionService.create(transactionDTO);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Transaccion creada correctamente");
        } catch (Exception ex) {
            response.setStatus(HttpStatus.EXPECTATION_FAILED.value());
            response.setMessage(ex.getMessage());
        }
        return ResponseEntity.ok(response);
    }

    @PutMapping
    ResponseEntity<GenericResponse> putTransaction(@RequestBody TransactionsDTO transactionDTO) {
        GenericResponse response = new GenericResponse();
        BigDecimal amount;

        // Se valida el signo del amount
        if ("deposito".equalsIgnoreCase(transactionDTO.getType())) {
            if (transactionDTO.getAmount().doubleValue() < 0) {
                amount = transactionDTO.getAmount();
                amount = amount.multiply(new BigDecimal(-1));
                transactionDTO.setAmount(amount);
            }
        } else {
            if (transactionDTO.getAmount().doubleValue() > 0) {
                amount = transactionDTO.getAmount();
                amount = amount.multiply(new BigDecimal(-1));
                transactionDTO.setAmount(amount);
            }
        }

        try {
            transactionService.update(transactionDTO);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Transaccion actualizada correctamente");
        } catch (Exception ex) {
            response.setStatus(HttpStatus.EXPECTATION_FAILED.value());
            response.setMessage(ex.getMessage());
        }
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    ResponseEntity deleteTransaction(@PathVariable("id") Integer id) {
        transactionService.delete(id);
        return ResponseEntity.ok(id);
    }
}
