package com.example.demo.web;

import com.example.demo.model.Account;
import com.example.demo.model.Persons;
import com.example.demo.requestModel.InvoiceRequest;
import com.example.demo.requestModel.UpdatePersonRequest;
import com.example.demo.service.PersonsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/persons")
public class PersonsController {

    @Autowired
    PersonsService personsService;

    // create new person
    @PostMapping(value = "/create_new_person")
    public ResponseEntity<String> createPerson(@RequestBody Persons person) {
    	personsService.createPerson(person);
    	System.out.println(person);
    	return ResponseEntity.ok("اطلاعات کاربر با موفقیت ثبت شد.");
    }

    // Get all persons
 /*   @GetMapping(value = "/get_all_persons")
    public List<Persons> getAllPersons() {
        return personsService.getAllPersons();
    } */

    // Get a person by nationalID
    @PostMapping(value = "/get_person_by_id")
    public Persons getPersonById(@RequestBody InvoiceRequest invoiceRequest) {
    	Persons person = personsService.getPersonsById(invoiceRequest.getNational_id());
        return person ;
    }
    
    // Get person accounts by nationalID
    @PostMapping(value = "/get_person_accounts_by_id")
    public List<Account> getPersonAccountsById(@RequestBody InvoiceRequest invoiceRequest) {
        List<Account> account = personsService.getAccountsById(invoiceRequest.getNational_id());
        account.stream().forEach(System.out::println);
		return account ;
    }

    // Update a person by nationalID
    @PutMapping("/update_a_person")
    public ResponseEntity<String> updatePerson(@RequestBody UpdatePersonRequest updatePersonRequest) {
        personsService.updatePerson(updatePersonRequest.getInvoiceRequest().getNational_id(), updatePersonRequest.getUpdatedPerson());
        return ResponseEntity.ok("اطلاعات کاربر با موفقیت به روز رسانی شد.");
    }

    // Delete a person by nationalID
    @DeleteMapping("/delete_a_person")
    public ResponseEntity<String> deletePerson(@RequestBody InvoiceRequest invoiceRequest) {
        personsService.deletePerson(invoiceRequest.getNational_id());
        return ResponseEntity.ok("کاربر با موفقیت حذف شد.");
    }


}
