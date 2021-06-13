package com.graphqlservices.graphql.service;


import com.graphqlservices.graphql.model.Book;
import com.graphqlservices.graphql.repository.BookRepository;
import com.graphqlservices.graphql.service.datafetcher.AllBooksDataFetcher;
import com.graphqlservices.graphql.service.datafetcher.BookDataFetcher;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.stream.Stream;

@Service
public class GraphqlService {

    @Autowired
    BookRepository bookRepository;

    @Value("classpath:books.graphql")
    Resource resource;

    private GraphQL graphQL;

    @Autowired
    private AllBooksDataFetcher allBooksDataFetcher;

    @Autowired
    private BookDataFetcher bookDataFetcher;

    @PostConstruct
    private void loadSchema() throws IOException {

        //load books into book repository
        loadDataIntoHSQL();
        //get the schema
        File schemaFile = resource.getFile();
        //parse schema
        TypeDefinitionRegistry typeDefinitionRegistry = new SchemaParser().parse(schemaFile);
        RuntimeWiring wiring = buildRuntimeWiring();
        GraphQLSchema schema = new SchemaGenerator().makeExecutableSchema(typeDefinitionRegistry, wiring);
        graphQL = GraphQL.newGraphQL(schema).build();

    }

    private void loadDataIntoHSQL() {

        Stream.of(
                new Book("1", "shoe dog", "penguinIndia", new String[]{"peter", "Sam"}, "01-01-2010"),
                new Book("2", "half gf", "macmillian", new String[]{"peter", "Sam"}, "01-01-2020"),
                new Book("3", "3 mistakes of my life", "penguinIndia", new String[]{"peter", "Sam"}, "01-01-2016"),
                new Book("4", "elon musk", "bill orielly", new String[]{"zeeshan"}, "01-01-2017")
        ).forEach(book -> {
            bookRepository.save(book);
        });
    }

    private RuntimeWiring buildRuntimeWiring() {
        return RuntimeWiring.newRuntimeWiring()
                .type("Query", typeWiring -> typeWiring
                        .dataFetcher("allBooks", allBooksDataFetcher)
                        .dataFetcher("book", bookDataFetcher))
                .build();
    }

    public GraphQL getGraphQL() {
        return graphQL;
    }
}
