package com.graphqlservices.graphql.repository;


import com.graphqlservices.graphql.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, String> {
}
