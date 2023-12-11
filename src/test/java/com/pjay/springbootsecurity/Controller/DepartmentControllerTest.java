package com.pjay.springbootsecurity.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pjay.springbootsecurity.Config.WebSecurityConfig;
import com.pjay.springbootsecurity.Model.Department;
import com.pjay.springbootsecurity.Service.DepartmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DepartmentController.class)
@Import(WebSecurityConfig.class)
class DepartmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DepartmentService departmentService;

    private Department department;

    @BeforeEach
    void setUp() {
        department = Department.builder()
                .departmentName("IT")
                .departmentAddress("Accra")
                .departmentCode("IT-01")
                .departmentId(1L)
                .build();
    }

    @Test
    void saveDepartment() throws Exception {
        Department inputDepartment = Department.builder()
                .departmentName("IT")
                .departmentAddress("Accra")
                .departmentCode("IT-01")
                .departmentId(1L)
                .build();

        Mockito.when(departmentService.saveDepartment(inputDepartment))
                .thenReturn(department);

        mockMvc.perform(MockMvcRequestBuilders.post("/department/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(inputDepartment)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.departmentName").value("IT"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.departmentAddress").value("Accra"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.departmentCode").value("IT-01"));
    }

    @Test
    void fetchByDepartmentById() throws Exception {

        Mockito.when(departmentService.fetchByDepartmentId(Mockito.anyLong()))
                .thenReturn(java.util.Optional.ofNullable(department));

        mockMvc.perform(MockMvcRequestBuilders.get("/department/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.departmentName").value("IT"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.departmentAddress").value("Accra"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.departmentCode").value("IT-01"));
    }

    // Utility method to convert an object to JSON string
    private String asJsonString(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
