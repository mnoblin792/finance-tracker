package com.marknoblin.financetracker.controller;

import static org.assertj.core.api.Assertions.assertThat;

import com.marknoblin.financetracker.entity.Transaction;
import com.marknoblin.financetracker.entity.TransactionType;
import com.marknoblin.financetracker.service.TransactionService;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TransactionController controller;

    @MockitoBean
    private TransactionService transactionService;

    @Test
    void contextLoads() throws Exception {
        assertThat(controller).isNotNull();
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

        Mockito.when(transactionService.getAllTransactions()).thenReturn(Arrays.asList(t1, t2));

        mockMvc.perform(get("/api/transactions")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].description", is("Bought Groceries")))
                .andExpect(jsonPath("$[0].amount", is(150.0)))
                .andExpect(jsonPath("$[0].type", is("EXPENSE")))
                .andExpect(jsonPath("$[0].category", is("Food")))
                .andExpect(jsonPath("$[0].date", is(LocalDate.now().toString())))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].description", is("Paycheck")))
                .andExpect(jsonPath("$[1].amount", is(500.0)))
                .andExpect(jsonPath("$[1].type", is("INCOME")))
                .andExpect(jsonPath("$[1].category", is("Paycheck")))
                .andExpect(jsonPath("$[1].date", is(LocalDate.now().toString())));
    }

    @Test
    void shouldCreateTransaction() throws Exception {
        Transaction inputTransaction = new Transaction();
        inputTransaction.setDescription("Bought Laptop");
        inputTransaction.setAmount(BigDecimal.valueOf(500.0));
        inputTransaction.setType(TransactionType.EXPENSE);
        inputTransaction.setCategory("Electronics");
        inputTransaction.setDate(LocalDate.now());

        Transaction expectedTransaction = new Transaction();
        expectedTransaction.setId(3L);
        expectedTransaction.setDescription("Bought Laptop");
        expectedTransaction.setAmount(BigDecimal.valueOf(500.0));
        expectedTransaction.setType(TransactionType.EXPENSE);
        expectedTransaction.setCategory("Electronics");
        expectedTransaction.setDate(LocalDate.now());

        Mockito.when(transactionService.addTransaction(Mockito.any(Transaction.class))).thenReturn(expectedTransaction);

        mockMvc.perform(post("/api/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(objectMapper.writeValueAsString(inputTransaction)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(3)))
                .andExpect(jsonPath("$.description", is("Bought Laptop")))
                .andExpect(jsonPath("$.amount", is(500.0)))
                .andExpect(jsonPath("$.type", is("EXPENSE")))
                .andExpect(jsonPath("$.category", is("Electronics")))
                .andExpect(jsonPath("$.date", is(LocalDate.now().toString())));

    }

    @Test
    void shouldGetBalance() throws Exception {
        BigDecimal expectedBalance = BigDecimal.valueOf(200.00);

        Mockito.when(transactionService.getNetBalance()).thenReturn(expectedBalance);

        mockMvc.perform(get("/api/transactions/balance")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(200.0)));
    }

    @Test
    void shouldDeleteTransactionById() throws Exception {
        Long transactionId = 1L;

        Mockito.doNothing().when(transactionService).deleteTransaction(transactionId);

        mockMvc.perform(delete("/api/transactions/{id}", transactionId))
                .andExpect(status().isNoContent());

        Mockito.verify(transactionService, Mockito.times(1)).deleteTransaction(transactionId);
    }
}
