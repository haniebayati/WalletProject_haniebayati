package com.example.demo.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {
	
	
    @Query("SELECT a FROM Account a WHERE a.account_number = :accountNumber")
    Account findByAccount_number(@Param("accountNumber") String accountNumber);
    
    @Query("SELECT a FROM Account a WHERE a.iban = :iban")
    Account findByIban(@Param("iban") String iban);

    @Query("SELECT COUNT(*) > 0 FROM Account a WHERE a.account_number = :accountNumber")
	boolean existsByAccount_number(@Param("accountNumber") String accountNumber);

    @Query("SELECT COUNT(*) > 0 FROM Account a WHERE a.iban = :iban")
	boolean existsByIban(@Param("iban") String iban);

}
