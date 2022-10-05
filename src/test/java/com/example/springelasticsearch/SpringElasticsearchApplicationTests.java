package com.example.springelasticsearch;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.testcontainers.elasticsearch.ElasticsearchContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
class SpringElasticsearchApplicationTests {

    @Container
    // static final ensures that Elastic will be up and running before the Spring Boot apps starts fully
    // this allows for the Elasticsearch indexes to be created in the App
    // see com.example.springelasticsearch.ElasticsearchIndexConfig.java
    static final ElasticsearchContainer ELASTICSEARCH_CONTAINER = ElasticSearchTestContainer.getInstance();

    @Autowired
    ElasticsearchOperations elasticsearchOperations;

    @Test
    void testElasticsearchIndexesCreated() {
        Assertions.assertTrue(elasticsearchOperations.indexOps(IndexCoordinates.of("lunch")).exists());
    }
}
