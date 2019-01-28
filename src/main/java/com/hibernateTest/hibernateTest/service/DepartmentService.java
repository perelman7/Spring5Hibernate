package com.hibernateTest.hibernateTest.service;

import com.hibernateTest.hibernateTest.model.Department;
import java.util.List;

public interface DepartmentService {

    List<Department> getAll() throws Exception;
    Department getById(int id) throws Exception;
    Department add(Department department);
    Department update(Department department) throws Exception;
    void delete(int id) throws Exception;
}
