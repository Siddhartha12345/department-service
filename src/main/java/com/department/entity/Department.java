package com.department.entity;

import com.department.constant.DepartmentConstant;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Department {

    @NotEmpty(message = DepartmentConstant.EMP_ID_MSG)
    @Pattern(regexp = DepartmentConstant.EMP_ID_REGEXP, message = DepartmentConstant.EMP_ID_REGEX_MSG)
    private String empId;

    @NotEmpty(message = DepartmentConstant.DEPT_ID_MSG)
    @Pattern(regexp = DepartmentConstant.DEPT_ID_REGEXP, message = DepartmentConstant.DEPT_ID_REGEX_MSG)
    private String deptId;

    @NotEmpty(message = DepartmentConstant.DEPT_NAME_MSG)
    private String deptName;
}
