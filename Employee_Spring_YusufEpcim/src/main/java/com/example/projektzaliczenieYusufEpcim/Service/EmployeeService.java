package com.example.projektzaliczenieYusufEpcim.Service;

import com.example.projektzaliczenieYusufEpcim.Model.Employee;
import com.example.projektzaliczenieYusufEpcim.Repository.EmployeeRepository;
import org.springframework.stereotype.Service;


import java.util.List;



@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository){

        this.employeeRepository = employeeRepository;
    }




    public Employee createEmployee(Employee employee) {

        return employeeRepository.save(employee);
    }

    public List<Employee> getAllEmployee() {

        return employeeRepository.findAll();
    }

    public void deleteEmployeeById(Long id) {

        employeeRepository.deleteById(id);

    }

    public Employee getEmployeeById(Long id) {

        return employeeRepository.findById(id).orElse(new Employee());
    }

    public Employee updateById(Long id, Employee employee) {
      Employee repoEmployee = employeeRepository.findById(id).orElseThrow();

      repoEmployee.setFirstName(employee.getFirstName());
      repoEmployee.setLastName(employee.getLastName());
      repoEmployee.setCity(employee.getCity());
      repoEmployee.setPhone(employee.getPhone());
      repoEmployee.setEmail(employee.getEmail());
      repoEmployee.setSalary(employee.getSalary());
      repoEmployee.setDepartment(employee.getDepartment());

      return employeeRepository.save(repoEmployee);

    }



}
