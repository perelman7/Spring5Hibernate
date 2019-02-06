package com.hibernateTest.hibernateTest.service.modelsServicesImplementation;

import com.hibernateTest.hibernateTest.model.Department;
import com.hibernateTest.hibernateTest.repository.DepartmentRepository;
import com.hibernateTest.hibernateTest.service.DepartmentService;
import com.hibernateTest.hibernateTest.util.ConvertorIteratorToList;
import com.hibernateTest.hibernateTest.util.MyDateTimeFormatter;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service("defoultDepartmentService")
public class DefaultDepartmentService implements DepartmentService {

    @Autowired
    @Qualifier("departmentRepos")
    private DepartmentRepository repository;
    private ConvertorIteratorToList<Department> convertor = new ConvertorIteratorToList<>();
    private static Logger logger = Logger.getLogger(DefaultDepartmentService.class);

    @Override
    public List<Department> getAll() {
        List<Department> departments = convertor.convert(repository.findAll());
        if(departments.size() <= 0){
            logger.error("Table Department is empty");
        }
        return departments;
    }

    @Override
    public Department getById(int id) {
        Optional<Department> department = repository.findById(id);
        if(!department.isPresent()){
            logger.error("Department with appointed id does not exist.");
        }
        return department.orElse(null);
    }

    @Override
    public Department add(Department department) {
        if(department == null){
            logger.error("Department is null.");
            return new Department();
        }else {
            department.setDateAdded(MyDateTimeFormatter.format(LocalDateTime.now()));
            department.setDateChange(MyDateTimeFormatter.format(LocalDateTime.now()));
            department.setVersion(1);
            return repository.save(department);
        }
    }

    @Override
    public Department update(Department department) {
        Department departmentForCheck = getById(department.getId());
        if(departmentForCheck == null){
            logger.error("The department which is wanted update does not exist.");
            return new Department();
        }else {
            department.setDateChange(MyDateTimeFormatter.format(LocalDateTime.now()));
            department.setDateAdded(MyDateTimeFormatter.format(departmentForCheck.getDateAdded()));
            department.setVersion(departmentForCheck.getVersion() + 1);
            return repository.save(department);
        }
    }

    @Override
    public boolean delete(int id) {
        Department department = getById(id);
        if(department == null){
            logger.error("The department which is wanted delete does not exist.");
            return false;
        }else {
            repository.delete(department);
            return true;
        }
    }
}
