package com.department.constant;

public class DepartmentConstant {

    private DepartmentConstant() {}

    public static final String EMP_ID_MSG = "Employee ID cannot be empty";

    public static final String DEPT_ID_MSG = "Department ID cannot be empty";

    public static final String DEPT_NAME_MSG = "Department name cannot be empty";

    public static final String EMP_ID_REGEX_MSG = "Employee ID should start with E followed by 2 digits";

    public static final String DEPT_ID_REGEX_MSG = "Department ID should start with D followed by 2 digits";

    public static final String EMP_ID_REGEXP = "^[E][0-9]{2}$";

    public static final String DEPT_ID_REGEXP = "^[D][0-9]{2}$";
}
