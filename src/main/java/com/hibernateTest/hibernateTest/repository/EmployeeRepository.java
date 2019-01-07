package com.hibernateTest.hibernateTest.repository;

import com.hibernateTest.hibernateTest.model.Employee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("employeeRepo")
public interface EmployeeRepository extends CrudRepository<Employee, Integer> {}
