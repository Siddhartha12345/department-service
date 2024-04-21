package com.department.entity;

import com.department.constant.DepartmentConstant;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
    private String departmentId;

    @NotEmpty(message = DepartmentConstant.DEPT_NAME_MSG)
    private String departmentName;

    @NotEmpty(message = DepartmentConstant.DEPT_HEAD_MSG)
    private String departmentHead;

    @Pattern(regexp = DepartmentConstant.DEPT_LOGO_REGEXP, message = DepartmentConstant.DEPT_LOGO_REGEXP_MSG)
    private String departmentLogo;

    @Size(min = 8, max = 500, message = DepartmentConstant.DEPT_INFO_MSG)
    private String departmentInfo;
}
