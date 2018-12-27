package com.hibernateTest.hibernateTest.service;

import com.hibernateTest.hibernateTest.model.Employee;

import java.util.List;

public interface EmployeeService {

    List<Employee> getAll();
    Employee getById();
    Employee add();
    Employee update();
    void delete();
}
