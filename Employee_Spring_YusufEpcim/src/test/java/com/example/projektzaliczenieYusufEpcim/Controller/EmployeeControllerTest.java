package com.example.projektzaliczenieYusufEpcim.Controller;

import com.example.projektzaliczenieYusufEpcim.Model.Employee;
import com.example.projektzaliczenieYusufEpcim.Service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmployeeController.class)
@ExtendWith(MockitoExtension.class)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    private Employee testEmployee;

    @BeforeEach
    void setUp() {
        testEmployee = new Employee(1L, "Yusuf", "Surname", "aaa@aaaa", "Ankara", 123123, "IT", 123123.0);
    }

    @Test
    void createEmployee() throws Exception {
        when(employeeService.createEmployee(Mockito.any(Employee.class))).thenReturn(testEmployee);

        mockMvc.perform(post("/api/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\":\"Yusuf\",\"lastName\":\"Surname\",\"email\":\"aaa@aaaa\",\"city\":\"Ankara\",\"phone\":123123,\"department\":\"IT\",\"salary\":123123.0}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Yusuf"))
                .andExpect(jsonPath("$.email").value("aaa@aaaa"));

        verify(employeeService).createEmployee(Mockito.any(Employee.class));
    }

    @Test
    void createEmployeeWithInvalidData() throws Exception {
        mockMvc.perform(post("/api/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\":\"\",\"lastName\":\"\",\"email\":\"invalid\",\"city\":\"\",\"phone\":0,\"department\":\"\",\"salary\":0}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getAllEmployee() throws Exception {
        when(employeeService.getAllEmployee()).thenReturn(Collections.singletonList(testEmployee));

        mockMvc.perform(get("/api/get")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("Yusuf"))
                .andExpect(jsonPath("$[0].email").value("aaa@aaaa"));

        verify(employeeService).getAllEmployee();
    }

    @Test
    void getAllEmployeeWhenEmpty() throws Exception {
        when(employeeService.getAllEmployee()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/get")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));

        verify(employeeService).getAllEmployee();
    }

    @Test
    void getEmployeeById() throws Exception {
        when(employeeService.getEmployeeById(1L)).thenReturn(testEmployee);

        mockMvc.perform(get("/api/employee/get/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Yusuf"))
                .andExpect(jsonPath("$.email").value("aaa@aaaa"));

        verify(employeeService).getEmployeeById(1L);
    }

    @Test
    void getEmployeeByNonExistentId() throws Exception {
        when(employeeService.getEmployeeById(999L)).thenReturn(null);

        mockMvc.perform(get("/api/employee/get/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(employeeService).getEmployeeById(999L);
    }

    @Test
    void deleteEmployee() throws Exception {
        doNothing().when(employeeService).deleteEmployeeById(1L);

        mockMvc.perform(delete("/api/delete/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(employeeService).deleteEmployeeById(1L);
    }

    @Test
    void deleteNonExistentEmployee() throws Exception {
        doThrow(new RuntimeException("Employee not found")).when(employeeService).deleteEmployeeById(999L);

        mockMvc.perform(delete("/api/delete/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(employeeService).deleteEmployeeById(999L);
    }

    @Test
    void updateEmployee() throws Exception {
        Employee updatedEmployee = new Employee(1L, "Updated", "UpdatedSurname", "updated@aaaa", "Istanbul", 456456, "HR", 200000.0);
        when(employeeService.updateById(eq(1L), Mockito.any(Employee.class))).thenReturn(updatedEmployee);

        mockMvc.perform(put("/api/employee/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\":\"Updated\",\"lastName\":\"UpdatedSurname\",\"email\":\"updated@aaaa\",\"city\":\"Istanbul\",\"phone\":456456,\"department\":\"HR\",\"salary\":200000.0}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Updated"))
                .andExpect(jsonPath("$.email").value("updated@aaaa"));

        verify(employeeService).updateById(eq(1L), Mockito.any(Employee.class));
    }

    @Test
    void updateNonExistentEmployee() throws Exception {
        when(employeeService.updateById(eq(999L), Mockito.any(Employee.class))).thenThrow(new RuntimeException("Employee not found"));

        mockMvc.perform(put("/api/employee/update/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\":\"NonExistent\",\"lastName\":\"NotFound\",\"email\":\"nonexistent@aaaa\",\"city\":\"Nowhere\",\"phone\":0,\"department\":\"None\",\"salary\":0}"))
                .andExpect(status().isNotFound());

        verify(employeeService).updateById(eq(999L), Mockito.any(Employee.class));
    }

    @Test
    void getEmployeesInDifferentFormats() throws Exception {
        when(employeeService.getAllEmployee()).thenReturn(Collections.singletonList(testEmployee));

        mockMvc.perform(get("/api/get")
                        .accept(MediaType.APPLICATION_XML))
                .andExpect(status().isOk())
                .andExpect(xpath("/List/item/firstName").string("Yusuf"))
                .andExpect(xpath("/List/item/email").string("aaa@aaaa"));

        mockMvc.perform(get("/api/get")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("Yusuf"))
                .andExpect(jsonPath("$[0].email").value("aaa@aaaa"));

        verify(employeeService, times(2)).getAllEmployee();
    }
}
