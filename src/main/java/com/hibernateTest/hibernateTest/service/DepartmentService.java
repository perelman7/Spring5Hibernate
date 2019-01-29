package com.hibernateTest.hibernateTest.service;

import com.hibernateTest.hibernateTest.model.Department;
import java.util.List;

public interface DepartmentService {

    List<Department> getAll();
    Department getById(int id);
    Department add(Department department);
    Department update(Department department);
    boolean delete(int id);
}
