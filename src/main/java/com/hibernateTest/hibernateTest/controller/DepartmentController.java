package com.hibernateTest.hibernateTest.controller;

import com.hibernateTest.hibernateTest.model.Department;
import com.hibernateTest.hibernateTest.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/deps")
public class DepartmentController {

    @Autowired
    @Qualifier("defoultDepartmentService")
    private DepartmentService service;

    @GetMapping(path = "/all")
    public ResponseEntity<List<Department>> getAll(){
        List<Department> departments = service.getAll();
        if (departments.size() > 0){
            return ResponseEntity.status(HttpStatus.OK).body(departments);
        }else{
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping(path = "/add")
    public ResponseEntity<Department> add(@Valid Department dep){
        try {
            Department department = service.add(dep);
            return ResponseEntity.status(HttpStatus.OK).body(department);
        }catch (Exception ex){}
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @PutMapping(path = "/update")
    public ResponseEntity<Department> update(@Valid Department dep){
        try {
            Department department = service.update(dep);
            return ResponseEntity.status(HttpStatus.OK).body(department);
        }catch (Exception ex){}
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @PostMapping(path = "/delete")
    public ResponseEntity delete(int id){
        service.delete(id);
        return ResponseEntity.ok(null);
    }
}
