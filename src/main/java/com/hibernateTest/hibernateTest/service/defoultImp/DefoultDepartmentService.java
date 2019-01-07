package com.hibernateTest.hibernateTest.service.defoultImp;

import com.hibernateTest.hibernateTest.model.Department;
import com.hibernateTest.hibernateTest.repository.DepartmentRepository;
import com.hibernateTest.hibernateTest.service.DepartmentService;
import com.hibernateTest.hibernateTest.util.ConvertorIteratorToList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("defoultDepartmentService")
public class DefoultDepartmentService implements DepartmentService {

    @Autowired
    @Qualifier("departmentRepos")
    private DepartmentRepository repository;

    private ConvertorIteratorToList<Department> convertor = new ConvertorIteratorToList<>();

    @Override
    public List<Department> getAll() {
        return convertor.convert(repository.findAll());
    }

    @Override
    public Department getById(int id) {
        Optional<Department> dep = repository.findById(id);
        return dep.orElse(null);
    }

    @Override
    public Department add(Department department) {
        return repository.save(department);
    }

    @Override
    public Department update(Department department) {
        return repository.save(department);
    }

    @Override
    public void delete(int id) {
        repository.delete(getById(id));
    }
}
