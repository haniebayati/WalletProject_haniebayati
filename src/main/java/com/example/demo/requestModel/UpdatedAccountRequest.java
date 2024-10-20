package com.example.demo.requestModel;

import com.example.demo.model.Account;

public class UpdatedAccountRequest {
	
    private InvoiceRequest invoiceRequest;
    private Account updatedAccount;
    
	public InvoiceRequest getInvoiceRequest() {
		return invoiceRequest;
	}
	public void setInvoiceRequest(InvoiceRequest invoiceRequest) {
		this.invoiceRequest = invoiceRequest;
	}
	public Account getUpdatedAccount() {
		return updatedAccount;
	}
	public void setUpdatedAccount(Account updatedAccount) {
		this.updatedAccount = updatedAccount;
	}
	@Override
	public String toString() {
		return "UpdatedAccountRequest [invoiceRequest=" + invoiceRequest + ", updatedAccount=" + updatedAccount + "]";
	}
	
    
    

}
