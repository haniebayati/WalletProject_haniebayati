package com.example.demo.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Pattern;


@Entity
@Table(name= "walletUser")
public class User {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    @Pattern(regexp = "^09\\d{9}$", message = "Invalid mobile number format.")
	private String mobile;
    
    @Column(nullable = true)
	private int otpCode;
	
    @Column(nullable = true)
	private LocalDateTime otpTime;
	
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public int getOtpCode() {
		return otpCode;
	}
	public void setOtpCode(int otpCode) {
		this.otpCode = otpCode;
	}
	public LocalDateTime getOtpTime() {
		return otpTime;
	}
	public void setOtpTime(LocalDateTime otpTime) {
		this.otpTime = otpTime;
	}
	
    @PrePersist
    protected void onCreate() {
    	otpTime = LocalDateTime.now();
    }
	
	

}
