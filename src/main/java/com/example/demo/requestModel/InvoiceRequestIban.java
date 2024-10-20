package com.example.demo.requestModel;

import jakarta.validation.constraints.Pattern;

public class InvoiceRequestIban {
	
    @Pattern(regexp = "^IR\\d{24}$", message = "Invalid IBAN format.")
    private String iban;
	private double amount;
	
	public String getIban() {
		return iban;
	}
	public void setIban(String iban) {
		this.iban = iban;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	

}
