package com.hibernateTest.hibernateTest.service.defoultImp;

import com.hibernateTest.hibernateTest.model.Employee;
import com.hibernateTest.hibernateTest.repository.EmployeeRepository;
import com.hibernateTest.hibernateTest.service.EmployeeService;
import com.hibernateTest.hibernateTest.util.ConvertorIteratorToList;
import org.apache.log4j.Logger;
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
    private static Logger logger = Logger.getLogger(DefoultEmployeeService.class);

    @Override
    public List<Employee> getAll() throws Exception {
        List<Employee> employees = convertor.convert(repository.findAll());
        if(employees == null || employees.size() <= 0){
            logger.error("Table Employee is empty.");
            throw new Exception("Table Employee is empty.");
        }
        return employees;
    }

    @Override
    public Employee getById(int id) throws Exception {
        Optional<Employee> employee = repository.findById(id);
        if (!employee.isPresent()){
            logger.error("Employee with appointed id does not exist.");
            throw new Exception("Employee with appointed id does not exist.");
        }
        return employee.orElse(null);
    }

    @Override
    public Employee add(Employee employee) {
        if(employee == null){
            logger.error("Employee is null.");
            throw new NullPointerException("Employee is null.");
        }
        return repository.save(employee);
    }

    @Override
    public Employee update(Employee employee) throws Exception {
        if(getById(employee.getId()) == null){
            logger.error("The employee which is wanted update does not exist.");
            throw new NullPointerException("The employee which is wanted update does not exist.");
        }
        return repository.save(employee);
    }

    @Override
    public void delete(int id ) throws Exception {
        if(getById(id) == null){
            logger.error("The employee which is wanted delete does not exist.");
            throw new NullPointerException("The employee which is wanted delete does not exist.");
        }
        repository.delete(getById(id));
    }
}
