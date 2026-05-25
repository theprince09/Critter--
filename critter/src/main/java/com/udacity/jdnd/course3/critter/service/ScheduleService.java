package com.udacity.jdnd.course3.critter.service;


import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetRepository;
import com.udacity.jdnd.course3.critter.schedule.Schedule;
import com.udacity.jdnd.course3.critter.schedule.ScheduleRepository;
import com.udacity.jdnd.course3.critter.user.CustomerRepository;
import com.udacity.jdnd.course3.critter.user.EmployeeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ScheduleService {
    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PetRepository petRepository;

    public Schedule saveSchedule(Schedule schedule){
        return scheduleRepository.save(schedule);
    }
    public List<Schedule> getSchedule(){
        return scheduleRepository.findAll();
    }
    public List<Schedule> getScheduleCustomers(Long id){
        List<Schedule> schCustomer = new ArrayList<>();
        List<Pet> cusPet = customerRepository.getOne(id).getPets();
        for (Pet pet:cusPet){
            schCustomer.addAll(scheduleRepository.findByPets(pet));
        }
        return schCustomer;
    }
    public List<Schedule> getScheduleEmployee(Long id){
        return scheduleRepository.findByEmployees(employeeRepository.getOne(id));
    }
    public List<Schedule> getScheduleByPets(Long id){
        return scheduleRepository.findByPets(petRepository.getOne(id));
    }
}
