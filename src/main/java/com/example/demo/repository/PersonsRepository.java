package com.example.demo.repository;

import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.Persons;

public interface PersonsRepository extends JpaRepository<Persons, Long> {
	
    @Query("SELECT a FROM Persons a WHERE a.national_id = :national_id")
    Optional<Persons> findByNational_id(@Param("national_id") String national_id);

    @Query("SELECT COUNT(*) > 0 FROM Persons WHERE national_id = :national_id")
	boolean existsByNational_id(@Param("national_id") String national_id);
    
    @Query("SELECT a FROM Persons a WHERE a.mobile = :mobile")
    Persons findByMobile(@Param("mobile") String mobile);
	
}
