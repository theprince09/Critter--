package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.service.PetService;
import com.udacity.jdnd.course3.critter.service.ScheduleService;
import com.udacity.jdnd.course3.critter.service.UserService;
import com.udacity.jdnd.course3.critter.user.Employee;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private PetService petService;

    @Autowired
    private UserService userService;

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {

        Schedule schedule = convertDTOToSchedule(scheduleDTO);

        Schedule savedSchedule = scheduleService.saveSchedule(schedule);

        return convertScheduleToDTO(savedSchedule);
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {

        return scheduleService.getSchedule()
                .stream()
                .map(this::convertScheduleToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {

        return scheduleService.getScheduleByPets(petId)
                .stream()
                .map(this::convertScheduleToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {

        return scheduleService.getScheduleEmployee(employeeId)
                .stream()
                .map(this::convertScheduleToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {

        return scheduleService.getScheduleCustomers(customerId)
                .stream()
                .map(this::convertScheduleToDTO)
                .collect(Collectors.toList());
    }

    private Schedule convertDTOToSchedule(ScheduleDTO scheduleDTO) {

        Schedule schedule = new Schedule();

        BeanUtils.copyProperties(scheduleDTO, schedule);

        if (scheduleDTO.getPetIds() != null) {
            schedule.setPets(
                    petService.getPetsByIds(scheduleDTO.getPetIds())
            );
        }

        if (scheduleDTO.getEmployeeIds() != null) {
            schedule.setEmployees(
                    userService.getEmployeesByIds(scheduleDTO.getEmployeeIds())
            );
        }

        return schedule;
    }

    private ScheduleDTO convertScheduleToDTO(Schedule schedule) {

        ScheduleDTO scheduleDTO = new ScheduleDTO();

        BeanUtils.copyProperties(schedule, scheduleDTO);

        if (schedule.getPets() != null) {
            scheduleDTO.setPetIds(
                    schedule.getPets()
                            .stream()
                            .map(Pet::getId)
                            .collect(Collectors.toList())
            );
        }

        if (schedule.getEmployees() != null) {
            scheduleDTO.setEmployeeIds(
                    schedule.getEmployees()
                            .stream()
                            .map(Employee::getId)
                            .collect(Collectors.toList())
            );
        }

        return scheduleDTO;
    }
}