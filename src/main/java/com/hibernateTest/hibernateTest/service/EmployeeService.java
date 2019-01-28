package com.hibernateTest.hibernateTest.service;

import com.hibernateTest.hibernateTest.model.Employee;
import java.util.List;

public interface EmployeeService {

    List<Employee> getAll() throws Exception;
    Employee getById(int id) throws Exception;
    Employee add(Employee employee);
    Employee update(Employee employee) throws Exception;
    void delete(int id) throws Exception;
}
