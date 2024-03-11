package com.department.constant;

public class DepartmentConstant {

    private DepartmentConstant() {}

    public static final String EMP_ID_MSG = "Employee ID cannot be empty";

    public static final String DEPT_ID_MSG = "Department ID cannot be empty";

    public static final String DEPT_ID_REGEX_MSG = "Department ID should start with DID followed by 1 or 2 digit(s)";

    public static final String EMP_ID_REGEX_MSG = "Employee ID should start with EID followed by digit(s)";

    public static final String EMP_ID_REGEXP = "^EID[0-9]+";

    public static final String DEPT_ID_REGEXP = "^DID[0-9]{1,2}$";
}
