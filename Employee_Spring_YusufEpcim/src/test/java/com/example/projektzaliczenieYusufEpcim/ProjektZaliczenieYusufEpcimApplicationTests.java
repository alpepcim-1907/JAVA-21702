package com.example.projektzaliczenieYusufEpcim;

import com.example.projektzaliczenieYusufEpcim.Model.Employee;
import com.example.projektzaliczenieYusufEpcim.Repository.EmployeeRepository;
import com.example.projektzaliczenieYusufEpcim.Service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProjektZaliczenieYusufEpcimApplicationTests {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeService employeeService;

    private Employee testEmployee;

    @BeforeEach
    void setUp() {
        testEmployee = new Employee();
        testEmployee.setFirstName("Test");
        testEmployee.setLastName("User");
        testEmployee.setEmail("test@example.com");
        testEmployee.setCity("Test City");
        testEmployee.setPhone(123456789);
        testEmployee.setDepartment("HR");
        testEmployee.setSalary(50000.0);

        employeeRepository.deleteAll();
    }

    @Test
    void contextLoads() {
        assertNotNull(employeeRepository);
        assertNotNull(employeeService);
    }

    @Test
    void createEmployee() {
        Employee savedEmployee = employeeService.createEmployee(testEmployee);
        assertNotNull(savedEmployee.getId());
        assertEquals(testEmployee.getFirstName(), savedEmployee.getFirstName());
        assertEquals(testEmployee.getEmail(), savedEmployee.getEmail());
    }

    @Test
    void getAllEmployee() {
        employeeService.createEmployee(testEmployee);
        List<Employee> employees = employeeService.getAllEmployee();
        assertFalse(employees.isEmpty());
        assertEquals(1, employees.size());
        assertEquals(testEmployee.getFirstName(), employees.get(0).getFirstName());
    }

    @Test
    void getEmployeeById() {
        Employee savedEmployee = employeeService.createEmployee(testEmployee);
        Employee foundEmployee = employeeService.getEmployeeById(savedEmployee.getId());
        assertNotNull(foundEmployee);
        assertEquals(savedEmployee.getId(), foundEmployee.getId());
        assertEquals(testEmployee.getCity(), foundEmployee.getCity());
    }

    @Test
    void updateEmployee() {
        Employee savedEmployee = employeeService.createEmployee(testEmployee);
        savedEmployee.setFirstName("Updated Name");
        savedEmployee.setCity("Updated City");
        Employee updatedEmployee = employeeService.updateById(savedEmployee.getId(), savedEmployee);
        assertEquals("Updated Name", updatedEmployee.getFirstName());
        assertEquals("Updated City", updatedEmployee.getCity());
    }

    @Test
    void deleteEmployee() {
        Employee savedEmployee = employeeService.createEmployee(testEmployee);
        employeeService.deleteEmployeeById(savedEmployee.getId());
        Optional<Employee> deletedEmployee = employeeRepository.findById(savedEmployee.getId());
        assertTrue(deletedEmployee.isEmpty());
    }

    @Test
    void repositoryCRUDOperations() {
        Employee savedEmployee = employeeRepository.save(testEmployee);
        assertNotNull(savedEmployee.getId());
        Optional<Employee> retrievedEmployee = employeeRepository.findById(savedEmployee.getId());
        assertTrue(retrievedEmployee.isPresent());
        assertEquals(savedEmployee.getFirstName(), retrievedEmployee.get().getFirstName());
        retrievedEmployee.get().setSalary(60000.0);
        Employee updatedEmployee = employeeRepository.save(retrievedEmployee.get());
        assertEquals(60000.0, updatedEmployee.getSalary());
        employeeRepository.delete(updatedEmployee);
        Optional<Employee> deletedEmployee = employeeRepository.findById(updatedEmployee.getId());
        assertTrue(deletedEmployee.isEmpty());
    }

    @Test
    void testGetEmployeeByNonExistentId() {
        Optional<Employee> nonExistentEmployee = employeeRepository.findById(999L);
        assertTrue(nonExistentEmployee.isEmpty());
    }

    @Test
    void testCreateEmployeeWithNullValues() {
        Employee invalidEmployee = new Employee();
        invalidEmployee.setFirstName(null);
        invalidEmployee.setLastName(null);
        invalidEmployee.setEmail("test@domain.com");
        invalidEmployee.setCity("Somewhere");
        invalidEmployee.setPhone(123456789);
        invalidEmployee.setDepartment("Engineering");
        invalidEmployee.setSalary(40000.0);
        Employee result = employeeRepository.save(invalidEmployee);
        assertNotNull(result.getId());
        assertNull(result.getFirstName());
        assertNull(result.getLastName());
    }

    @Test
    void testUpdateNonExistentEmployee() {
        Employee nonExistentEmployee = new Employee();
        nonExistentEmployee.setId(999L);
        nonExistentEmployee.setFirstName("NonExistent");
        nonExistentEmployee.setCity("Unknown City");
        assertThrows(Exception.class, () -> employeeService.updateById(999L, nonExistentEmployee));
    }

    @Test
    void testDeleteNonExistentEmployee() {
        assertThrows(Exception.class, () -> employeeService.deleteEmployeeById(999L));
    }

    @Test
    void testFindAllOnEmptyRepository() {
        List<Employee> employees = employeeRepository.findAll();
        assertTrue(employees.isEmpty());
    }

    @Test
    void testMultipleEmployeeInsertion() {
        Employee employee1 = new Employee(null, "Alice", "Smith", "alice@domain.com", "City1", 123456, "Finance", 70000.0);
        Employee employee2 = new Employee(null, "Bob", "Johnson", "bob@domain.com", "City2", 987654, "IT", 80000.0);
        employeeRepository.save(employee1);
        employeeRepository.save(employee2);
        List<Employee> employees = employeeRepository.findAll();
        assertEquals(2, employees.size());
    }
}
