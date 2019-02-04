package com.hibernateTest.hibernateTest.controller;

import com.hibernateTest.hibernateTest.model.Department;
import com.hibernateTest.hibernateTest.service.DepartmentService;
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
@RequestMapping(path = "/deps")
@Api(value="department_controller", description="execute restful requests with table Departments")
public class DepartmentController {

    @Autowired
    @Qualifier("defoultDepartmentService")
    private DepartmentService service;
    private static Logger logger = Logger.getLogger(DepartmentController.class);

    @GetMapping(path = "/all")
    @ApiOperation(value = "return list of departments", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully returned list departments"),
            @ApiResponse(code = 401, message = "You are not authorized to execute this request"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public ResponseEntity<List<Department>> getAll(){
         List<Department> departments = service.getAll();
         if (departments.size() > 0){
            return ResponseEntity.status(HttpStatus.OK).body(departments);
         }else{
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
         }
    }

    @PostMapping(path = "/add")
    @ApiOperation(value = "return department which was added", response = Department.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully returned added department"),
            @ApiResponse(code = 401, message = "You are not authorized to execute this request"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public ResponseEntity<Department> add(@Valid Department department){
        if(department != null) {
            Department departmentResult = service.add(department);
            return ResponseEntity.status(HttpStatus.OK).body(departmentResult);
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PutMapping(path = "/update")
    @ApiOperation(value = "return department which was updated", response = Department.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully returned updated department"),
            @ApiResponse(code = 401, message = "You are not authorized to execute this request"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public ResponseEntity<Department> update(@Valid Department department){
        if(department != null) {
            Department departmentResult = service.update(department);
            return ResponseEntity.status(HttpStatus.OK).body(departmentResult);
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @DeleteMapping(path = "/delete")
    @ApiOperation(value = "delete department")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully department was deleted"),
            @ApiResponse(code = 401, message = "You are not authorized to execute this request"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public ResponseEntity delete(@RequestParam Integer id) throws Exception {
        if(id != null){
            service.delete(id);
            return ResponseEntity.ok(id);
        }else{
            return ResponseEntity.badRequest().body(null);
        }
    }
}
