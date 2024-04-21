package com.department.controller;

import com.department.config.DepartmentIdSequenceGenerator;
import com.department.constant.DepartmentTestConstant;
import com.department.entity.Department;
import com.department.repository.DepartmentRepository;
import com.department.response.BusinessErrorResponse;
import com.department.response.ValidationError;
import com.department.response.ValidationErrorResponse;
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

    @MockBean
    private DepartmentIdSequenceGenerator departmentIdSequenceGenerator;

    // GET /department
    @Test
    @DisplayName("Test to retrieve department list successfully")
    public void testGet_FetchDepartmentList() throws Exception {
        Department department = Department.builder().departmentId("DID1").departmentName("IT").departmentHead("Test Name").departmentLogo("abcde").build();
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
        BusinessErrorResponse errorResponse =  objectMapper.readValue(response.getResponse().getContentAsString(), BusinessErrorResponse.class);
        Assertions.assertEquals(response.getResponse().getStatus(), DepartmentTestConstant.HTTP_NOT_FOUND_CODE);
        Assertions.assertEquals(errorResponse.getErrorCode(), DepartmentTestConstant.EMPTY_LIST_ERR_CODE);
    }

    // POST /department
    @Test
    @DisplayName("Test to create department object successfully")
    public void testPost_CreateDepartment() throws Exception {
        Department department = Department.builder().departmentId("DID1").departmentName("IT").departmentHead("Test Name").departmentLogo("https://test.png").build();
        Mockito.when(departmentRepository.save(Mockito.any(Department.class))).thenReturn(department);
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.post(DepartmentTestConstant.POST_DEPT_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(department)))
                        .andReturn();
        Assertions.assertEquals(response.getResponse().getStatus(), DepartmentTestConstant.HTTP_CREATED_CODE);
    }

    @Test
    @DisplayName("Test negative scenario to create department object when empty department name is passed")
    public void testPost_ForEmptyDeptName() throws Exception {
        Department department = Department.builder().departmentId("DID1").departmentHead("Test Name").departmentLogo("https://test.png").build();
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.post(DepartmentTestConstant.POST_DEPT_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(department)))
                .andReturn();
        ValidationErrorResponse validationErrorResponse = objectMapper.readValue(response.getResponse().getContentAsString(), ValidationErrorResponse.class);
        ValidationError validationError = validationErrorResponse.getValidationErrors().get(0);
        Assertions.assertEquals(response.getResponse().getStatus(), DepartmentTestConstant.HTTP_BAD_REQUEST_CODE);
        Assertions.assertEquals(validationError.getErrorCode(), DepartmentTestConstant.VALIDATION_ERR_CODE);
    }

    @Test
    @DisplayName("Test negative scenario to create department object when invalid department logo is passed")
    public void testPost_ForInvalidDeptId() throws Exception {
        Department department = Department.builder().departmentId("DID1").departmentName("IT").departmentHead("Test Name").departmentLogo("test").build();
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.post(DepartmentTestConstant.POST_DEPT_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(department)))
                .andReturn();
        ValidationErrorResponse validationErrorResponse = objectMapper.readValue(response.getResponse().getContentAsString(), ValidationErrorResponse.class);
        ValidationError validationError = validationErrorResponse.getValidationErrors().get(0);
        Assertions.assertEquals(response.getResponse().getStatus(), DepartmentTestConstant.HTTP_BAD_REQUEST_CODE);
        Assertions.assertEquals(validationError.getErrorCode(), DepartmentTestConstant.VALIDATION_ERR_CODE);
    }

    // GET /department/{deptId}
    @Test
    @DisplayName("Test success scenario to get department object by Dept ID")
    public void testGetDepartmentByDeptId_Success() throws Exception {
        Department department = Department.builder().departmentId("DID1").departmentName("IT").departmentHead("Test Name").departmentLogo("https://test.png").build();
        Mockito.when(departmentRepository.findById(Mockito.anyString())).thenReturn(Optional.ofNullable(department));
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.get(DepartmentTestConstant.GET_DEPT_BY_DEPTID_ENDPOINT, "DID1")
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
        ValidationErrorResponse validationErrorResponse = objectMapper.readValue(response.getResponse().getContentAsString(), ValidationErrorResponse.class);
        ValidationError validationError = validationErrorResponse.getValidationErrors().get(0);
        Assertions.assertEquals(response.getResponse().getStatus(), DepartmentTestConstant.HTTP_BAD_REQUEST_CODE);
        Assertions.assertEquals(validationError.getErrorCode(), DepartmentTestConstant.VALIDATION_ERR_CODE);
    }

    @Test
    @DisplayName("Test negative scenario to get department object by passing a non existing Dept ID")
    public void testGetDepartmentByDeptId_DeptNotFound() throws Exception {
        Mockito.when(departmentRepository.findById(Mockito.anyString())).thenReturn(Optional.empty());
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.get(DepartmentTestConstant.GET_DEPT_BY_DEPTID_ENDPOINT, "DID3")
                        .accept(MediaType.APPLICATION_JSON))
                        .andReturn();
        BusinessErrorResponse errorResponse = objectMapper.readValue(response.getResponse().getContentAsString(), BusinessErrorResponse.class);
        Assertions.assertEquals(response.getResponse().getStatus(), DepartmentTestConstant.HTTP_NOT_FOUND_CODE);
        Assertions.assertEquals(errorResponse.getErrorCode(), DepartmentTestConstant.DEPT_NOT_FOUND_ERR_CODE);
    }

    // PUT /department
    @Test
    @DisplayName("Test scenario to update a department object successfully")
    public void testPut_UpdateDeptSuccessfully() throws Exception {
        Department department = Department.builder().departmentId("DID1").departmentName("IT").departmentHead("Test Name").departmentLogo("https://test.png").build();
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
        Department department = Department.builder().departmentId("DID9").departmentName("IT").departmentHead("Test Name").departmentLogo("https://test.png").build();
        Mockito.when(departmentRepository.findById(Mockito.anyString())).thenReturn(Optional.empty());
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.put(DepartmentTestConstant.PUT_DEPT_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(department)))
                        .andReturn();
        BusinessErrorResponse errorResponse = objectMapper.readValue(response.getResponse().getContentAsString(), BusinessErrorResponse.class);
        Assertions.assertEquals(response.getResponse().getStatus(), DepartmentTestConstant.HTTP_NOT_FOUND_CODE);
        Assertions.assertEquals(errorResponse.getErrorCode(), DepartmentTestConstant.DEPT_NOT_FOUND_ERR_CODE);
    }

    // DELETE /department/{empId}
    @Test
    @DisplayName("Test scenario to delete a department object successfully")
    public void testDel_DeleteDeptSuccessfully() throws Exception {
        Department department = Department.builder().departmentId("DID1").departmentName("IT").departmentHead("Test Name").departmentLogo("https://test.png").build();
        Mockito.when(departmentRepository.findById(Mockito.anyString())).thenReturn(Optional.of(department));
        Mockito.doNothing().when(departmentRepository).deleteById(Mockito.anyString());
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.delete(DepartmentTestConstant.DELETE_DEPT_ENDPOINT, "DID1"))
                                    .andReturn();
        Assertions.assertEquals(response.getResponse().getStatus(), DepartmentTestConstant.HTTP_OK_CODE);
    }

    @Test
    @DisplayName("Test negative scenario to delete a department object where employee ID does not exist")
    public void testDel_DeleteDeptFailure() throws Exception {
        Mockito.when(departmentRepository.findById(Mockito.anyString())).thenReturn(Optional.empty());
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.delete(DepartmentTestConstant.DELETE_DEPT_ENDPOINT, "DID1"))
                                    .andReturn();
        BusinessErrorResponse errorResponse = objectMapper.readValue(response.getResponse().getContentAsString(), BusinessErrorResponse.class);
        Assertions.assertEquals(response.getResponse().getStatus(), DepartmentTestConstant.HTTP_NOT_FOUND_CODE);
        Assertions.assertEquals(errorResponse.getErrorCode(), DepartmentTestConstant.DEPT_NOT_FOUND_ERR_CODE);
    }
}
