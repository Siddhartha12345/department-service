package com.department.constant;

public class DepartmentTestConstant {

    private DepartmentTestConstant() {}

    public static final String GET_DEPT_LIST_ENDPOINT = "/api/v1/department";

    public static final String POST_DEPT_ENDPOINT = "/api/v1/department";

    public static final String GET_DEPT_BY_DEPTID_ENDPOINT = "/api/v1/department/{deptId}";

    public static final String GET_DEPT_BY_EMPID_ENDPOINT = "/api/v1/department/employee/{empId}";

    public static final int HTTP_OK_CODE = 200;

    public static final int HTTP_CREATED_CODE = 201;

    public static final int HTTP_BAD_REQUEST_CODE = 400;

    public static final int HTTP_NOT_FOUND_CODE = 404;
}
