package com.pichincha.prueba.service;

import com.pichincha.prueba.exception.SystemException;
import com.pichincha.prueba.model.Account;
import com.pichincha.prueba.model.Client;
import com.pichincha.prueba.model.Transactions;
import com.pichincha.prueba.model.dto.AccountDTO;
import com.pichincha.prueba.model.dto.ReportDTO;
import com.pichincha.prueba.model.dto.TransactionsDTO;
import com.pichincha.prueba.repository.IAccountRepository;
import com.pichincha.prueba.repository.IClientRepository;
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

    @Autowired
    private IClientRepository clientRepository;

    @Override
    public TransactionsDTO create(TransactionsDTO dto) throws SystemException {
        AccountDTO actDTO = dto.getAccountDTO();
        Integer id = actDTO.getAccountId();
        Account act = accountRepository.findById(id).get();
        BigDecimal balance = act.getCurrentBalance();
        dto.setInitialBalance(balance);
        BigDecimal newBalance;

        if ("deposito".equalsIgnoreCase(dto.getType())) {
            dto.setInitialBalance(balance);
            newBalance = BigDecimal.valueOf(balance.doubleValue() + dto.getAmount().doubleValue());
            dto.setFinalBalance(newBalance);
            act.setCurrentBalance(newBalance);
        } else {
            if (balance.doubleValue() <= 0 || dto.getAmount().doubleValue() > balance.doubleValue()) {
                throw new SystemException("Saldo no disponible");
            } else {
                java.util.Date today = Calendar.getInstance().getTime();
                Date date = new Date(today.getTime());

                BigDecimal dailyTotal = transactionsRepository.getDailyTotal(date, act.getId(), "retiro");
                if (dailyTotal == null) {
                    dailyTotal = new BigDecimal(0);
                }
                BigDecimal currentDaily = new BigDecimal(0);
                currentDaily = currentDaily.add(dailyTotal).add(dto.getAmount());

                if (currentDaily.doubleValue() > DAILY_QUOTE.doubleValue()) {
                    throw new SystemException("Cupo diario Excedido");
                } else {
                    newBalance = BigDecimal.valueOf(balance.doubleValue() - dto.getAmount().doubleValue());
                    dto.setFinalBalance(newBalance);
                    act.setCurrentBalance(newBalance);
                }
            }
        }
        accountRepository.save(act);
        Transactions tran = dto.parse(dto);

        tran.setAccount(act);
        transactionsRepository.save(tran);
        dto.setId(tran.getId());
        return dto;
    }

    @Override
    public Iterable<TransactionsDTO> read() {
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

        if ("deposito".equalsIgnoreCase(dto.getType())) {
            balance = balance.add(dto.getAmount());
        } else {
            java.util.Date today = Calendar.getInstance().getTime();
            Date date = new Date(today.getTime());

            BigDecimal dailyTotal = transactionsRepository.getDailyTotal(date, act.getId(), "retiro");
            if (dailyTotal == null) {
                dailyTotal = new BigDecimal(0);
            }
            BigDecimal estimate = new BigDecimal(0);
            estimate = estimate.add(dailyTotal).add(dto.getAmount()).add(DAILY_QUOTE);

            if (estimate != null && estimate.doubleValue() < 0) {
                throw new SystemException("Cupo diario Excedido");
            } else {
                balance = balance.add(dto.getAmount());
            }
        }

        if (balance.doubleValue() < 0) {
            throw new SystemException("Saldo no disponible");
        } else {
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
    public Iterable<ReportDTO> getTransactions(Date initialDate, Date finalDate, Integer clientId) {
        List<ReportDTO> transacts = new ArrayList<>();
        Iterable<Transactions> result = transactionsRepository.getTransactions(initialDate, finalDate, clientId);
        result.forEach((Transactions tran) -> {
            ReportDTO report = new ReportDTO();
            Account act = accountRepository.findById(tran.getAccount().getId()).get();
            Client cli =  clientRepository.findById(act.getClient().getId()).get();
            report.setFecha(tran.getTransactionDate());
            report.setCliente(cli.getName());
            report.setNumeroCuenta(act.getAccountNumber());
            report.setTipo(act.getType());
            report.setSaldoInicial(tran.getInitialBalance());
            report.setEstado(tran.getStatus());
            BigDecimal factor = new BigDecimal(-1);
            BigDecimal amount = tran.getType().equals("deposito")? tran.getAmount() : tran.getAmount().multiply(factor);
            report.setMovimiento(amount);
            report.setSaldoDisponible(tran.getFinalBalance());

            transacts.add(report);
        });
        return transacts;
    }
}
