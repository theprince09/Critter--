package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.pet.PetRepository;
import com.udacity.jdnd.course3.critter.user.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PetRepository petRepository;

    public Customer saveCustomer(Customer customer){
        return customerRepository.save(customer);
    }
    public Customer getCustomerByPet(Long id){
        return customerRepository.findByPets(petRepository.getOne(id));
    }
    public Customer getCustomer(long id){
        return customerRepository.getOne(id);
    }
    public List<Customer> getCustomers(){
        return customerRepository.findAll();
    }
    public Employee saveEmployee(Employee employee){
        return employeeRepository.save(employee);
    }
    public Employee getEmployee(Long id){
        return employeeRepository.getOne(id);
    }
    public Employee setEmployeeAvailable(Set<DayOfWeek> days,Long id){
        Employee employee= employeeRepository.getOne(id);
        employee.setWorkDays(days);
        return employeeRepository.save(employee);
    }
    public List<Employee> findEmployeeService(EmployeeRequestDTO employeeRequestDTO){
        Set<EmployeeSkill> skills = employeeRequestDTO.getSkills();
        LocalDate date = employeeRequestDTO.getDate();
        return employeeRepository.findAllByWorkDaysAndSkillsIn(date.getDayOfWeek(),skills).stream().filter(employee -> employee.getSkills().containsAll(skills)).collect(Collectors.toList());
    }
    public List<Employee> getEmployeesByIds(List<Long> ids){
        return employeeRepository.findAllById(ids);
    }
}
