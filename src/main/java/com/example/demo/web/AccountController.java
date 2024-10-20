package com.example.demo.web;

import com.example.demo.model.Account;
import com.example.demo.requestModel.InvoiceRequest;
import com.example.demo.requestModel.UpdatedAccountRequest;
import com.example.demo.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    AccountService accountService;


    // create new account
    @PostMapping(value = "/create_new_account")
    public Account createAccount(@RequestBody Account account) {
        return accountService.createAccount(account);
    }

    // Get all accounts
 /*   @GetMapping(value = "/get_all_accounts")
    public List<Account> getAllAccounts() {
        return accountService.getAllAccounts();
    }   */

    // Get an account by ID
    @PostMapping(value = "/get_account_by_id")
    public Account getAccountById(@RequestBody InvoiceRequest invoiceRequest) {
        Account account = accountService.getAccountById(invoiceRequest.getAccount_number());
        return account ;
    }

    // Update an account
    @PutMapping("/update_an_account")
    public ResponseEntity<String> updateAccount(@RequestBody UpdatedAccountRequest updatedAccountRequest) {
    	accountService.updateAccount(updatedAccountRequest.getInvoiceRequest().getAccount_number(),updatedAccountRequest.getUpdatedAccount());
    	return ResponseEntity.ok("حساب با موفقیت به روز رسانی شد."); 
    }

    // Delete an account
    @DeleteMapping("/delete_an_account")
    public ResponseEntity<String> deleteAccount(@RequestBody InvoiceRequest invoiceRequest) {
        accountService.deleteAccount(invoiceRequest.getAccount_number());
        return ResponseEntity.ok("حساب با موفقیت حذف شد.");
    }


}
