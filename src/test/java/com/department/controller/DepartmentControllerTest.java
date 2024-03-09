package com.department.controller;

import com.department.constant.DepartmentTestConstant;
import com.department.entity.Department;
import com.department.repository.DepartmentRepository;
import com.department.service.IDepartmentService;
import com.department.service.impl.DepartmentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.Optional;

@WebMvcTest(DepartmentController.class)
@Import(value = {DepartmentService.class})
public class DepartmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private IDepartmentService departmentService;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private DepartmentRepository departmentRepository;

    // GET /department
    @Test
    @DisplayName("Test to retrieve department list successfully")
    public void testGet_FetchDepartmentList() throws Exception {
        Department department = Department.builder().employeeId("E00001").departmentId("D01").departmentName("IT").departmentHead("Test Name").departmentLogo("abcde").build();
        Mockito.when(departmentRepository.findAll()).thenReturn(Arrays.asList(department));
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.get(DepartmentTestConstant.GET_DEPT_LIST_ENDPOINT)
                        .accept(MediaType.APPLICATION_JSON))
                        .andReturn();
        Assertions.assertEquals(response.getResponse().getStatus(), DepartmentTestConstant.HTTP_OK_CODE);
    }

    @Test
    @DisplayName("Test negative scenario when department list is null")
    public void testGetFetchDepartmentList_Negative() throws Exception {
        Mockito.when(departmentRepository.findAll()).thenReturn(Arrays.asList());
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.get(DepartmentTestConstant.GET_DEPT_LIST_ENDPOINT)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn();
        Assertions.assertEquals(response.getResponse().getStatus(), DepartmentTestConstant.HTTP_NOT_FOUND_CODE);
    }

    // POST /department
    @Test
    @DisplayName("Test to create department object successfully")
    public void testPost_CreateDepartment() throws Exception {
        Department department = Department.builder().employeeId("E00001").departmentId("D01").departmentName("IT").departmentHead("Test Name").departmentLogo("abcde").build();
        Mockito.when(departmentRepository.save(Mockito.any(Department.class))).thenReturn(department);
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.post(DepartmentTestConstant.POST_DEPT_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(department)))
                        .andReturn();
        Assertions.assertEquals(response.getResponse().getStatus(), DepartmentTestConstant.HTTP_CREATED_CODE);
    }

    @Test
    @DisplayName("Test negative scenario to create department object when invalid Emp Id is passed")
    public void testPost_ForInvalidEmpId() throws Exception {
        Department department = Department.builder().employeeId("A01").departmentId("D01").build();
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.post(DepartmentTestConstant.POST_DEPT_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(department)))
                .andReturn();
        Assertions.assertEquals(response.getResponse().getStatus(), DepartmentTestConstant.HTTP_BAD_REQUEST_CODE);
    }

    @Test
    @DisplayName("Test negative scenario to create department object when invalid Dept Id is passed")
    public void testPost_ForInvalidDeptId() throws Exception {
        Department department = Department.builder().employeeId("E00001").departmentId("G01").build();
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.post(DepartmentTestConstant.POST_DEPT_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(department)))
                .andReturn();
        Assertions.assertEquals(response.getResponse().getStatus(), DepartmentTestConstant.HTTP_BAD_REQUEST_CODE);
    }

    // GET /department/{deptId}
    @Test
    @DisplayName("Test success scenario to get department object by Dept ID")
    public void testGetDepartmentByDeptId_Success() throws Exception {
        Department department = Department.builder().employeeId("E00001").departmentId("D01").departmentName("IT").departmentHead("Test Name").departmentLogo("abcde").build();
        Mockito.when(departmentRepository.findByDepartmentId(Mockito.anyString())).thenReturn(Arrays.asList(department));
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
        Mockito.when(departmentRepository.findByDepartmentId(Mockito.anyString())).thenReturn(null);
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.get(DepartmentTestConstant.GET_DEPT_BY_DEPTID_ENDPOINT, "D03")
                        .accept(MediaType.APPLICATION_JSON))
                        .andReturn();
        Assertions.assertEquals(response.getResponse().getStatus(), DepartmentTestConstant.HTTP_NOT_FOUND_CODE);
    }

    // GET /department/employee/{empId}
    @Test
    @DisplayName("Test success scenario to get department object by Emp ID")
    public void testGetDepartmentByEmpId_Success() throws Exception {
        Department department = Department.builder().employeeId("E00001").departmentId("D01").departmentName("IT").departmentHead("Test Name").departmentLogo("abcde").build();
        Mockito.when(departmentRepository.findById(Mockito.anyString())).thenReturn(Optional.ofNullable(department));
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.get(DepartmentTestConstant.GET_DEPT_BY_EMPID_ENDPOINT, "E00001")
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
        Mockito.when(departmentRepository.findById(Mockito.anyString())).thenReturn(Optional.empty());
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.get(DepartmentTestConstant.GET_DEPT_BY_EMPID_ENDPOINT, "E00008")
                        .accept(MediaType.APPLICATION_JSON))
                        .andReturn();
        Assertions.assertEquals(response.getResponse().getStatus(), DepartmentTestConstant.HTTP_NOT_FOUND_CODE);
    }

    // PUT /department
    @Test
    @DisplayName("Test scenario to update a department object successfully")
    public void testPut_UpdateDeptSuccessfully() throws Exception {
        Department department = Department.builder().employeeId("E00001").departmentId("D01").departmentName("IT").departmentHead("Test Name").departmentLogo("abcde").build();
        Mockito.when(departmentRepository.findById(Mockito.anyString())).thenReturn(Optional.of(department));
        Mockito.when(departmentRepository.save(Mockito.any(Department.class))).thenReturn(department);
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.put(DepartmentTestConstant.PUT_DEPT_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(department)))
                        .andReturn();
        Assertions.assertEquals(response.getResponse().getStatus(), DepartmentTestConstant.HTTP_OK_CODE);
    }

    @Test
    @DisplayName("Test negative scenario for update department where object does not exist in DB")
    public void testPut_UpdateDeptFailure() throws Exception {
        Department department = Department.builder().employeeId("E00001").departmentId("D01").departmentName("IT").departmentHead("Test Name").departmentLogo("abcde").build();
        Mockito.when(departmentRepository.findById(Mockito.anyString())).thenReturn(Optional.empty());
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.put(DepartmentTestConstant.PUT_DEPT_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(department)))
                        .andReturn();
        Assertions.assertEquals(response.getResponse().getStatus(), DepartmentTestConstant.HTTP_NOT_FOUND_CODE);
    }

    // DELETE /department/{empId}
    @Test
    @DisplayName("Test scenario to delete a department object successfully")
    public void testDel_DeleteDeptSuccessfully() throws Exception {
        Department department = Department.builder().employeeId("E00001").departmentId("D01").departmentName("IT").departmentHead("Test Name").departmentLogo("abcde").build();
        Mockito.when(departmentRepository.findById(Mockito.anyString())).thenReturn(Optional.of(department));
        Mockito.doNothing().when(departmentRepository).deleteById(Mockito.anyString());
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.delete(DepartmentTestConstant.DELETE_DEPT_ENDPOINT, "E00001"))
                                    .andReturn();
        Assertions.assertEquals(response.getResponse().getStatus(), DepartmentTestConstant.HTTP_OK_CODE);
    }

    @Test
    @DisplayName("Test negative scenario to delete a department object where employee ID does not exist")
    public void testDel_DeleteDeptFailure() throws Exception {
        Mockito.when(departmentRepository.findById(Mockito.anyString())).thenReturn(Optional.empty());
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.delete(DepartmentTestConstant.DELETE_DEPT_ENDPOINT, "E00001"))
                                    .andReturn();
        Assertions.assertEquals(response.getResponse().getStatus(), DepartmentTestConstant.HTTP_NOT_FOUND_CODE);
    }
}
