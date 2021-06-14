package com.graphqlservices.graphql.service.datafetcher;

import com.graphqlservices.graphql.model.Book;
import com.graphqlservices.graphql.model.Student;
import com.graphqlservices.graphql.repository.StudentRepository;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AllStudentDataFetcher implements DataFetcher<List<Student>> {

    @Autowired
    StudentRepository studentRepository;

    @Override
    public List<Student> get(DataFetchingEnvironment dataFetchingEnvironment) {
        return studentRepository.findAll();
    }
}
