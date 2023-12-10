package com.pjay.springbootsecurity.Repository;

import com.pjay.springbootsecurity.Model.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

    public Department findByDepartmentName(String name);
    public Department findByDepartmentNameIgnoreCase(String name);
}
