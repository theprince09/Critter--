package com.udacity.jdnd.course3.critter.schedule;


import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.user.Employee;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    @JoinTable(name = "scheduleEmployee",joinColumns = @JoinColumn(name = "schedule_id"),inverseJoinColumns = @JoinColumn(name = "employee_id"))
            private List<Employee> employees;

    @ManyToMany
    private List<Pet> pets;
    private LocalDate date;

    @ElementCollection(targetClass = EmployeeSkill.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "ScheduledActivitiesTable",joinColumns = @JoinColumn(name = "ScheduledId"))
    @Column(name = "activity")
    private Set<EmployeeSkill> activities = new HashSet<>();


}
