package com.example.springelasticsearch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;

@Configuration
public class ElasticsearchIndexConfig {

    @Autowired
    ElasticsearchOperations elasticsearchOperations;

    /**
     * After Spring Boot starts but before accepting requests. Setup Elasticsearch indexes.
     */
    @EventListener
    public void setupElasticsearchIndexes(ContextRefreshedEvent contextRefreshedEvent) {
        elasticsearchOperations.indexOps(IndexCoordinates.of("lunch")).create();
    }
}
