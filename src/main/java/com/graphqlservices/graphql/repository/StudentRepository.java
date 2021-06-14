package com.graphqlservices.graphql.repository;

import com.graphqlservices.graphql.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, String> {
}

