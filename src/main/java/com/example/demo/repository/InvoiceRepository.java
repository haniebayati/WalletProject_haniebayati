package com.example.demo.repository;

import java.time.LocalDateTime;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.Description;
import com.example.demo.model.Invoice;

public interface InvoiceRepository extends JpaRepository<Invoice, Long>{

	@Query("SELECT COALESCE(SUM(i.amount), 0) FROM Invoice i WHERE i.account.account_number = :account_number AND i.description = :description AND i.invoiceDate >= :startOfDay")
	double sumWithdrawalsByAccountAndDate(@Param("account_number") String accountNumber, 
	                                      @Param("description") Description description,
	                                      @Param("startOfDay") LocalDateTime startOfDay);

    
    @Query("SELECT COALESCE(SUM(i.amount), 0) FROM Invoice i WHERE i.account.iban = :iban AND i.description = :description AND i.invoiceDate >= :startOfDay")
    double sumWithdrawalsByIbanAndDate(@Param("iban") String iban, 
    									  @Param("description") Description description,
                                          @Param("startOfDay") LocalDateTime date);

}
