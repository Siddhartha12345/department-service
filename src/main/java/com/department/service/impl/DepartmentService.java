package com.department.service.impl;

import com.department.config.DepartmentIdSequenceGenerator;
import com.department.constant.DepartmentErorEnum;
import com.department.entity.Department;
import com.department.exception.ResourceNotFoundException;
import com.department.repository.DepartmentRepository;
import com.department.service.IDepartmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class DepartmentService implements IDepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private DepartmentIdSequenceGenerator departmentIdSequenceGenerator;

    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentService.class);

    @Override
    public List<Department> getDepartments() {
        LOGGER.info("Fetching department list from database...");
        List<Department> departmentList = departmentRepository.findAll();
        if(Objects.isNull(departmentList) || departmentList.size() == 0) {
            LOGGER.error("No Records found in the database!!");
            throw new ResourceNotFoundException(DepartmentErorEnum.EMPTY_DEPT_LIST.getErrorCode(), DepartmentErorEnum.EMPTY_DEPT_LIST.getErrorMessage());
        }
        LOGGER.info("Fetch operation completed!!");
        return departmentList;
    }

    @Override
    public Department getDepartmentByDeptId(String deptId) {
        LOGGER.info("Fetching department objects for the given Dept ID: {}", deptId);
        Department department = departmentRepository.findById(deptId).orElseThrow(() -> {
            LOGGER.error("No Record found for the given department ID: {}", deptId);
            throw new ResourceNotFoundException(DepartmentErorEnum.DEPT_NOT_FOUND.getErrorCode(), DepartmentErorEnum.DEPT_NOT_FOUND.getErrorMessage());
        });
        LOGGER.info("Fetch operation completed!!");
        return department;
    }

    @Override
    public Department createDepartment(Department department) {
        LOGGER.info("Generating department ID...");
        long sequence = departmentIdSequenceGenerator.findNext();
        department.setDepartmentId("DID" + sequence);

        LOGGER.info("Saving department object on the DB: {}", department);
        return departmentRepository.save(department);
    }

    @Override
    public Department updateDepartment(Department department) {
        LOGGER.info("Checking whether the department object exists on the DB for the given department ID: {}", department.getDepartmentId());
        departmentRepository.findById(department.getDepartmentId()).orElseThrow(() -> {
            LOGGER.error("No Record found for the given department ID: {}", department.getDepartmentId());
            throw new ResourceNotFoundException(DepartmentErorEnum.DEPT_NOT_FOUND.getErrorCode(), DepartmentErorEnum.DEPT_NOT_FOUND.getErrorMessage());
        });
        LOGGER.info("Record found | Updating department object on the DB for department ID: {}", department.getDepartmentId());
        return departmentRepository.save(department);
    }

    @Override
    public void deleteDepartment(String deptId) {
        LOGGER.info("Checking whether the department object exists on the DB for the given department ID: {}", deptId);
        departmentRepository.findById(deptId).orElseThrow(() -> {
            LOGGER.error("No Records found for the given department ID: {}", deptId);
            throw new ResourceNotFoundException(DepartmentErorEnum.DEPT_NOT_FOUND.getErrorCode(), DepartmentErorEnum.DEPT_NOT_FOUND.getErrorMessage());
        });
        LOGGER.info("Record found | Deleting department object on the DB for department ID: {}", deptId);
        departmentRepository.deleteById(deptId);
    }
}
