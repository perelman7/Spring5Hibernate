package com.hibernateTest.hibernateTest.controller;

import com.hibernateTest.hibernateTest.model.Employee;
import com.hibernateTest.hibernateTest.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/emps")
public class EmployeeController {

    @Autowired
    @Qualifier("defoultEmployeeService")
    private EmployeeService service;

    @GetMapping(path = "/all")
    public ResponseEntity<List<Employee>> getAll(){
        List<Employee> employees = service.getAll();
        if(employees.size() > 0){
            return ResponseEntity.status(HttpStatus.OK).body(employees);
        }else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping(path = "/add")
    public ResponseEntity<Employee> add(@Valid Employee emp){
        try {
            Employee employee = service.add(emp);
            return ResponseEntity.status(HttpStatus.OK).body(employee);
        }catch (Exception ex){}
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @PutMapping(path = "/update")
    public ResponseEntity<Employee> update(@Valid Employee emp){
        try {
            Employee employee = service.update(emp);
            return ResponseEntity.status(HttpStatus.OK).body(employee);
        }catch (Exception ex){}
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @PostMapping(path = "/delete")
    public ResponseEntity delete(int id){
        service.delete(id);
        return ResponseEntity.ok(null);
    }
}
