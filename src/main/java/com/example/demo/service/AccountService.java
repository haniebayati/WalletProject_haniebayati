package com.example.demo.service;

import com.example.demo.model.Account;
import com.example.demo.model.Persons;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.PersonsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class AccountService {
	
    @Autowired
    AccountRepository accountRepository;
    
    @Autowired
    PersonsRepository personsRepository;
    
    // Create new account
    public Account createAccount(Account account) {
    	
        Persons person = account.getPerson();
   
        // Check if the person exists
        if (personsRepository.existsByNational_id(person.getNational_id())) { 	
            person = personsRepository.findByNational_id(person.getNational_id())
                    .orElseThrow(() -> new IllegalArgumentException("کاربر پیدا نشد"));
        } else {
           // if person not exist , save it.
            person = personsRepository.save(person);
        }
        
        // Generate unique account number
        String accountNumber = generateAccountNumber();
        account.setAccount_number(accountNumber);
        
        // Generate unique IBAN
        String iban = generateIban(accountNumber);
        account.setIban(iban);
        
        // set default account balance
        account.setAccount_Balance(10000);
        
        // set person to account and save account
        account.setPerson(person);
        return accountRepository.save(account);
    }
    
    // Method to generate a unique account number
    public String generateAccountNumber() {
        Random random = new Random();
        String accountNumber;

        do {
            // Generate a random 10-digit number for the account number
            accountNumber = String.format("%010d", random.nextLong(9999999999L));
        } while (accountRepository.existsByAccount_number(accountNumber)); // Check uniqueness

        return accountNumber;
    }

    // Method to generate a unique IBAN based on account number
    public String generateIban(String accountNumber) {
        String countryCode = "IR";
        String checkDigits = "99"; 
        String code = "000000000000";   

        // Concatenate country code, check digits, and bank-specific fields
        String iban = countryCode + checkDigits + code + accountNumber;

        // Ensure uniqueness
        while (accountRepository.existsByIban(iban)) {
            iban = countryCode + checkDigits + code + generateAccountNumber();
        }
        return iban;
    }


    // Get all accounts
   /*   public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    } */

    // Get an account by ID
    public Account getAccountById(String account_number) {
		Account account = accountRepository.findByAccount_number(account_number);
				//.orElseThrow(() -> new RuntimeException("شماره حساب پیدا نشد"));
        return account ;
    }

    // Update an account
    public Account updateAccount(String account_number , Account updatedAccount) {
		Account account = accountRepository.findByAccount_number(account_number);
				//.orElseThrow(() -> new RuntimeException("شماره حساب پیدا نشد"));

		Persons person = account.getPerson();
		Persons updatedperson = updatedAccount.getPerson(); 
		
		person.setName(updatedperson.getName());
        person.setFamily(updatedperson.getFamily());
        person.setNational_id(updatedperson.getNational_id());
        person.setGender(updatedperson.getGender());
        person.setMobile(updatedperson.getMobile());
        person.setEmail(updatedperson.getEmail());
        person.setBirthDate(updatedperson.getBirthDate());
        person.setConscription(updatedperson.getConscription());
        account.setPerson(person);
        
        return accountRepository.save(account);
    }

    // Delete an account
    public void deleteAccount(String account_number) {
		Account account = accountRepository.findByAccount_number(account_number);
				//.orElseThrow(() -> new RuntimeException("شماره حساب پیدا نشد"));
        accountRepository.delete(account);
    }
}
