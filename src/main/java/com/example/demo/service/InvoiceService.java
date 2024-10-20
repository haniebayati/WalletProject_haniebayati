package com.example.demo.service;

import jakarta.transaction.Transactional;
import com.example.demo.model.Account;
import com.example.demo.model.Description;
import com.example.demo.model.Invoice;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class InvoiceService {
	
	private final int fee = 10 ;
	private final int ibanFee = 100 ;
	

	@Autowired
	InvoiceRepository invoiceRepository;
	@Autowired
	private AccountRepository accountRepository;

	@Transactional
	public Invoice deposit(String account_number, double amount) {
		Account account = accountRepository.findByAccount_number(account_number);
				//.orElseThrow(() -> new RuntimeException("شماره حساب پیدا نشد"));

		// increase to account balance
		account.setAccount_Balance(account.getAccount_Balance() + amount - fee);
		accountRepository.save(account);

		// new invoice for deposit
		Invoice invoice = new Invoice();
		invoice.setAccount(account);
		invoice.setAmount(amount);
		invoice.setFee(fee);
		invoice.setDescription(Description.Diposit);
		invoice.setInvoiceDate(LocalDateTime.now());
		return invoiceRepository.save(invoice);
	}

/*	@Transactional
	public Invoice withdraw(String account_number, double amount) {
		Account account = accountRepository.findByAccount_number(account_number)
				.orElseThrow(() -> new RuntimeException("Account Number not found"));

		// check Insufficient balance
		if (account.getAccount_Balance() < amount) {
			throw new RuntimeException("Insufficient balance");
		}
		else {
		// decrease from account balance
		account.setAccount_Balance(account.getAccount_Balance() - amount);
		accountRepository.save(account);

		// new invoice for withdraw
		Invoice invoice = new Invoice();
		invoice.setAccount(account);
		invoice.setAmount(amount);
		invoice.setDescription(Description.Withdraw);
		invoice.setInvoiceDate(LocalDateTime.now());
		return invoiceRepository.save(invoice);
		}
	}   */
	
	@Transactional
	public Invoice withdraw(String account_number , double amount) {
	    
		// check min Limitation
	    if (amount < 100000) {
	        throw new RuntimeException("Minimum withdrawal amount is 100,000 Rial.");
	    }
	    
	    // find account 
		Account account = accountRepository.findByAccount_number(account_number);
			//	.orElseThrow(() -> new RuntimeException("شماره حساب پیدا نشد"));

		// check Insufficient balance
		if (account.getAccount_Balance() < amount) {
			throw new RuntimeException("Insufficient balance");
		}

	    // calculate today withdraws 
	    double totalWithdrawToday = invoiceRepository.sumWithdrawalsByAccountAndDate(
	    		account_number ,Description.Withdraw , LocalDate.now().atStartOfDay());

	    System.out.println(totalWithdrawToday);
	    // check daily max Limitation
	    if ((totalWithdrawToday + amount) > 10000000) {
	        throw new RuntimeException("Daily withdrawal limit exceeded (10,000,000 Rial).");
	    }

		// decrease from account balance
		account.setAccount_Balance(account.getAccount_Balance() - amount - fee );
		accountRepository.save(account);

		// new invoice for withdraw
		Invoice invoice = new Invoice();
		invoice.setAccount(account);
		invoice.setAmount(amount);
		invoice.setFee(fee);
		invoice.setDescription(Description.Withdraw);
		invoice.setInvoiceDate(LocalDateTime.now());
		return invoiceRepository.save(invoice);
		
	}


	public List<Invoice> invoiceList(String account_number) {
		Account account = accountRepository.findByAccount_number(account_number);
				//.orElseThrow(() -> new RuntimeException("شماره حساب پیدا نشد"));*/
		return account.getInvoice();
	}
	
	@Transactional
	public Invoice depositIban(String iban, double amount) {
		Account account = accountRepository.findByIban(iban);
				//.orElseThrow(() -> new RuntimeException("شماره شبا پیدا نشد"));

		// increase to account balance
		account.setAccount_Balance(account.getAccount_Balance() + amount - ibanFee);
		accountRepository.save(account);

		// new invoice for deposit
		Invoice invoice = new Invoice();
		invoice.setAccount(account);
		invoice.setAmount(amount);
		invoice.setFee(ibanFee);
		invoice.setDescription(Description.Diposit);
		invoice.setInvoiceDate(LocalDateTime.now());
		return invoiceRepository.save(invoice);
	}

	@Transactional
	public Invoice withdrawIban(String iban, double amount) {
		// check min Limitation
	    if (amount < 100000) {
	        throw new RuntimeException("Minimum withdrawal amount is 100,000 Rial.");
	    }
	    // find account
		Account account = accountRepository.findByIban(iban);
				//.orElseThrow(() -> new RuntimeException("شماره شبا پیدا نشد"));

		// check Insufficient balance
		if (account.getAccount_Balance() < amount) {
			throw new RuntimeException("Insufficient balance");
		}
	    // calculate today withdraws 
	    double totalWithdrawToday = invoiceRepository.sumWithdrawalsByIbanAndDate(
	            account.getIban(),Description.Withdraw , LocalDate.now().atStartOfDay());

	    // check daily max Limitation
	    if ((totalWithdrawToday + amount) > 50000000) {
	        throw new RuntimeException("Daily withdrawal limit exceeded (50,000,000 Rial).");
	    }
		
		// decrease from account balance
		account.setAccount_Balance(account.getAccount_Balance() - amount - ibanFee);
		accountRepository.save(account);

		// new invoice for withdraw
		Invoice invoice = new Invoice();
		invoice.setAccount(account);
		invoice.setAmount(amount);
		invoice.setFee(ibanFee);
		invoice.setDescription(Description.Withdraw);
		invoice.setInvoiceDate(LocalDateTime.now());
		return invoiceRepository.save(invoice);
		
	}

	public List<Invoice> invoiceListIban(String iban) {
		Account account = accountRepository.findByIban(iban);
				//.orElseThrow(() -> new RuntimeException("شماره شبا پیدا نشد"));
		return account.getInvoice();
	}

}
