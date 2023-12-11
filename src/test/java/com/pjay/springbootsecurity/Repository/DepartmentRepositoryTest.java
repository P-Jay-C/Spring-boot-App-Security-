package com.pjay.springbootsecurity.Repository;

import com.pjay.springbootsecurity.Model.Department;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class DepartmentRepositoryTest {

    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private TestEntityManager entityManager;

    private Long departmentId;

    @BeforeEach
    void setUp() {
        Department department = Department.builder()
                .departmentName("Mechanical Engineering")
                .departmentCode("ME-011")
                .departmentAddress("Delhi")
                .build();

        department = entityManager.persistFlushFind(department);
        departmentId = department.getDepartmentId(); // Assuming there's an getId() method
    }

    @Test
    public void whenFindById_thenReturnDepartment(){
        Optional<Department> optionalDepartment = departmentRepository.findById(departmentId);

        assertTrue(optionalDepartment.isPresent(), "Department not found");
        Department department = optionalDepartment.get();

        assertEquals("Mechanical Engineering", department.getDepartmentName());
        assertEquals("ME-011", department.getDepartmentCode());
        assertEquals("Delhi", department.getDepartmentAddress());
    }
}
