package com.hibernateTest.hibernateTest.service.defoultImp;

import com.hibernateTest.hibernateTest.model.Employee;
import com.hibernateTest.hibernateTest.repository.EmployeeRepository;
import com.hibernateTest.hibernateTest.service.EmployeeService;
import com.hibernateTest.hibernateTest.util.ConvertorIteratorToList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("defoultEmployeeService")
public class DefoultEmployeeService implements EmployeeService {

    @Autowired
    @Qualifier("employeeRepo")
    private EmployeeRepository repository;

    private ConvertorIteratorToList convertor = new ConvertorIteratorToList();

    @Override
    public List<Employee> getAll() {
        return convertor.convert(repository.findAll());
    }

    @Override
    public Employee getById(int id) {
        Optional<Employee> employee = repository.findById(id);
        return employee.orElse(null);
    }

    @Override
    public Employee add(Employee employee) {
        return repository.save(employee);
    }

    @Override
    public Employee update(Employee employee) {
        return repository.save(employee);
    }

    @Override
    public void delete(int id ) {
        repository.delete(getById(id));
    }
}
