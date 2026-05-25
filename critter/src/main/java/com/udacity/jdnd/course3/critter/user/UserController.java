package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO) {

        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO, customer);
        Customer savedCustomer = userService.saveCustomer(customer);
        CustomerDTO response = new CustomerDTO();
        BeanUtils.copyProperties(savedCustomer, response);
        return response;
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers() {

        return userService.getCustomers()
                .stream()
                .map(this::convertCustomerToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId) {

        Customer customer = userService.getCustomerByPet(petId);
        return convertCustomerToDTO(customer);
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {

        Employee employee = convertDTOToEmployee(employeeDTO);
        Employee savedEmployee = userService.saveEmployee(employee);
        return convertEmployeeToDTO(savedEmployee);
    }

    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {

        Employee employee = userService.getEmployee(employeeId);
        return convertEmployeeToDTO(employee);
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(
            @RequestBody Set<DayOfWeek> daysAvailable,
            @PathVariable long employeeId) {
        userService.setEmployeeAvailable(daysAvailable, employeeId);
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(
            @RequestBody EmployeeRequestDTO employeeDTO) {
        return userService.findEmployeeService(employeeDTO)
                .stream()
                .map(this::convertEmployeeToDTO)
                .collect(Collectors.toList());
    }

    private CustomerDTO convertCustomerToDTO(Customer customer) {

        CustomerDTO dto = new CustomerDTO();
        BeanUtils.copyProperties(customer, dto);
        if(customer.getPets() != null){
            dto.setPetIds(
                    customer.getPets()
                            .stream()
                            .map(Pet::getId)
                            .collect(Collectors.toList())
            );
        }

        return dto;
    }

    private EmployeeDTO convertEmployeeToDTO(Employee employee) {

        EmployeeDTO dto = new EmployeeDTO();
        BeanUtils.copyProperties(employee, dto);
        dto.setDaysAvailable(employee.getWorkDays());
        return dto;
    }

    private Employee convertDTOToEmployee(EmployeeDTO dto) {

        Employee employee = new Employee();
        BeanUtils.copyProperties(dto, employee);
        employee.setWorkDays(dto.getDaysAvailable());
        return employee;
    }
}