package com.department.service;

import com.department.entity.Department;

import java.util.List;

public interface IDepartmentService {

    List<Department> getDepartments();

    List<Department> getDepartmentsByDeptId(String deptId);

    Department getDepartmentByEmployeeId(String empId);

    Department createDepartment(Department department);
}
