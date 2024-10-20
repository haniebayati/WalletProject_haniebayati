package com.example.demo.service;

import com.example.demo.model.Account;
import com.example.demo.model.Persons;
import com.example.demo.repository.PersonsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PersonsService {
    @Autowired
    PersonsRepository personsRepository;

    // Create new person
    public Persons createPerson(Persons person) {
        return personsRepository.save(person);
    }

    // Get all persons
 /*   public List<Persons> getAllPersons() {
        return personsRepository.findAll();
    } */

    // Get a person by nationalID
    public Persons getPersonsById(String national_id) {
		Persons person = personsRepository.findByNational_id(national_id)
				.orElseThrow(() -> new RuntimeException("کاربر پیدا نشد."));
        return person ;
    }

    // Update a Person
    public Persons updatePerson(String national_id , Persons updatedPerson) {
		Persons person = personsRepository.findByNational_id(national_id)
				.orElseThrow(() -> new RuntimeException("کاربر پیدا نشد."));
        person.setName(updatedPerson.getName());
        person.setFamily(updatedPerson.getFamily());
        person.setNational_id(updatedPerson.getNational_id());
        person.setGender(updatedPerson.getGender());
        person.setMobile(updatedPerson.getMobile());
        person.setEmail(updatedPerson.getEmail());
        person.setBirthDate(updatedPerson.getBirthDate());
        person.setConscription(updatedPerson.getConscription());
        personsRepository.save(person);
        return person;
    }

    // Delete a person
    public void deletePerson(String national_id) {
		Persons person = personsRepository.findByNational_id(national_id)
				.orElseThrow(() -> new RuntimeException("کاربر پیدا نشد."));
        personsRepository.delete(person);
    }

    // Get person accounts by nationalID
	public List<Account> getAccountsById(String national_id) {
		Persons person = personsRepository.findByNational_id(national_id)
				.orElseThrow(() -> new RuntimeException("کاربر پیدا نشد."));
		return person.getAccounts();
	}

}
