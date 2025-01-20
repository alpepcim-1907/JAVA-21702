package com.example.projektzaliczenieYusufEpcim.Service;

import com.example.projektzaliczenieYusufEpcim.Model.Employee;
import com.example.projektzaliczenieYusufEpcim.Repository.EmployeeRepository;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.*;


@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {


    private EmployeeService employeeService;

    private EmployeeRepository employeeRepository;


    @BeforeEach
    void setUp() {

        employeeRepository = Mockito.mock(EmployeeRepository.class);
        employeeService = new EmployeeService(employeeRepository);
    }

    @Test
    void createEmployee() {


        Employee employee = new Employee();
        employee.setFirstName("Yusuf");
        employee.setLastName("surname");
        employee.setPhone(123123);
        employee.setDepartment("IT");
        employee.setEmail("aaa@aaaa");
        employee.setCity("Ankara");
        employee.setSalary(123123);

        Mockito.when(employeeRepository.save(employee)).thenReturn(employee);

        Employee result = employeeService.createEmployee(employee);

        Assertions.assertEquals(result, employee);

        Mockito.verify(employeeRepository).save(employee);

    }

    @Test
    void getAllEmployee() {


        Employee employee2 = new Employee();
        Employee employee1=new Employee();

        employee2.setFirstName("Yusuf");
        employee2.setLastName("surname");
        employee2.setPhone(123123);
        employee2.setDepartment("IT");
        employee2.setEmail("aaa@aaaa");
        employee2.setCity("Ankara");
        employee2.setSalary(123123);

        employee1.setFirstName("Tahsin");
        employee1.setLastName("Shan");
        employee1.setPhone(123123);
        employee1.setDepartment("IT");
        employee1.setEmail("aaa@aaaa");
        employee1.setCity("Ankara");
        employee1.setSalary(999999);

        ArrayList<Employee> employees0 = new ArrayList<>();
        employees0.add(employee2);
        employees0.add(employee1);




        Mockito.when(employeeRepository.findAll()).thenReturn(employees0);

        List<Employee> result =  employeeService.getAllEmployee();

        Assertions.assertEquals(result,employees0);


        Mockito.verify(employeeRepository).findAll();
    }

    @Test
    public void deleteEmployeeById() {
    }

    @Test
    void getEmployeeById() {

        Employee employee = new Employee();
        employee.setFirstName("Yusuf");
        employee.setLastName("surname");
        employee.setPhone(123123);
        employee.setDepartment("IT");
        employee.setEmail("aaa@aaaa");
        employee.setCity("Ankara");
        employee.setSalary(123123);

        Mockito.when(employeeRepository.findById(employee.getId())).thenReturn(Optional.of(employee));

        Employee result = employeeService.getEmployeeById(employee.getId());

        Assertions.assertEquals(result, employee);

        Mockito.verify(employeeRepository).findById(employee.getId());

    }

    @Test
    void updateById() {

        Employee newEmployee = new Employee();
        newEmployee.setId(3L);
        newEmployee.setFirstName("yusuf");
        newEmployee.setLastName("surname");
        newEmployee.setPhone(123123);
        newEmployee.setDepartment("IT");
        newEmployee.setEmail("aaa@aaaa");
        newEmployee.setCity("Ankara");
        newEmployee.setSalary(123123);

        Mockito.when(employeeRepository.findById(3L)).thenReturn(Optional.of(newEmployee));
        Mockito.when(employeeService.createEmployee(newEmployee)).thenReturn(newEmployee);

        Employee result = employeeService.updateById(newEmployee.getId(),newEmployee);

        Assertions.assertEquals(result,newEmployee);

        Mockito.verify(employeeRepository).save(newEmployee);






    }
}