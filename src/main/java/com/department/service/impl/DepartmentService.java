package com.department.service.impl;

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

    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentService.class);

    @Override
    public List<Department> getDepartments() {
        LOGGER.info("Fetching department list from database...");
        List<Department> departmentList = departmentRepository.findAll();
        if(Objects.isNull(departmentList) || departmentList.size() == 0) {
            LOGGER.error("No Records found in the database!!");
            throw new ResourceNotFoundException("Department List is empty");
        }
        LOGGER.info("Fetch operation completed!!");
        return departmentList;
    }

    @Override
    public List<Department> getDepartmentsByDeptId(String deptId) {
        LOGGER.info("Fetching department objects for the given Dept ID: {}", deptId);
        List<Department> departments = departmentRepository.findByDepartmentId(deptId);
        if(departments == null) {
            LOGGER.error("No Records found for the given department ID: {}", deptId);
            throw new ResourceNotFoundException("Department with given Dept ID is not found on the DB: " + deptId);
        }
        LOGGER.info("Fetch operation completed!!");
        return departments;
    }

    @Override
    public Department getDepartmentByEmployeeId(String empId) {
        LOGGER.info("Fetching department object for the given Emp ID: {}", empId);
        return departmentRepository.findById(empId).orElseThrow(() -> {
            LOGGER.error("No Records found for the given employee ID: {}", empId);
            throw new ResourceNotFoundException("Department with given Emp ID is not found on the DB: " + empId);
        });
    }

    @Override
    public Department createDepartment(Department department) {
        LOGGER.info("Saving department object on the DB: {}", department);
        Department preparedDepartment = fetchDepartmentInfo(department);
        return departmentRepository.save(preparedDepartment);
    }

    private static Department fetchDepartmentInfo(Department department) {
        LOGGER.info("Preparing department object information...");
        switch (department.getDepartmentId()) {
            case "DID1": department.setDepartmentName("Information Technology");
                        department.setDepartmentHead("Manoj Jaiswal");
                        department.setDepartmentLogo("https://i.pinimg.com/originals/06/bc/e8/06bce81285badba0c3becd273ca67f95.png");
                        break;
            case "DID2": department.setDepartmentName("Human Resources");
                        department.setDepartmentHead("Kirti Kumari");
                        department.setDepartmentLogo("https://t4.ftcdn.net/jpg/04/80/07/65/360_F_480076596_9kBMBjheEMPoR422SJp0olzqMHuthBtu.jpg");
                        break;
            case "DID3": department.setDepartmentName("Accounts and Finance");
                        department.setDepartmentHead("Ankush Jha");
                        department.setDepartmentLogo("https://png.pngtree.com/element_our/sm/20180417/sm_5ad5dd92336fd.png");
                        break;
            case "DID4": department.setDepartmentName("Transport");
                        department.setDepartmentHead("Gaurav Sharma");
                        department.setDepartmentLogo("https://png.pngtree.com/png-vector/20191129/ourmid/pngtree-fast-delivery-icon-delivery-icon-png-image_2047531.jpg");
                        break;
            case "DID5": department.setDepartmentName("Admin");
                        department.setDepartmentHead("Praful Kumar");
                        department.setDepartmentLogo("https://static.vecteezy.com/system/resources/previews/020/429/953/non_2x/admin-icon-vector.jpg");
                        break;
        }
        LOGGER.info("Department object preparation done!!");
        return department;
    }

    @Override
    public Department updateDepartment(Department department) {
        LOGGER.info("Checking whether the department object exists on the DB for the given Emp ID: {}", department.getEmployeeId());
        departmentRepository.findById(department.getEmployeeId()).orElseThrow(() -> {
            LOGGER.error("No Records found for the given employee ID: {}", department.getEmployeeId());
            throw new ResourceNotFoundException("Department with given Emp ID is not found on the DB: " + department.getEmployeeId());
        });
        Department preparedDepartment = fetchDepartmentInfo(department);
        LOGGER.info("Record found | Updating department object on the DB for Emp ID: {}", department.getEmployeeId());
        return departmentRepository.save(preparedDepartment);
    }

    @Override
    public void deleteDepartment(String empId) {
        LOGGER.info("Checking whether the department object exists on the DB for the given Emp ID: {}", empId);
        departmentRepository.findById(empId).orElseThrow(() -> {
            LOGGER.error("No Records found for the given employee ID: {}", empId);
            throw new ResourceNotFoundException("Department with given Emp ID is not found on the DB: " + empId);
        });
        LOGGER.info("Record found | Deleting department object on the DB for Emp ID: {}", empId);
        departmentRepository.deleteById(empId);
    }
}
