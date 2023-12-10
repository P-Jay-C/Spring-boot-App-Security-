package com.pjay.springbootsecurity.Service;

import com.pjay.springbootsecurity.Model.Department;

import java.util.List;
import java.util.Optional;

public interface DepartmentService {
    Department saveDepartment(Department department);

    List<Department> fetchDepartmentList();

    Optional<Department> fetchByDepartmentById(Long departmentId);

    Department updateDepartment(Long departmentId, Department department);

    Department findByDepartmentName(String name);
}
