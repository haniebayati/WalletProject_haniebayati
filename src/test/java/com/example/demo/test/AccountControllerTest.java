package com.example.demo.test;

import com.example.demo.WalletApplication;
import com.example.demo.model.*;
import com.example.demo.requestModel.InvoiceRequest;
import com.example.demo.requestModel.UpdatedAccountRequest;
import com.example.demo.service.AccountService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

@SpringBootTest(classes = WalletApplication.class)
@AutoConfigureMockMvc
public class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    @Test
    public void testCreateAccount() throws Exception {
        // Test data
        Account account = new Account();
        Persons person = new Persons();
        
        account.setId(1L);
        
        // Set person details
        person.setId(1L);
        person.setNational_id("0023396024");
        person.setName("hanie");
        person.setFamily("bayati");
        person.setGender(Gender.Female);
        person.setMobile("09196972095");
        person.setEmail("hanie@gmail.com");
        person.setBirthDate(LocalDate.of(2000, 7, 23));
        person.setConscription(false);

        account.setPerson(person);

        // jUnit test
        
        // Mock behavior of accountService to generate account number and IBAN
        Mockito.when(accountService.createAccount(any(Account.class))).thenAnswer(invocation -> {
            Account acc = invocation.getArgument(0);
            acc.setAccount_Balance(10000); //set default account balance 
            acc.setAccount_number("1234567890");  // Simulate account number generation
            acc.setIban("IR12345678901234567890");  // Simulate IBAN generation
            acc.setCreate_Account(LocalDate.now());  // Set creation date
            return acc;
        });
        
        
        // Convert account to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String accountJson = objectMapper.writeValueAsString(account);

        // Send POST request
        mockMvc.perform(post("/accounts/create_new_account")
                .contentType(MediaType.APPLICATION_JSON)
                .content(accountJson))  // Request body
                .andExpect(status().isOk())  // Validate response status
                .andExpect(MockMvcResultMatchers.jsonPath("$.person.name").value("hanie"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.person.family").value("bayati"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.accountNumber").value("1234567890"))  // Check account number
                .andExpect(MockMvcResultMatchers.jsonPath("$.iban").value("IR12345678901234567890"));  // Check IBAN
    }

    @Test
    public void testGetAccountById() throws Exception {
        // Test data
        Account account = new Account();
        account.setAccount_number("1234567890");
        account.setId(1L);
        
        // Mock behavior of accountService
        Mockito.when(accountService.getAccountById(account.getAccount_number())).thenReturn(account);

        // Create InvoiceRequest
        InvoiceRequest invoiceRequest = new InvoiceRequest();
        invoiceRequest.setAccount_number(account.getAccount_number());

        // Convert InvoiceRequest to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(invoiceRequest);

        // Send POST request
        mockMvc.perform(post("/accounts/get_account_by_id") 
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))  // Request body
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.account_number").value(account.getAccount_number()));
    }

    @Test
    public void testUpdateAccount() throws Exception {
        // Test data
        UpdatedAccountRequest updatedAccountRequest = new UpdatedAccountRequest();

        // Create and configure the updated account
        Account updatedAccount = new Account();
        Persons updatedPerson = new Persons();
        
        
        // Set updated Person details
        updatedPerson.setId(1L);
        updatedPerson.setNational_id("0023396025");
        updatedPerson.setName("elahe");
        updatedPerson.setFamily("bayati");
        updatedPerson.setGender(Gender.Female);
        updatedPerson.setMobile("09126972095");
        updatedPerson.setEmail("elahe@gmail.com");
        updatedPerson.setBirthDate(LocalDate.of(2002, 7, 23));
        updatedPerson.setConscription(false);
        
        updatedAccount.setPerson(updatedPerson);
        

        // Set up the InvoiceRequest with the account number
        InvoiceRequest invoiceRequest = new InvoiceRequest();
        invoiceRequest.setAccount_number("1234567890");

        updatedAccountRequest.setInvoiceRequest(invoiceRequest);
        updatedAccountRequest.setUpdatedAccount(updatedAccount);

        // Mock behavior of accountService
        Mockito.doNothing().when(accountService).updateAccount(invoiceRequest.getAccount_number(), updatedAccount);

        // Convert UpdatedAccountRequest to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(updatedAccountRequest);

        // Send PUT request
        mockMvc.perform(put("/accounts/update_an_account")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))  // Request body
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("حساب با موفقیت به روز رسانی شد."));
    }
    
    @Test
    public void testDeleteAccount() throws Exception {
        // Test data: create an InvoiceRequest to hold the account number
        InvoiceRequest invoiceRequest = new InvoiceRequest();
        invoiceRequest.setAccount_number("1234567890");

        // Mock behavior of accountService to do nothing when deleteAccount is called
        Mockito.doNothing().when(accountService).deleteAccount(invoiceRequest.getAccount_number());

        // Convert InvoiceRequest to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(invoiceRequest);

        // Send DELETE request
        mockMvc.perform(delete("/accounts/delete_an_account")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))  // Request body
                .andExpect(status().isOk())  // Check that the status is OK
                .andExpect(MockMvcResultMatchers.content().string("حساب با موفقیت حذف شد."));  // Validate response message
    }

}

