package com.pichincha.prueba.service;

import com.pichincha.prueba.exception.SystemException;
import com.pichincha.prueba.model.Account;
import com.pichincha.prueba.model.Transactions;
import com.pichincha.prueba.model.dto.AccountDTO;
import com.pichincha.prueba.model.dto.ClientDTO;
import com.pichincha.prueba.model.dto.TransactionsDTO;
import com.pichincha.prueba.repository.IAccountRepository;
import com.pichincha.prueba.repository.ITransactionsRepository;
import com.pichincha.prueba.service.interfaces.ITransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
@Transactional
public class TransactionService implements ITransactionService {

    private final BigDecimal DAILY_QUOTE = new BigDecimal(1000);

    @Autowired
    private ITransactionsRepository transactionsRepository;

    @Autowired
    private IAccountRepository accountRepository;
    @Override
    public TransactionsDTO create(TransactionsDTO dto) throws SystemException {
        Account act = accountRepository.findById(dto.getAccountDTO().getAccountId()).get();
        BigDecimal balance = act.getInitialBalance();

        List<BigDecimal> balances  = transactionsRepository.getBalances(act.getClient().getId(), act.getId());
        if(balances != null && !balances.isEmpty()){
            balance = balances.get(0);
        }

        if("deposito".equalsIgnoreCase(dto.getType())){
            balance = balance.add(dto.getAmount());
        }else{
            java.util.Date today = Calendar.getInstance().getTime();
            Date date = new Date(today.getTime());

            BigDecimal dailyTotal = transactionsRepository.getDailyTotal(date, act.getClient().getId(), act.getId(), "retiro");
            if(dailyTotal==null){
                dailyTotal = new BigDecimal(0);
            }
            BigDecimal estimate = new BigDecimal(0);
            estimate = estimate.add(dailyTotal).add(dto.getAmount()).add(DAILY_QUOTE);

            if(estimate!=null && estimate.doubleValue() < 0){
                throw new SystemException("Cupo diario Excedido");
            }else{
                balance = balance.add(dto.getAmount());
            }
        }

        if(balance.doubleValue() < 0){
            throw new SystemException("Saldo no disponible");
        }else{
            dto.setFinalBalance(balance);
        }

        Transactions tran = dto.parse(dto);
        tran.setAccount(act);
        transactionsRepository.save(tran);

        dto.setId(tran.getId());
        return dto;
    }

    @Override
    public Iterable<TransactionsDTO> read()  {
        List<TransactionsDTO> allTrans = new ArrayList<>();
        Iterable<Transactions> trans = transactionsRepository.findAll();
        trans.forEach(tran -> {
            TransactionsDTO dto = new TransactionsDTO();
            dto.parse(tran);
            allTrans.add(dto);
        });
        return allTrans;
    }

    @Override
    public TransactionsDTO update(TransactionsDTO dto) throws SystemException {
        Account act = accountRepository.findById(dto.getAccountDTO().getAccountId()).get();
        BigDecimal balance = act.getInitialBalance();

        if("deposito".equalsIgnoreCase(dto.getType())){
            balance = balance.add(dto.getAmount());
        }else{
            java.util.Date today = Calendar.getInstance().getTime();
            Date date = new Date(today.getTime());

            BigDecimal dailyTotal = transactionsRepository.getDailyTotal(date, act.getClient().getId(), act.getId(), "retiro");
            if(dailyTotal==null){
                dailyTotal = new BigDecimal(0);
            }
            BigDecimal estimate = new BigDecimal(0);
            estimate = estimate.add(dailyTotal).add(dto.getAmount()).add(DAILY_QUOTE);

            if(estimate!=null && estimate.doubleValue() < 0){
                throw new SystemException("Cupo diario Excedido");
            }else{
                balance = balance.add(dto.getAmount());
            }
        }

        if(balance.doubleValue() < 0){
            throw new SystemException("Saldo no disponible");
        }else{
            dto.setFinalBalance(balance);
        }

        Transactions tran = dto.parse(dto);
        tran.setAccount(act);
        transactionsRepository.save(tran);
        return dto;
    }

    @Override
    public void delete(Integer id) {
        transactionsRepository.deleteById(id);
    }

    @Override
    public Iterable<TransactionsDTO> getTransactions(Date initialDate, Date finalDate, Integer clientId) {
        List<TransactionsDTO> transacts = new ArrayList<>();
        Iterable<Transactions> result = transactionsRepository.getTransactions(initialDate, finalDate, clientId);
        result.forEach(tran -> {
            TransactionsDTO dto = new TransactionsDTO();
            dto.parse(tran);
            Account act = accountRepository.findById(tran.getAccount().getId()).get();
            AccountDTO actDTO = new AccountDTO();
            actDTO.parse(act);
            ClientDTO cliDTO = new ClientDTO();
            cliDTO.parse(actDTO.getClientDTO());
            actDTO.setClientDTO(cliDTO);
            dto.setAccountDTO(actDTO);
            transacts.add(dto);
        });
        return transacts;
    }
}
