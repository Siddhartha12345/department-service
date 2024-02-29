package com.department.service.impl;

import com.department.entity.Department;
import com.department.exception.ResourceNotFoundException;
import com.department.service.IDepartmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DepartmentService implements IDepartmentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentService.class);

    private List<Department> departmentList = new ArrayList<>();

    public DepartmentService() {
        departmentList.add(new Department("E01", "D01", "Information Technology"));
        departmentList.add(new Department("E02", "D02", "Human Resource"));
    }

    @Override
    public List<Department> getDepartments() {
        LOGGER.info("Fetching department list from server...");
        return departmentList;
    }

    @Override
    public Department getDepartment(String deptId) {
        LOGGER.info("Fetching department object for the given Dept ID: {}", deptId);
        return departmentList.stream().filter(d -> d.getDeptId().equals(deptId))
                .findAny().orElseThrow(() -> {
                    LOGGER.error("Department with given Dept ID is not found on the server: {}", deptId);
                    throw new ResourceNotFoundException("Department with given Dept ID is not found on the server: " + deptId);
                });
    }

    @Override
    public Department getDepartmentByEmployeeId(String empId) {
        LOGGER.info("Fetching department object for the given Emp ID: {}", empId);
        return departmentList.stream().filter(d -> d.getEmpId().equals(empId))
                .findAny().orElseThrow(() -> {
                    LOGGER.error("Department with given Emp ID is not found on the server: {}", empId);
                    throw new ResourceNotFoundException("Department with given Emp ID is not found on the server: " + empId);
                });
    }

    @Override
    public Department createDepartment(Department department) {
        LOGGER.info("Saving department object on the server: {}", department);
        departmentList.add(department);
        return department;
    }
}
