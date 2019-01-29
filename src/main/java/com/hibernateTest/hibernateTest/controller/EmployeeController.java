package com.hibernateTest.hibernateTest.controller;

import com.hibernateTest.hibernateTest.model.Employee;
import com.hibernateTest.hibernateTest.service.EmployeeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/emps")
@Api(value="employee_controller", description="execute restful requests with table Employees")
public class EmployeeController {

    @Autowired
    @Qualifier("defoultEmployeeService")
    private EmployeeService service;
    private static Logger logger = Logger.getLogger(EmployeeController.class);

    @GetMapping(path = "/all")
    @ApiOperation(value = "return list of employees", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully returned list employees"),
            @ApiResponse(code = 401, message = "You are not authorized to execute this request"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<List<Employee>> getAll(){
        List<Employee> employees = service.getAll();
        if(employees.size() > 0){
            return ResponseEntity.status(HttpStatus.OK).body(employees);
        }else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping(path = "/add")
    @ApiOperation(value = "return employee who was added", response = Employee.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully returned added employee"),
            @ApiResponse(code = 400, message = "Uncorrected data was sent to the server"),
            @ApiResponse(code = 401, message = "You are not authorized to execute this request"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public ResponseEntity<Employee> add( @Valid Employee employeeJson){
        if(employeeJson != null){
            Employee employee = service.add(employeeJson);
            return ResponseEntity.status(HttpStatus.OK).body(employee);
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PutMapping(path = "/update")
    @ApiOperation(value = "return employee who was updated", response = Employee.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully returned updated employee"),
            @ApiResponse(code = 400, message = "Uncorrected data was sent to the server"),
            @ApiResponse(code = 401, message = "You are not authorized to execute this request"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public ResponseEntity<Employee> update(@Valid Employee emp){
        if(emp != null){
            Employee employee = service.update(emp);
            return ResponseEntity.status(HttpStatus.OK).body(employee);
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @DeleteMapping(path = "/delete")
    @ApiOperation(value = "delete employee")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully employee was deleted"),
            @ApiResponse(code = 400, message = "Uncorrected data was sent to the server"),
            @ApiResponse(code = 401, message = "You are not authorized to execute this request"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public ResponseEntity delete(@RequestParam Integer id){
        if(id != null) {
            service.delete(id);
            return ResponseEntity.ok(null);
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
