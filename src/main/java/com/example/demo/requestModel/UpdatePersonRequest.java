package com.example.demo.requestModel;

import com.example.demo.model.Persons;

public class UpdatePersonRequest {
	
    private InvoiceRequest invoiceRequest;
    private Persons updatedPerson;
    
	public InvoiceRequest getInvoiceRequest() {
		return invoiceRequest;
	}
	public void setInvoiceRequest(InvoiceRequest invoiceRequest) {
		this.invoiceRequest = invoiceRequest;
	}
	public Persons getUpdatedPerson() {
		return updatedPerson;
	}
	public void setUpdatedPerson(Persons updatedPerson) {
		this.updatedPerson = updatedPerson;
	}

    
}

