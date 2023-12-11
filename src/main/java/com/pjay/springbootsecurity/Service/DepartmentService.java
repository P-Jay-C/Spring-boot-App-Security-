package com.pjay.springbootsecurity.Service;

import com.pjay.springbootsecurity.Error.DepartmentNotFoundException;
import com.pjay.springbootsecurity.Model.Department;

import java.util.List;
import java.util.Optional;

public interface DepartmentService {
    Department saveDepartment(Department department);

    List<Department> fetchDepartmentList();

    Optional<Department> fetchByDepartmentId(Long departmentId) throws DepartmentNotFoundException;

    Department updateDepartment(Long departmentId, Department department) throws DepartmentNotFoundException;

    Department fetchByDepartmentName(String name);
}
