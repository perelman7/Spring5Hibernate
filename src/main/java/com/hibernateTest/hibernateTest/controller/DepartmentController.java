package com.hibernateTest.hibernateTest.controller;

import com.hibernateTest.hibernateTest.model.Department;
import com.hibernateTest.hibernateTest.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/deps")
public class DepartmentController {

    @Autowired
    private DepartmentRepository repository;

    @GetMapping(path = "/add")
    public String addNewDepartment(@RequestParam String name, @RequestParam String description){
        try {
            repository.save(new Department(name, description));
        }catch (Exception ex){}

        return "Saved";
    }

    @GetMapping(path = "/all")
    public Iterable<Department> getAllDepartments(){
        return repository.findAll();
    }

    @GetMapping(path = "/test")
    public String test(){return "test";}
}
