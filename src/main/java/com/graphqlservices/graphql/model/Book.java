package com.graphqlservices.graphql.model;

import lombok.*;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.persistence.Entity;
import javax.persistence.Id;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
public class Book {

    @Id
    public String isn;
    public String title;
    public String publisher;
    public String[] Authors;
    public String publishedDate;


}
