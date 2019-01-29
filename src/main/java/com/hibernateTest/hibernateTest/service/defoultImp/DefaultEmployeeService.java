package com.hibernateTest.hibernateTest.service.defoultImp;

import com.hibernateTest.hibernateTest.model.Employee;
import com.hibernateTest.hibernateTest.repository.EmployeeRepository;
import com.hibernateTest.hibernateTest.service.EmployeeService;
import com.hibernateTest.hibernateTest.util.ConvertorIteratorToList;
import com.hibernateTest.hibernateTest.util.MyDateTimeFormatter;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service("defoultEmployeeService")
public class DefaultEmployeeService implements EmployeeService {

    @Autowired
    @Qualifier("employeeRepo")
    private EmployeeRepository repository;
    private ConvertorIteratorToList convertor = new ConvertorIteratorToList();
    private static Logger logger = Logger.getLogger(DefaultEmployeeService.class);

    @Override
    public List<Employee> getAll() {
        List<Employee> employees = convertor.convert(repository.findAll());
        if(employees.size() <= 0){
            logger.error("Table Employee is empty.");
        }
        return employees;
    }

    @Override
    public Employee getById(int id) {
        Optional<Employee> employee = repository.findById(id);
        if (!employee.isPresent()){
            logger.error("Employee with appointed id does not exist.");
        }
        return employee.orElse(new Employee());
    }

    @Override
    public Employee add(Employee employee) {
        if(employee == null){
            logger.error("Employee is null.");
            return new Employee();
        }else {
            employee.setDateAdded(MyDateTimeFormatter.format(LocalDateTime.now()));
            employee.setDateChange(MyDateTimeFormatter.format(LocalDateTime.now()));
            employee.setVersion(1);
            return repository.save(employee);
        }
    }

    @Override
    public Employee update(Employee employee) {
        Employee employeeForCheck = getById(employee.getId());
        if(employeeForCheck == null){
            logger.error("The employee which is wanted update does not exist.");
            return new Employee();
        }else{
            employee.setDateChange(MyDateTimeFormatter.format(LocalDateTime.now()));
            employee.setDateAdded(MyDateTimeFormatter.format(employeeForCheck.getDateAdded()));
            employee.setVersion(employeeForCheck.getVersion() + 1);
            return repository.save(employee);
        }
    }

    @Override
    public boolean delete(int id ) {
        Employee employee = getById(id);
        if(employee == null){
            logger.error("The employee which is wanted delete does not exist.");
            return false;
        }else{
            repository.delete(employee);
            return true;
        }
    }
}
