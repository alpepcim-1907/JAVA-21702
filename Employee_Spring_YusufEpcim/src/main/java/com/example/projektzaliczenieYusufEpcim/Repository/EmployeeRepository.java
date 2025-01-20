package com.example.projektzaliczenieYusufEpcim.Repository;

import com.example.projektzaliczenieYusufEpcim.Model.Employee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository


public interface EmployeeRepository  extends JpaRepository <Employee,Long> {



}

