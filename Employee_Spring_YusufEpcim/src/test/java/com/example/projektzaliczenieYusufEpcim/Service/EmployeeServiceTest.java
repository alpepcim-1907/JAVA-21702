package com.example.projektzaliczenieYusufEpcim.Service;

import com.example.projektzaliczenieYusufEpcim.Model.Employee;
import com.example.projektzaliczenieYusufEpcim.Repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    private EmployeeService employeeService;

    private EmployeeRepository employeeRepository;

    private Employee employee;

    @BeforeEach
    void setUp() {
        employeeRepository = mock(EmployeeRepository.class);
        employeeService = new EmployeeService(employeeRepository);

        employee = new Employee(1L, "Yusuf", "Surname", "aaa@aaaa", "Ankara", 123123, "IT", 123123.0);
    }

    @Test
    void createEmployee() {
        when(employeeRepository.save(employee)).thenReturn(employee);

        Employee result = employeeService.createEmployee(employee);

        assertEquals(employee, result);
        verify(employeeRepository).save(employee);
    }

    @Test
    void getAllEmployee() {
        when(employeeRepository.findAll()).thenReturn(Arrays.asList(employee));

        List<Employee> result = employeeService.getAllEmployee();

        assertEquals(1, result.size());
        assertEquals(employee, result.get(0));
        verify(employeeRepository).findAll();
    }

    @Test
    void getAllEmployeesWhenEmpty() {
        when(employeeRepository.findAll()).thenReturn(Collections.emptyList());

        List<Employee> result = employeeService.getAllEmployee();

        assertTrue(result.isEmpty());
        verify(employeeRepository).findAll();
    }

    @Test
    void getEmployeeById() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        Employee result = employeeService.getEmployeeById(1L);

        assertEquals(employee, result);
        verify(employeeRepository).findById(1L);
    }

    @Test
    void getEmployeeByNonExistentId() {
        when(employeeRepository.findById(999L)).thenReturn(Optional.empty());

        Employee result = employeeService.getEmployeeById(999L);

        assertNotNull(result);
        assertNull(result.getFirstName());
        verify(employeeRepository).findById(999L);
    }

    @Test
    void deleteEmployeeById() {
        doNothing().when(employeeRepository).deleteById(1L);

        employeeService.deleteEmployeeById(1L);

        verify(employeeRepository).deleteById(1L);
    }

    @Test
    void deleteNonExistentEmployee() {
        doThrow(new RuntimeException("Employee not found")).when(employeeRepository).deleteById(999L);

        assertThrows(RuntimeException.class, () -> employeeService.deleteEmployeeById(999L));

        verify(employeeRepository).deleteById(999L);
    }

    @Test
    void updateById() {
        Employee updatedEmployee = new Employee(1L, "Updated", "UpdatedSurname", "updated@aaaa", "Istanbul", 456456, "HR", 200000.0);

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(employeeRepository.save(any(Employee.class))).thenReturn(updatedEmployee);

        Employee result = employeeService.updateById(1L, updatedEmployee);

        assertEquals("Updated", result.getFirstName());
        assertEquals("UpdatedSurname", result.getLastName());
        assertEquals("updated@aaaa", result.getEmail());
        verify(employeeRepository).findById(1L);
        verify(employeeRepository).save(any(Employee.class));
    }

    @Test
    void updateNonExistentEmployee() {
        Employee updatedEmployee = new Employee(999L, "NonExistent", "Not Found", "nonexistent@aaaa", "Nowhere", 0, "None", 0);

        when(employeeRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> employeeService.updateById(999L, updatedEmployee));

        verify(employeeRepository).findById(999L);
    }

    @Test
    void createEmployeeWithNullValues() {
        Employee invalidEmployee = new Employee(null, null, null, "null@domain.com", "Null City", 0, null, 0);

        when(employeeRepository.save(invalidEmployee)).thenReturn(invalidEmployee);

        Employee result = employeeService.createEmployee(invalidEmployee);

        assertNull(result.getFirstName());
        assertEquals("null@domain.com", result.getEmail());
        verify(employeeRepository).save(invalidEmployee);
    }

    @Test
    void getAllEmployeesWithMultipleEntries() {
        Employee employee2 = new Employee(2L, "Alice", "Smith", "alice@example.com", "CityA", 456456, "Finance", 70000.0);
        Employee employee3 = new Employee(3L, "Bob", "Johnson", "bob@example.com", "CityB", 789789, "HR", 80000.0);

        when(employeeRepository.findAll()).thenReturn(Arrays.asList(employee, employee2, employee3));

        List<Employee> result = employeeService.getAllEmployee();

        assertEquals(3, result.size());
        assertTrue(result.contains(employee));
        assertTrue(result.contains(employee2));
        assertTrue(result.contains(employee3));
        verify(employeeRepository).findAll();
    }

    @Test
    void updateEmployeeWithPartialFields() {
        Employee partialUpdate = new Employee();
        partialUpdate.setFirstName("Partial Update");

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        Employee result = employeeService.updateById(1L, partialUpdate);

        assertEquals("Partial Update", result.getFirstName());
        assertEquals("Surname", result.getLastName());
        verify(employeeRepository).findById(1L);
        verify(employeeRepository).save(any(Employee.class));
    }

    @Test
    void handleLargeNumberOfEmployees() {
        List<Employee> largeEmployeeList = Collections.nCopies(1000, employee);

        when(employeeRepository.findAll()).thenReturn(largeEmployeeList);

        List<Employee> result = employeeService.getAllEmployee();

        assertEquals(1000, result.size());
        verify(employeeRepository).findAll();
    }
}
