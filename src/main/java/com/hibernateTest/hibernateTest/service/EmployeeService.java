package com.hibernateTest.hibernateTest.service;

import com.hibernateTest.hibernateTest.model.Employee;
import java.util.List;

public interface EmployeeService {

    List<Employee> getAll();
    Employee getById(int id);
    Employee add(Employee employee);
    Employee update(Employee employee);
    boolean delete(int id);
}
