package com.department.controller;

import com.department.constant.DepartmentTestConstant;
import com.department.entity.Department;
import com.department.service.IDepartmentService;
import com.department.service.impl.DepartmentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest(DepartmentController.class)
@Import(value = {DepartmentService.class})
public class DepartmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private IDepartmentService departmentService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Test to retrieve department list successfully")
    public void testGet_FetchDepartmentList() throws Exception {
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.get(DepartmentTestConstant.GET_DEPT_LIST_ENDPOINT)
                        .accept(MediaType.APPLICATION_JSON))
                        .andReturn();
        Assertions.assertEquals(response.getResponse().getStatus(), DepartmentTestConstant.HTTP_OK_CODE);
    }

    @Test
    @DisplayName("Test to create department object successfully")
    public void testPost_CreateDepartment() throws Exception {
        Department department = Department.builder().empId("E01").deptId("D01").deptName("HR").build();
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.post(DepartmentTestConstant.POST_DEPT_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(department)))
                        .andReturn();
        Assertions.assertEquals(response.getResponse().getStatus(), DepartmentTestConstant.HTTP_CREATED_CODE);
    }

    @Test
    @DisplayName("Test negative scenario to create department object when invalid Emp Id is passed")
    public void testPost_ForInvalidEmpId() throws Exception {
        Department department = Department.builder().empId("A01").deptId("D01").deptName("HR").build();
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.post(DepartmentTestConstant.POST_DEPT_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(department)))
                .andReturn();
        Assertions.assertEquals(response.getResponse().getStatus(), DepartmentTestConstant.HTTP_BAD_REQUEST_CODE);
    }

    @Test
    @DisplayName("Test negative scenario to create department object when invalid Dept Id is passed")
    public void testPost_ForInvalidDeptId() throws Exception {
        Department department = Department.builder().empId("E01").deptId("G01").deptName("HR").build();
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.post(DepartmentTestConstant.POST_DEPT_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(department)))
                .andReturn();
        Assertions.assertEquals(response.getResponse().getStatus(), DepartmentTestConstant.HTTP_BAD_REQUEST_CODE);
    }

    @Test
    @DisplayName("Test negative scenario to create department object when empty Dept name is passed")
    public void testPost_ForEmptyDeptName() throws Exception {
        Department department = Department.builder().empId("E01").deptId("D01").build();
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.post(DepartmentTestConstant.POST_DEPT_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(department)))
                .andReturn();
        Assertions.assertEquals(response.getResponse().getStatus(), DepartmentTestConstant.HTTP_BAD_REQUEST_CODE);
    }

    @Test
    @DisplayName("Test success scenario to get department object by Dept ID")
    public void testGetDepartmentByDeptId_Success() throws Exception {
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.get(DepartmentTestConstant.GET_DEPT_BY_DEPTID_ENDPOINT, "D01")
                        .accept(MediaType.APPLICATION_JSON))
                        .andReturn();
        Assertions.assertEquals(response.getResponse().getStatus(), DepartmentTestConstant.HTTP_OK_CODE);
    }

    @Test
    @DisplayName("Test negative scenario to get department object by passing an invalid Dept ID")
    public void testGetDepartmentByDeptId_InvalidDeptId() throws Exception {
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.get(DepartmentTestConstant.GET_DEPT_BY_DEPTID_ENDPOINT, "A01")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn();
        Assertions.assertEquals(response.getResponse().getStatus(), DepartmentTestConstant.HTTP_BAD_REQUEST_CODE);
    }

    @Test
    @DisplayName("Test negative scenario to get department object by passing a non existing Dept ID")
    public void testGetDepartmentByDeptId_DeptNotFound() throws Exception {
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.get(DepartmentTestConstant.GET_DEPT_BY_DEPTID_ENDPOINT, "D08")
                        .accept(MediaType.APPLICATION_JSON))
                        .andReturn();
        Assertions.assertEquals(response.getResponse().getStatus(), DepartmentTestConstant.HTTP_NOT_FOUND_CODE);
    }

    @Test
    @DisplayName("Test success scenario to get department object by Emp ID")
    public void testGetDepartmentByEmpId_Success() throws Exception {
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.get(DepartmentTestConstant.GET_DEPT_BY_EMPID_ENDPOINT, "E01")
                        .accept(MediaType.APPLICATION_JSON))
                        .andReturn();
        Assertions.assertEquals(response.getResponse().getStatus(), DepartmentTestConstant.HTTP_OK_CODE);
    }

    @Test
    @DisplayName("Test negative scenario to get department object by passing an invalid Emp ID")
    public void testGetDepartmentByEmpId_InvalidEmpId() throws Exception {
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.get(DepartmentTestConstant.GET_DEPT_BY_EMPID_ENDPOINT, "A01")
                        .accept(MediaType.APPLICATION_JSON))
                        .andReturn();
        Assertions.assertEquals(response.getResponse().getStatus(), DepartmentTestConstant.HTTP_BAD_REQUEST_CODE);
    }

    @Test
    @DisplayName("Test negative scenario to get department object by passing a non existing Emp ID")
    public void testGetDepartmentByEmpId_DeptNotFound() throws Exception {
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.get(DepartmentTestConstant.GET_DEPT_BY_EMPID_ENDPOINT, "E08")
                        .accept(MediaType.APPLICATION_JSON))
                        .andReturn();
        Assertions.assertEquals(response.getResponse().getStatus(), DepartmentTestConstant.HTTP_NOT_FOUND_CODE);
    }
}
