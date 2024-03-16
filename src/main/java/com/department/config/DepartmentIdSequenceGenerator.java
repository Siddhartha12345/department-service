package com.department.config;

import com.department.entity.DepartmentCounter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Component
public class DepartmentIdSequenceGenerator {

    @Autowired
    private MongoTemplate mongoTemplate;

    public long findNext() {

        Query query = query(where("_id").is("my-sequence"));
        Update update = new Update().inc("sequence", 1);
        DepartmentCounter counter = mongoTemplate.findAndModify(query, update, options().returnNew(true), DepartmentCounter.class);
        return counter.getSequence();
    }
}
