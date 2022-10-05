package com.example.springelasticsearch;

import com.example.springelasticsearch.model.Lunch;
import com.example.springelasticsearch.repo.LunchRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.elasticsearch.ElasticsearchContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.stream.StreamSupport;

@SpringBootTest
@Testcontainers
class SpringElasticsearchApplicationTests {

    @Container
    ElasticsearchContainer ELASTICSEARCH_CONTAINER = ElasticSearchTestContainer.getInstance();

    @Autowired
    LunchRepository lunchRepository;

    @Test
    void testRepo() {
        lunchRepository.save(new Lunch("l1", "pizza"));
        lunchRepository.save(new Lunch("l2", "sandwich"));
        Assertions.assertEquals(2, StreamSupport.stream(lunchRepository.findAll().spliterator(), false).count());
    }
}
