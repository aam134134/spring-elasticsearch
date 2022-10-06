package com.example.springelasticsearch.service;

import com.example.springelasticsearch.model.Lunch;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
public class LunchService {

    private final ElasticsearchOperations elasticsearchOperations;
    private final RestHighLevelClient restHighLevelClient;

    public LunchService(ElasticsearchOperations anElasticsearchOperations, RestHighLevelClient aRestHighLevelClient) {
        elasticsearchOperations = anElasticsearchOperations;
        restHighLevelClient = aRestHighLevelClient;
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

    public Map<String, Object> findByMainCourse(final String mainCourse) throws IOException {
        SearchSourceBuilder builder = new SearchSourceBuilder().postFilter(QueryBuilders.matchQuery("mainCourse", mainCourse));
        SearchRequest searchRequest = Requests.searchRequest("lunch");
        searchRequest.searchType(SearchType.DFS_QUERY_THEN_FETCH);
        searchRequest.source(builder);

        SearchResponse resp = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        return resp.getHits().iterator().next().getSourceAsMap();
    }

    public SearchHits<Lunch> findByMainCourseNative(final String mainCourse) {
        final Query q = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchQuery("mainCourse", mainCourse))
                .build();
        return elasticsearchOperations.search(q, Lunch.class, IndexCoordinates.of("lunch"));
    }
}
