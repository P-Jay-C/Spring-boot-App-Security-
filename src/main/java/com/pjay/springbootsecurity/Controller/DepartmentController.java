package com.pjay.springbootsecurity.Controller;

import com.pjay.springbootsecurity.Error.DepartmentNotFoundException;
import com.pjay.springbootsecurity.Model.Department;
import com.pjay.springbootsecurity.Service.DepartmentService;

import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;    

    private final Logger logger = LoggerFactory.getLogger(Department.class);

    @PostMapping("/departments")
    public Department saveDepartment(@Valid @RequestBody Department department){

        logger.info("Inside save department of department controller");
        return departmentService.saveDepartment(department);
    }

    @GetMapping("/departments")
    public List<Department> fetchDepartmentList(){
        return departmentService.fetchDepartmentList();
    }

    @GetMapping("/department/{id}")
    public Optional<Department> fetchByDepartmentById(@PathVariable("id") Long departmentId) throws DepartmentNotFoundException {
        return departmentService.fetchByDepartmentById(departmentId);
    }

    @PostMapping("/department/{id}")
    public Department updateDepartment(@PathVariable("id") Long departmentId, @RequestBody Department department){

        return departmentService.updateDepartment(departmentId,department);
        
    }

    @GetMapping("department/name/{name}")
    public Department fetchByDepartmentName(@PathVariable("name") String name){
        return departmentService.findByDepartmentName(name);
    }

    
}
