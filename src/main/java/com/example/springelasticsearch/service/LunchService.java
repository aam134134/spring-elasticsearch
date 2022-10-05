package com.example.springelasticsearch.service;

import com.example.springelasticsearch.model.Lunch;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.stereotype.Service;

@Service
public class LunchService {

    private final ElasticsearchOperations elasticsearchOperations;

    public LunchService(ElasticsearchOperations anElasticsearchOperations) {
        elasticsearchOperations = anElasticsearchOperations;
    }

    public String save(Lunch lunch) {
        IndexQuery indexQuery = new IndexQueryBuilder()
                .withId(lunch.getLunchId())
                .withObject(lunch)
                .build();
        return elasticsearchOperations.index(indexQuery, IndexCoordinates.of("lunch"));
    }

    public Lunch findById(final String lunchId) {
        return elasticsearchOperations.get(lunchId, Lunch.class, IndexCoordinates.of("lunch"));
    }
}
