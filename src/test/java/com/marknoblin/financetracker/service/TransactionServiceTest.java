package com.marknoblin.financetracker.service;

import com.marknoblin.financetracker.entity.Transaction;
import com.marknoblin.financetracker.entity.TransactionType;
import com.marknoblin.financetracker.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;

@SpringBootTest
@AutoConfigureMockMvc
public class TransactionServiceTest {
    @MockitoBean
    private TransactionRepository repository;

    @Autowired
    private TransactionService service;

    @Test
    void contextLoads() throws Exception {
        assertThat(service).isNotNull();
    }

    @Test
    void shouldGetAllTransactions() throws Exception {
        Transaction t1 = new Transaction();
        t1.setId(1L);
        t1.setDescription("Bought Groceries");
        t1.setAmount(BigDecimal.valueOf(150.00));
        t1.setType(TransactionType.EXPENSE);
        t1.setCategory("Food");
        t1.setDate(LocalDate.now());

        Transaction t2 = new Transaction();
        t2.setId(2L);
        t2.setDescription("Paycheck");
        t2.setAmount(BigDecimal.valueOf(500.00));
        t2.setType(TransactionType.INCOME);
        t2.setCategory("Paycheck");
        t2.setDate(LocalDate.now());

        List<Transaction> expectedResult = new ArrayList<>();
        expectedResult.add(t1);
        expectedResult.add(t2);

        Mockito.when(repository.findAll()).thenReturn(Arrays.asList(t1, t2));

        List<Transaction> result = service.getAllTransactions();

        assertNotNull(result);
        assertIterableEquals(expectedResult, result);

        Mockito.verify(repository, times(1)).findAll();
    }

    @Test
    void shouldAddTransaction_dateIsNull() throws Exception {
        Transaction newTransaction = new Transaction();
        newTransaction.setDescription("Bought Groceries");
        newTransaction.setAmount(BigDecimal.valueOf(150.00));
        newTransaction.setType(TransactionType.EXPENSE);
        newTransaction.setCategory("Food");

        Transaction expectedTransaction = new Transaction();
        expectedTransaction.setId(1L);
        expectedTransaction.setDescription("Bought Groceries");
        expectedTransaction.setAmount(BigDecimal.valueOf(150.00));
        expectedTransaction.setType(TransactionType.EXPENSE);
        expectedTransaction.setCategory("Food");
        expectedTransaction.setDate(LocalDate.now());

        Mockito.when(repository.save(Mockito.any(Transaction.class))).thenReturn(expectedTransaction);

        Transaction result = service.addTransaction(newTransaction);

        assertNotNull(result);
        assertEquals(result, expectedTransaction);

        Mockito.verify(repository, times(1)).save(newTransaction);
    }

    @Test
    void shouldAddTransaction_dateIsNotNull() throws Exception {
        Transaction newTransaction = new Transaction();
        newTransaction.setDescription("Bought Groceries");
        newTransaction.setAmount(BigDecimal.valueOf(150.00));
        newTransaction.setType(TransactionType.EXPENSE);
        newTransaction.setCategory("Food");
        newTransaction.setDate(LocalDate.now());

        Transaction expectedTransaction = new Transaction();
        expectedTransaction.setId(1L);
        expectedTransaction.setDescription("Bought Groceries");
        expectedTransaction.setAmount(BigDecimal.valueOf(150.00));
        expectedTransaction.setType(TransactionType.EXPENSE);
        expectedTransaction.setCategory("Food");
        expectedTransaction.setDate(LocalDate.now());

        Mockito.when(repository.save(Mockito.any(Transaction.class))).thenReturn(expectedTransaction);

        Transaction result = service.addTransaction(newTransaction);

        assertNotNull(result);
        assertEquals(result, expectedTransaction);

        Mockito.verify(repository, times(1)).save(newTransaction);
    }

    @Test
    void shouldDeleteTransactionById () throws Exception {
        Long transactionId = 1L;

        Mockito.doNothing().when(repository).deleteById(transactionId);

        service.deleteTransaction(transactionId);

        Mockito.verify(repository, times(1)).deleteById(transactionId);
    }

    @Test
    void shouldGetNetBalance() throws Exception {
        Transaction t1 = new Transaction();
        t1.setId(1L);
        t1.setDescription("Bought Groceries");
        t1.setAmount(BigDecimal.valueOf(150.00));
        t1.setType(TransactionType.EXPENSE);
        t1.setCategory("Food");
        t1.setDate(LocalDate.now());

        Transaction t2 = new Transaction();
        t2.setId(2L);
        t2.setDescription("Paycheck");
        t2.setAmount(BigDecimal.valueOf(500.00));
        t2.setType(TransactionType.INCOME);
        t2.setCategory("Paycheck");
        t2.setDate(LocalDate.now());

        BigDecimal expectedBalance = BigDecimal.valueOf(350.00);

        Mockito.when(repository.findAll()).thenReturn(Arrays.asList(t1, t2));

        BigDecimal balance = service.getNetBalance();

        assertNotNull(balance);
        assertEquals(balance, expectedBalance);

        Mockito.verify(repository, times(1)).findAll();
    }
}
