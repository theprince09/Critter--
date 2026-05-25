package com.udacity.jdnd.course3.critter.service;


import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetRepository;
import com.udacity.jdnd.course3.critter.user.Customer;
import com.udacity.jdnd.course3.critter.user.CustomerRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional

public class PetService {
    @Autowired
    private PetRepository petRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public Pet savePet(Pet pet){
        Pet pet1 = petRepository.save(pet);
        Customer customer = pet1.getCustomer();
        if(customer != null){
            customer.addPet(pet1);
            customerRepository.save(customer);
        }
        return pet1;
    }
    public Pet getPet(Long id){
        return petRepository.findById(id).orElse(null);
    }
    public List<Pet> getPets(){
        return petRepository.findAll();
    }
    public List<Pet> getPetsByOwner(Long id){
        return petRepository.findByCustomerId(id);
    }

    public List<Pet> getPetsByIds(List<Long> id){
        return petRepository.findAllById(id);
    }

}
