package com.hibernateTest.hibernateTest.service.defoultImp;

import com.hibernateTest.hibernateTest.model.Department;
import com.hibernateTest.hibernateTest.repository.DepartmentRepository;
import com.hibernateTest.hibernateTest.service.DepartmentService;
import com.hibernateTest.hibernateTest.util.ConvertorIteratorToList;
import org.apache.log4j.Logger;
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
    private static Logger logger = Logger.getLogger(DefoultDepartmentService.class);

    @Override
    public List<Department> getAll() throws Exception {
        List<Department> departments = convertor.convert(repository.findAll());
        if(departments == null || departments.size() <= 0){
            logger.error("Table Department is empty");
            throw new Exception("Table Department is empty.");
        }
        return departments;
    }

    @Override
    public Department getById(int id) throws Exception {
        Optional<Department> department = repository.findById(id);
        if(!department.isPresent()){
            logger.error("Department with appointed id does not exist.");
            throw new Exception("Department with appointed id does not exist.");
        }
        return department.orElse(null);
    }

    @Override
    public Department add(Department department) {
        if(department == null){
            logger.error("Department is null.");
            throw new NullPointerException("Department is null.");
        }
        return repository.save(department);
    }

    @Override
    public Department update(Department department) throws Exception {
        if(getById(department.getId()) == null){
            logger.error("The department which is wanted update does not exist.");
            throw new NullPointerException("The department which is wanted update does not exist.");
        }
        return repository.save(department);
    }

    @Override
    public void delete(int id) throws Exception {
        if(getById(id) == null){
            logger.error("The department which is wanted delete does not exist.");
            throw new NullPointerException("The department which is wanted delete does not exist.");
        }
        repository.delete(getById(id));
    }
}
