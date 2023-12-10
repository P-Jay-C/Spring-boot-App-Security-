package com.pjay.springbootsecurity.Service;

import com.pjay.springbootsecurity.Error.DepartmentNotFoundException;
import com.pjay.springbootsecurity.Model.Department;
import com.pjay.springbootsecurity.Repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;
    @Override
    public Department saveDepartment(Department department) {
        return departmentRepository.save(department);
    }

    @Override
    public List<Department> fetchDepartmentList() {
        return departmentRepository.findAll();
    }

    @Override
    public Optional<Department> fetchByDepartmentById(Long departmentId) throws DepartmentNotFoundException {
        var department = departmentRepository.findById(departmentId);

        if (!department.isPresent()) {
            throw new DepartmentNotFoundException("Department not found");
        }
    }

    @Override
    public Department updateDepartment(Long departmentId, Department department) throws DepartmentNotFoundException {
        
        
        Department dep = fetchByDepartmentById(departmentId).get();

        if(dep != null){
            dep.setDepartmentName(department.getDepartmentName());
            dep.setDepartmentAddress(department.getDepartmentAddress());
            dep.setDepartmentCode(department.getDepartmentCode());
        }

        departmentRepository.save(dep);
        return dep;
        
    }

    @Override
    public Department findByDepartmentName(String name) {
        return departmentRepository.findByDepartmentName(name);
    }

    

}
