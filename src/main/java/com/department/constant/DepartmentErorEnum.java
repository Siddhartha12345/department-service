package com.department.constant;

import lombok.Getter;

@Getter
public enum DepartmentErorEnum {

    EMPTY_DEPT_LIST("DEP-SVC-001", "Department List is empty"),
    DEPT_VALIDATION_ERR("DEP-SVC-002", "Validation error occurred"),
    DEPT_NOT_FOUND("DEP-SVC-003", "Department with given Dept ID is not found on the DB");

    private final String errorCode;

    private final String errorMessage;

    private DepartmentErorEnum(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
