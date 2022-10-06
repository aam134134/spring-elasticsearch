package com.example.springelasticsearch;

import com.example.springelasticsearch.model.Lunch;
import com.example.springelasticsearch.service.LunchService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.testcontainers.elasticsearch.ElasticsearchContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.IOException;

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

    @Autowired
    LunchService lunchService;

    @Test
    void testElasticsearchIndexesCreated() {
        Assertions.assertTrue(elasticsearchOperations.indexOps(IndexCoordinates.of("lunch")).exists());
    }

    @Test
    void testElasticsearchSave() {
        lunchService.save(new Lunch("l1", "pizza"));
        lunchService.save(new Lunch("l2", "sandwich"));
        Assertions.assertEquals("l1", lunchService.findById("l1").getLunchId());
        Assertions.assertEquals("sandwich", lunchService.findById("l2").getMainCourse());
    }

    @Test
    void testElasticsearchRestHighLevelQuery() throws IOException {
        lunchService.save(new Lunch("l1", "pizza"));
        lunchService.save(new Lunch("l2", "sandwich"));
        Assertions.assertEquals("pizza", lunchService.findByMainCourse("pizza").get("mainCourse"));
    }

    @Test
    void testElasticsearchNativeQuery() throws IOException, InterruptedException {
        lunchService.save(new Lunch("l3", "spaghetti"));
        lunchService.save(new Lunch("l4", "chicken"));

        // appear to be hitting a timing hole after index insert; adding delay
        Thread.sleep(1000);

        SearchHits<Lunch> hits = lunchService.findByMainCourseNative("chicken");
        Assertions.assertEquals("l4", hits.stream().iterator().next().getContent().getLunchId());

        hits = lunchService.findByMainCourseNative("pizza");
        Assertions.assertEquals("l1", hits.stream().iterator().next().getContent().getLunchId());
    }
}
