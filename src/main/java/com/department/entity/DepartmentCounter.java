package com.department.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "deptcounter")
public class DepartmentCounter {

    @Id
    private String id;

    private long sequence;
}
