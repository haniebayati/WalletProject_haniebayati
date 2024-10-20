package com.example.demo.requestModel;

import jakarta.validation.constraints.Pattern;

public class InvoiceRequest {
	
//	@Pattern(regexp = "^\\d{4}\\d{4}\\d{4}\\d{4}$", message = "Invalid account number format.")
	@Pattern(regexp = "^\\d{10}$", message = "account number must be between 10 digits and contain only numbers.")
	private String account_number;
	private double amount;
    @Pattern(regexp = "^\\d{10}$", message = "The number must contain exactly 10 digits.")
    private String national_id;
	
	
	public String getAccount_number() {
		return account_number;
	}
	public void setAccount_number(String account_number) {
		this.account_number = account_number;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getNational_id() {
		return national_id;
	}
	public void setNational_id(String national_id) {
		this.national_id = national_id;
	}
	@Override
	public String toString() {
		return "InvoiceRequest [account_number=" + account_number + ", amount=" + amount + ", national_id="
				+ national_id + "]";
	}
	
	
	

}
