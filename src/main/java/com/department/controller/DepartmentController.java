package com.department.controller;

import com.department.constant.DepartmentConstant;
import com.department.entity.Department;
import com.department.service.IDepartmentService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("/api/v1")
public class DepartmentController {

    @Autowired
    private IDepartmentService departmentService;

    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentController.class);

    @GetMapping("/department")
    public ResponseEntity<List<Department>> getAllDepartments() {
        LOGGER.info("Execution started for fetching department list...");
        List<Department> departmentList = departmentService.getDepartments();
        LOGGER.info("List fetched successfully: {}", departmentList);
        return new ResponseEntity<>(departmentList, HttpStatus.OK);
    }

    @GetMapping("/department/{deptId}")
    public ResponseEntity<Department> getDepartment(@Pattern(regexp = DepartmentConstant.DEPT_ID_REGEXP, message = DepartmentConstant.DEPT_ID_REGEX_MSG)
                                                    @PathVariable String deptId) {
        LOGGER.info("Execution started for fetching department object based on Dept ID");
        Department department = departmentService.getDepartmentByDeptId(deptId);
        LOGGER.info("Department object fetched successfully: {}", department);
        return new ResponseEntity<>(department, HttpStatus.OK);
    }

    @PostMapping("/department")
    public ResponseEntity<Department> addDepartment(@Valid @RequestBody Department department) {
        LOGGER.info("Execution started for creating a department object");
        Department createdDeparted = departmentService.createDepartment(department);
        LOGGER.info("Department object created successfully: {}", department);
        return new ResponseEntity<>(createdDeparted, HttpStatus.CREATED);
    }

    @PutMapping("/department")
    public ResponseEntity<Department> updateDepartment(@Valid @RequestBody Department department) {
        LOGGER.info("Execution started for updating department object...");
        Department updatedDepartment = departmentService.updateDepartment(department);
        LOGGER.info("Department object updated successfully: {}", updatedDepartment);
        return new ResponseEntity<>(updatedDepartment, HttpStatus.OK);
    }

    @DeleteMapping("/department/{deptId}")
    public ResponseEntity<Void> deleteDepartment(@Pattern(regexp = DepartmentConstant.DEPT_ID_REGEXP, message = DepartmentConstant.DEPT_ID_REGEX_MSG)
                                                 @PathVariable String deptId) {
        LOGGER.info("Execution started for deleting department object...");
        departmentService.deleteDepartment(deptId);
        LOGGER.info("Department object deleted successfully");
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
