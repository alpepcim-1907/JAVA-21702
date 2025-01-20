package com.example.projektzaliczenieYusufEpcim.Controller;



import com.example.projektzaliczenieYusufEpcim.Model.Employee;
import com.example.projektzaliczenieYusufEpcim.Repository.EmployeeRepository;
import com.example.projektzaliczenieYusufEpcim.Service.EmployeeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api")
public class EmployeeController {




    private final EmployeeService employeeService;

    public EmployeeController( EmployeeService employeeService) {

        this.employeeService = employeeService;
    }

    @PostMapping(path = "/save",
            consumes = {MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE}
            ,produces = {MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE})
    public Employee createEmployee(@RequestBody Employee employee){
        return employeeService.createEmployee(employee);

    }
    @GetMapping(path = "/get",produces = {MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE})
    public List<Employee> getAllEmployee(){

        return employeeService.getAllEmployee();
    }



    @DeleteMapping(path = "/delete/{id}",produces = {MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE})
    public void deleteEmployee (@PathVariable("id") long id){
        employeeService.deleteEmployeeById(id);
    }

    @GetMapping(path = "employee/get/{id}",produces = {MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE})
    public Employee getEmployeeById(@PathVariable("id") long id){
        return employeeService.getEmployeeById(id);
    }

    @PutMapping(path = "/employee/update/{id}",produces = {MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE})
    public Employee updateEmployee(@PathVariable long id, @RequestBody Employee employee){
        return employeeService.updateById(id,employee);
    }
}


