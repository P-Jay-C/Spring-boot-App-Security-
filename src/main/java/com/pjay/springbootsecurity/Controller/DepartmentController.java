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
@RequestMapping("/department")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;    

    private final Logger logger = LoggerFactory.getLogger(Department.class);

    @PostMapping("/add")
    public Department saveDepartment(@Valid @RequestBody Department department){

        logger.info("Inside save department of department controller");
        return departmentService.saveDepartment(department);
    }

    @GetMapping("/getAll")
    public List<Department> fetchDepartmentList(){
        return departmentService.fetchDepartmentList();
    }

    @GetMapping("/{id}")
    public Optional<Department> fetchByDepartmentById(@PathVariable("id") Long departmentId) throws DepartmentNotFoundException {
        return departmentService.fetchByDepartmentId(departmentId);
    }

    @PostMapping("/{id}")
    public Department updateDepartment(@PathVariable("id") Long departmentId, @RequestBody Department department) throws DepartmentNotFoundException {

        return departmentService.updateDepartment(departmentId,department);
        
    }

    @GetMapping("/name/{name}")
    public Department fetchByDepartmentName(@PathVariable("name") String name){
        return departmentService.fetchByDepartmentName(name);
    }

    
}
