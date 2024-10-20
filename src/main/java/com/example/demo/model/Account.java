package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = true , unique = true)
  //  @Pattern(regexp = "^\\d{4}\\d{4}\\d{4}\\d{4}$", message = "Invalid account number format.")
    @Pattern(regexp = "^\\d{10}$", message = "Account Number must be 10 digits and contain only numbers.")
    private String account_number;
    
    @Column(nullable = true , unique = true)
    @Pattern(regexp = "^IR\\d{24}$", message = "Invalid IBAN format.")
    private String iban;
    
    @Column(nullable = true)
    @Min(value = 10000 , message = "Account Balance must be > 10000 RIAL")
    private double account_Balance ;
    
    @Column(nullable = false)
    private LocalDate create_Account;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "person_id" , nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Persons person;
    
    @Column(nullable = true)
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Invoice> invoice;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccount_number() {
        return account_number;
    }

    public void setAccount_number(String account_number) {
        this.account_number = account_number;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public double getAccount_Balance() {
        return account_Balance;
    }

    public void setAccount_Balance(double account_Balance) {
        this.account_Balance = account_Balance;
    }

    public LocalDate getCreate_Account() {
        return create_Account;
    }

    public void setCreate_Account(LocalDate create_Account) {
        this.create_Account = create_Account;
    }

	public Persons getPerson() {
		return person;
	}

	public void setPerson(Persons person) {
		this.person = person;
	} 

	public List<Invoice> getInvoice() {
		return invoice;
	}

	public void setInvoice(List<Invoice> invoice) {
		this.invoice = invoice;
	}
	
    @PrePersist
    protected void onCreate() {
    	create_Account = LocalDate.now();
    }

	@Override
	public String toString() {
		return "Account [id=" + id + ", account_number=" + account_number + ", iban=" + iban + ", account_Balance="
				+ account_Balance + ", create_Account=" + create_Account + "]";
	}
	
	

}
