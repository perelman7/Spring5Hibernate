package com.hibernateTest.hibernateTest.service.defoultImp;

import com.hibernateTest.hibernateTest.model.Department;
import com.hibernateTest.hibernateTest.repository.DepartmentRepository;
import com.hibernateTest.hibernateTest.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class DefoultDepartmentService implements DepartmentService {

    @Autowired
    @Qualifier("departmentRepository")
    private DepartmentRepository repository;

    @Override
    public Iterable<Department> getAll() {
        return repository.findAll();
    }

    @Override
    public Department getById(int id) {
        return repository.findById(id);
    }

    @Override
    public Department add(Department department) {
        return null;
    }

    @Override
    public Department update(Department department) {
        return null;
    }

    @Override
    public void delete(Department department) {

    }
}
