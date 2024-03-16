package com.department.service;

import com.department.entity.Department;

import java.util.List;

public interface IDepartmentService {

    List<Department> getDepartments();

    Department getDepartmentByDeptId(String deptId);

    Department createDepartment(Department department);

    Department updateDepartment(Department department);

    void deleteDepartment(String deptId);
}
