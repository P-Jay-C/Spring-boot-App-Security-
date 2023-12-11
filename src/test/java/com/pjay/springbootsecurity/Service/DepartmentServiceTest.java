package com.pjay.springbootsecurity.Service;

import com.pjay.springbootsecurity.Model.Department;
import com.pjay.springbootsecurity.Repository.DepartmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class DepartmentServiceTest {

    @Autowired
    private DepartmentService departmentService;

    @MockBean
    private DepartmentRepository departmentRepository;
    @BeforeEach
    void setUp() {
            Department department = Department.builder()
                    .departmentName("IT")
                    .departmentAddress("Accra")
                    .departmentCode("IT-01")
                    .departmentId(1L)
                    .build();

        Mockito.when(departmentRepository.findByDepartmentNameIgnoreCase("IT"))
                .thenReturn(department);
    }


    @Test
    @DisplayName("Get Department By Name")
    public  void whenValidDepartmentName_thenDepartmentShouldFound(){
            String departmentName = "IT";

        Department found = departmentService.fetchByDepartmentName(departmentName);
        assertEquals(departmentName,found.getDepartmentName());

    }
}