package com.department.entity;

import com.department.constant.DepartmentConstant;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Document(collection = "department")
public class Department {

    @Id
    @NotEmpty(message = DepartmentConstant.EMP_ID_MSG)
    @Pattern(regexp = DepartmentConstant.EMP_ID_REGEXP, message = DepartmentConstant.EMP_ID_REGEX_MSG)
    private String employeeId;

    @NotEmpty(message = DepartmentConstant.DEPT_ID_MSG)
    @Pattern(regexp = DepartmentConstant.DEPT_ID_REGEXP, message = DepartmentConstant.DEPT_ID_REGEX_MSG)
    private String departmentId;

    private String departmentName;

    private String departmentHead;

    private String departmentLogo;
}
