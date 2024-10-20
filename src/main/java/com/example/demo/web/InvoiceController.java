package com.example.demo.web;

import java.util.List;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demo.model.Account;
import com.example.demo.model.Invoice;
import com.example.demo.model.Persons;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.PersonsRepository;
import com.example.demo.requestModel.InvoiceRequest;
import com.example.demo.requestModel.InvoiceRequestIban;
import com.example.demo.service.InvoiceService;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/invoice")
public class InvoiceController {
	
    @Autowired
    InvoiceService invoiceService;
    
    @Autowired
    AuthenticationController authenticationController;
    
    @Autowired
    PersonsRepository personsRepository;
    
    @Autowired
    AccountRepository accountRepository;
    
    
    // deposit by accountNumber
	@PostMapping(value = "/deposit_by_accountNumber")
	public Invoice deposit(@RequestBody InvoiceRequest invoiceRequest) {
		return invoiceService.deposit(invoiceRequest.getAccount_number(), invoiceRequest.getAmount());
	}

	// withdraw by accountNumber
	@PostMapping(value = "/withdraw_by_accountNumber")
	public Invoice withdraw(@RequestBody InvoiceRequest invoiceRequest) {
		return invoiceService.withdraw(invoiceRequest.getAccount_number(), invoiceRequest.getAmount());
	}
    
	// invoice list by accountNumber
/*	@PostMapping(value = "/invoice_list_by_accountNumber")
	public List<Invoice> invoiceList(@RequestBody InvoiceRequest invoiceRequest) {
		// return invoiceService.invoiceList(invoiceListRequest.getAccount_number());
		List<Invoice> invoices = invoiceService.invoiceList(invoiceRequest.getAccount_number());
		invoices.stream().forEach(System.out::println);
		return invoices ;	
	} */
	
	// invoice list by accountNumber
	@PostMapping(value = "/invoice_list_by_accountNumber")
	public ResponseEntity<?> invoiceList(@RequestBody InvoiceRequest invoiceRequest, HttpServletRequest request) {
	    // Retrieve the token from the header
	    String authorizationHeader = request.getHeader("Authorization");
	    if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Missing or invalid Authorization header");
	    }

	    String token = authorizationHeader.substring(7); // Remove "Bearer " from the token

	    try {
	        // Verify the token
	        DecodedJWT decodedJWT = AuthenticationController.verifyToken(token);
	        String mobile = decodedJWT.getSubject(); // Extract the mobile number from the token

	        // Find the user by mobile number
	        Persons person = personsRepository.findByMobile(mobile);
	        
	        if (person == null) {
	        	return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Person not found");
	        }

	        // Check if the account belongs to the user
	        Account account = accountRepository.findByAccount_number(invoiceRequest.getAccount_number());
	        
	        if (account == null) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found");
	        }

	        // Assuming the Account entity has a method to get the owner's mobile
	        if (!account.getPerson()
	        		.equals(person)) {
	            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("شما اجازه دسترسی به لیست تراکنش ها را ندارید.");
	        }

	        // Retrieve invoices if the user is authorized
	        List<Invoice> invoices = invoiceService.invoiceList(invoiceRequest.getAccount_number());
	        return ResponseEntity.ok(invoices);
	        
	    } catch (JWTVerificationException exception) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid JWT token");
	    }
	}

	
    // deposit by iban
	@PostMapping(value = "/deposit_by_iban")
	public Invoice depositIban(@RequestBody InvoiceRequestIban invoiceRequestIban) {
		return invoiceService.depositIban(invoiceRequestIban.getIban(),invoiceRequestIban.getAmount());
	}

	// withdraw by iban
	@PostMapping(value = "/withdraw_by_iban")
	public Invoice withdrawIban(@RequestBody InvoiceRequestIban invoiceRequestIban) {
		return invoiceService.withdrawIban(invoiceRequestIban.getIban(),invoiceRequestIban.getAmount());
	}
    
	// invoice list by iban
/*	@PostMapping(value = "/invoice_list_by_iban")
	public List<Invoice> invoiceListIban(@RequestBody InvoiceRequestIban invoiceRequestIban) {
		List<Invoice> invoices = invoiceService.invoiceListIban(invoiceRequestIban.getIban());
		invoices.stream().forEach(System.out::println);
		return invoices ;	
	} */
	
	
	// invoice list by iban
	@PostMapping(value = "/invoice_list_by_iban")
	public ResponseEntity<?> invoiceListIban(@RequestBody InvoiceRequestIban invoiceRequestIban, HttpServletRequest request) {
	    // Retrieve the token from the header
	    String authorizationHeader = request.getHeader("Authorization");
	    if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Missing or invalid Authorization header");
	    }

	    String token = authorizationHeader.substring(7); // Remove "Bearer " from the token

	    try {
	        // Verify the token
	        DecodedJWT decodedJWT = AuthenticationController.verifyToken(token);
	        String mobile = decodedJWT.getSubject(); // Extract the mobile number from the token

	        // Find the user by mobile number
	        Persons person = personsRepository.findByMobile(mobile);
	        
	        if (person == null) {
	        	return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Person not found");
	        }

	        // Check if the account belongs to the user
	        Account account = accountRepository.findByIban(invoiceRequestIban.getIban());
	        
	        if (account == null) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found");
	        }

	        // Assuming the Account entity has a method to get the owner's mobile
	        if (!account.getPerson()
	        		.equals(person)) {
	            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("شما اجازه دسترسی به لیست تراکنش ها را ندارید.");
	        }

	        // Retrieve invoices if the user is authorized
	        List<Invoice> invoices = invoiceService.invoiceListIban(invoiceRequestIban.getIban());
	        return ResponseEntity.ok(invoices);
	        
	    } catch (JWTVerificationException exception) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid JWT token");
	    }
	}

}



