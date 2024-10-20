package com.example.demo.test;

import com.example.demo.WalletApplication;
import com.example.demo.model.Account;
import com.example.demo.model.Gender;
import com.example.demo.model.Persons;
import com.example.demo.requestModel.InvoiceRequest;
import com.example.demo.requestModel.UpdatePersonRequest;
import com.example.demo.service.PersonsService;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = WalletApplication.class)
@AutoConfigureMockMvc
public class PersonsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonsService personsService;

    @Test
    public void testCreatePerson() throws Exception {
        // Test data
        Persons person = new Persons();
        person.setNational_id("0023396024");
        person.setName("hanie");
        person.setFamily("bayati");
        person.setGender(Gender.Female);
        person.setMobile("09196972095");
        person.setEmail("hanie@gmail.com");
        person.setBirthDate(LocalDate.of(2000, 7, 23));
        person.setConscription(false);

        // Mock behavior of personsService
        Mockito.doNothing().when(personsService).createPerson(any(Persons.class));

        // Convert person to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String personJson = objectMapper.writeValueAsString(person);

        // Send POST request
        mockMvc.perform(post("/persons/create_new_person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(personJson))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("اطلاعات کاربر با موفقیت ثبت شد."));
    }

    @Test
    public void testGetPersonById() throws Exception {
        // Test data
        Persons person = new Persons();
        person.setNational_id("0023396024");
        person.setName("hanie");
        person.setFamily("bayati");

        // Mock behavior of personsService
        Mockito.when(personsService.getPersonsById(person.getNational_id())).thenReturn(person);

        // Create InvoiceRequest
        InvoiceRequest invoiceRequest = new InvoiceRequest();
        invoiceRequest.setNational_id(person.getNational_id());

        // Convert InvoiceRequest to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(invoiceRequest);

        // Send POST request
        mockMvc.perform(post("/persons/get_person_by_id")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("hanie"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.family").value("bayati"));
    }

    @Test
    public void testGetPersonAccountsById() throws Exception {
        // Test data
        List<Account> accounts = new ArrayList<>();
        Account account1 = new Account();
        account1.setAccount_number("1234567890");
        accounts.add(account1);

        Account account2 = new Account();
        account2.setAccount_number("0987654321");
        accounts.add(account2);

        // Mock behavior of personsService
        Mockito.when(personsService.getAccountsById("0023396024")).thenReturn(accounts);

        // Create InvoiceRequest
        InvoiceRequest invoiceRequest = new InvoiceRequest();
        invoiceRequest.setNational_id("0023396024");

        // Convert InvoiceRequest to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(invoiceRequest);

        // Send POST request
        mockMvc.perform(post("/persons/get_person_accounts_by_id")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].account_number").value("1234567890"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].account_number").value("0987654321"));
    }

    @Test
    public void testUpdatePerson() throws Exception {
        // Test data
        UpdatePersonRequest updatePersonRequest = new UpdatePersonRequest();

        // Create and configure the updated person
        Persons updatedPerson = new Persons();
        updatedPerson.setNational_id("0023396024");
        updatedPerson.setName("hanie_updated");

        // Set up the InvoiceRequest with the national ID
        InvoiceRequest invoiceRequest = new InvoiceRequest();
        invoiceRequest.setNational_id("0023396024"); // This should be the national ID to update

        updatePersonRequest.setInvoiceRequest(invoiceRequest);
        updatePersonRequest.setUpdatedPerson(updatedPerson);

        // Mock behavior of personsService
        Mockito.doNothing().when(personsService).updatePerson(invoiceRequest.getNational_id(), updatedPerson);

        // Convert UpdatePersonRequest to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(updatePersonRequest);

        // Send PUT request
        mockMvc.perform(put("/persons/update_a_person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("اطلاعات کاربر با موفقیت به روز رسانی شد."));
    }

    @Test
    public void testDeletePerson() throws Exception {
        // Test data
        InvoiceRequest invoiceRequest = new InvoiceRequest();
        invoiceRequest.setNational_id("0023396024");

        // Mock behavior of personsService
        Mockito.doNothing().when(personsService).deletePerson(invoiceRequest.getNational_id());

        // Convert InvoiceRequest to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(invoiceRequest);

        // Send DELETE request
        mockMvc.perform(delete("/persons/delete_a_person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("کاربر با موفقیت حذف شد."));
    }

}
