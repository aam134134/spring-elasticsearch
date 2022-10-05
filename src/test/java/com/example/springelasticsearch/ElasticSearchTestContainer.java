package com.example.springelasticsearch;

import org.testcontainers.elasticsearch.ElasticsearchContainer;

public class ElasticSearchTestContainer extends ElasticsearchContainer {

    // Current default and supported version of Elastic in Spring Boot
    // I believe this can be overwritten using "elasticsearch.version" in Gradle.
    private static final String ELASTIC_SEARCH_VERSION = "docker.elastic.co/elasticsearch/elasticsearch:7.17.6";

    private static ElasticSearchTestContainer container;

    private ElasticSearchTestContainer() {
        super(ELASTIC_SEARCH_VERSION);
    }

    public static ElasticSearchTestContainer getInstance() {
        if (container == null) {
            container = new ElasticSearchTestContainer();
        }
        return container;
    }

    @Override
    public void start() {
        addFixedExposedPort(9200, 9200);
        super.start();
    }
}