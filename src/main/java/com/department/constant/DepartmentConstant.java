package com.department.constant;

public class DepartmentConstant {

    private DepartmentConstant() {}

    public static final String DEPT_NAME_MSG = "Department name cannot be empty";

    public static final String DEPT_HEAD_MSG = "Department head cannot be empty";

    public static final String DEPT_LOGO_REGEXP = "(?:([^:/?#]+):)?(?://([^/?#]*))?([^?#]*\\.(?:jpg|png))(?:\\?([^#]*))?(?:#(.*))?";

    public static final String DEPT_LOGO_REGEXP_MSG = "Invalid Department Logo";

    public static final String DEPT_ID_REGEXP = "^DID[0-9]+";

    public static final String DEPT_ID_REGEX_MSG = "Department ID should start with DID followed by digit(s)";
}
