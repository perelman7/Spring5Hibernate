package com.hibernateTest.hibernateTest.service;

import com.hibernateTest.hibernateTest.model.Department;

public interface DepartmentService {

    Iterable<Department> getAll();
    Department getById(int id);
    Department add(Department department);
    Department update(Department department);
    void delete(Department department);
}
