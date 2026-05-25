package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.pet.Pet;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface CustomerRepository extends JpaRepository<Customer,Long> {
    Customer findByPets(Pet pet);

    //List<Pet> getOn();
}
