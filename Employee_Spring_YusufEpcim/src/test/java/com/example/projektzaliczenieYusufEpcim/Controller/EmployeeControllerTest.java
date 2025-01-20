package com.example.projektzaliczenieYusufEpcim.Controller;

import com.example.projektzaliczenieYusufEpcim.Model.Employee;
import com.example.projektzaliczenieYusufEpcim.Repository.EmployeeRepository;
import com.example.projektzaliczenieYusufEpcim.Service.EmployeeService;

import org.h2.engine.Database;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.ArrayList;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmployeeController.class)
@ExtendWith(MockitoExtension.class)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private EmployeeService employeeService;
    @MockBean
    private EmployeeRepository employeeRepository;


    @BeforeEach
    void setUp() {


    }


    @Test
    void createEmployee() {


    }

    @Test
    void getAllEmployee(){

    }

    @Test
    void deleteEmployee() {
    }

    @Test
    void getEmployeeById() {

    }

    @Test
    void updateEmployee() {
    }





    }
