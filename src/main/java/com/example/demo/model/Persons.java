package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;


@Entity
@ConscriptionValidation
public class Persons {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    @Pattern(regexp = "^\\d{10}$", message = "The national id must contain exactly 10 digits.")
    private String national_id;
    
    @Column(length = 50, nullable = false)
    private String name;
    
    @Column(length = 50, nullable = false)
    private String family;
    
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;
    
    @Column(nullable = false)
    @Pattern(regexp = "^09\\d{9}$", message = "Invalid mobile number format.")
    private String mobile;
    
    @Column(nullable = false)
    @Email
    private String email;
    
    @Column(nullable = false)
    private LocalDate birthDate;
    
    @Column(nullable = true)
    private Boolean conscription;
    
    @Column(nullable = true)
    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Account> accounts;   


    public List<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}  

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNational_id() {
        return national_id;
    }

    public void setNational_id(String national_id) {
        this.national_id = national_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Boolean getConscription() {
        return conscription;
    }

    public void setConscription(Boolean conscription) {
        this.conscription = conscription;
    }

	@Override
	public String toString() {
		return "Persons [id=" + id + ", national_id=" + national_id + ", name=" + name + ", family=" + family
				+ ", gender=" + gender + ", mobile=" + mobile + ", email=" + email + ", birthDate=" + birthDate
				+ ", conscription=" + conscription + "]";
	}
    
    

}
