package com.example.demo.test;

import com.example.demo.WalletApplication;
import com.example.demo.model.Description;
import com.example.demo.model.Invoice;
import com.example.demo.requestModel.InvoiceRequest;
import com.example.demo.requestModel.InvoiceRequestIban;
import com.example.demo.service.InvoiceService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = WalletApplication.class)
@AutoConfigureMockMvc
public class InvoiceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InvoiceService invoiceService;

    @Test
    public void testDepositByAccountNumber() throws Exception {
        // Test data
        InvoiceRequest invoiceRequest = new InvoiceRequest();
        invoiceRequest.setAccount_number("1234567890");
        invoiceRequest.setAmount(100000);

        Invoice invoice = new Invoice();
        invoice.setAmount(100000);
        invoice.setFee(10);
		invoice.setDescription(Description.Diposit);
		invoice.setInvoiceDate(LocalDateTime.now());
		
        // Mock behavior of invoiceService
        Mockito.when(invoiceService.deposit(invoiceRequest.getAccount_number(), invoiceRequest.getAmount())).thenReturn(invoice);

        // Convert invoiceRequest to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(invoiceRequest);

        // Send POST request
        mockMvc.perform(post("/invoice/deposit_by_accountNumber")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.amount").value(100000))
                .andExpect(MockMvcResultMatchers.jsonPath("$.account_number").value("1234567890"));
    }

    @Test
    public void testWithdrawByAccountNumber() throws Exception {
        // Test data
        InvoiceRequest invoiceRequest = new InvoiceRequest();
        invoiceRequest.setAccount_number("1234567890");
        invoiceRequest.setAmount(10000);

        Invoice invoice = new Invoice();
        invoice.setAmount(10000);
        invoice.setFee(10);
		invoice.setDescription(Description.Withdraw);
		invoice.setInvoiceDate(LocalDateTime.now());

        // Mock behavior of invoiceService
        Mockito.when(invoiceService.withdraw(invoiceRequest.getAccount_number(), invoiceRequest.getAmount())).thenReturn(invoice);

        // Convert invoiceRequest to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(invoiceRequest);

        // Send POST request
        mockMvc.perform(post("/invoice/withdraw_by_accountNumber")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.amount").value(10000))
                .andExpect(MockMvcResultMatchers.jsonPath("$.account_number").value("1234567890"));
    }

    @Test
    public void testInvoiceListByAccountNumber() throws Exception {
        // Test data
        InvoiceRequest invoiceRequest = new InvoiceRequest();
        invoiceRequest.setAccount_number("1234567890");

        List<Invoice> invoices = new ArrayList<>();
        
        Invoice invoice1 = new Invoice();
        invoice1.setAmount(100000);
        invoice1.setFee(10);
		invoice1.setDescription(Description.Diposit);
		invoice1.setInvoiceDate(LocalDateTime.now());

        Invoice invoice2 = new Invoice();
        invoice2.setAmount(10000);
        invoice2.setFee(10);
		invoice2.setDescription(Description.Withdraw);
		invoice2.setInvoiceDate(LocalDateTime.now());
		
        Invoice invoice3 = new Invoice();
        invoice3.setAmount(100000);
        invoice3.setFee(100);
		invoice3.setDescription(Description.Diposit);
		invoice3.setInvoiceDate(LocalDateTime.now());

        Invoice invoice4 = new Invoice();
        invoice4.setAmount(10000);
        invoice4.setFee(100);
		invoice4.setDescription(Description.Withdraw);
		invoice4.setInvoiceDate(LocalDateTime.now());

        invoices.add(invoice1);
        invoices.add(invoice2);
        invoices.add(invoice3);
        invoices.add(invoice4);

        // Mock behavior of invoiceService
        Mockito.when(invoiceService.invoiceList(invoiceRequest.getAccount_number())).thenReturn(invoices);

        // Convert invoiceRequest to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(invoiceRequest);

        // Send POST request
        mockMvc.perform(post("/invoice/invoice_list_by_accountNumber")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].amount").value(100000))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].amount").value(10000));
    }

    @Test
    public void testDepositByIban() throws Exception {
        // Test data
        InvoiceRequestIban invoiceRequestIban = new InvoiceRequestIban();
        invoiceRequestIban.setIban("IR12345678901234567890");
        invoiceRequestIban.setAmount(100000);

        Invoice invoice = new Invoice();
        invoice.setAmount(100000);
        invoice.setFee(100);
		invoice.setDescription(Description.Diposit);
		invoice.setInvoiceDate(LocalDateTime.now());

        // Mock behavior of invoiceService
        Mockito.when(invoiceService.depositIban(invoiceRequestIban.getIban(), invoiceRequestIban.getAmount())).thenReturn(invoice);

        // Convert invoiceRequestIban to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(invoiceRequestIban);

        // Send POST request
        mockMvc.perform(post("/invoice/deposit_by_iban")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.amount").value(100000))
                .andExpect(MockMvcResultMatchers.jsonPath("$.iban").value("IR12345678901234567890"));
    }

    @Test
    public void testWithdrawByIban() throws Exception {
        // Test data
        InvoiceRequestIban invoiceRequestIban = new InvoiceRequestIban();
        invoiceRequestIban.setIban("IR12345678901234567890");
        invoiceRequestIban.setAmount(10000);

        Invoice invoice = new Invoice();
        invoice.setAmount(10000);
        invoice.setFee(100);
		invoice.setDescription(Description.Withdraw);
		invoice.setInvoiceDate(LocalDateTime.now());

        // Mock behavior of invoiceService
        Mockito.when(invoiceService.withdrawIban(invoiceRequestIban.getIban(), invoiceRequestIban.getAmount())).thenReturn(invoice);

        // Convert invoiceRequestIban to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(invoiceRequestIban);

        // Send POST request
        mockMvc.perform(post("/invoice/withdraw_by_iban")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.amount").value(10000))
                .andExpect(MockMvcResultMatchers.jsonPath("$.iban").value("IR12345678901234567890"));
    }

    @Test
    public void testInvoiceListByIban() throws Exception {
        // Test data
        InvoiceRequestIban invoiceRequestIban = new InvoiceRequestIban();
        invoiceRequestIban.setIban("IR12345678901234567890");

        List<Invoice> invoices = new ArrayList<>();
        
        Invoice invoice1 = new Invoice();
        invoice1.setAmount(100000);
        invoice1.setFee(10);
		invoice1.setDescription(Description.Diposit);
		invoice1.setInvoiceDate(LocalDateTime.now());

        Invoice invoice2 = new Invoice();
        invoice2.setAmount(10000);
        invoice2.setFee(10);
		invoice2.setDescription(Description.Withdraw);
		invoice2.setInvoiceDate(LocalDateTime.now());
		
        Invoice invoice3 = new Invoice();
        invoice3.setAmount(100000);
        invoice3.setFee(100);
		invoice3.setDescription(Description.Diposit);
		invoice3.setInvoiceDate(LocalDateTime.now());

        Invoice invoice4 = new Invoice();
        invoice4.setAmount(10000);
        invoice4.setFee(100);
		invoice4.setDescription(Description.Withdraw);
		invoice4.setInvoiceDate(LocalDateTime.now());

        invoices.add(invoice1);
        invoices.add(invoice2);
        invoices.add(invoice3);
        invoices.add(invoice4);

        // Mock behavior of invoiceService
        Mockito.when(invoiceService.invoiceListIban(invoiceRequestIban.getIban())).thenReturn(invoices);

        // Convert invoiceRequestIban to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(invoiceRequestIban);

        // Send POST request
        mockMvc.perform(post("/invoice/invoice_list_by_iban")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].amount").value(100000))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].amount").value(10000));
    }
}
