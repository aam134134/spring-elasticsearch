package com.example.springelasticsearch.repo;

import com.example.springelasticsearch.model.Lunch;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface LunchRepository extends ElasticsearchRepository<Lunch, String> {

    List<Lunch> findByMainCourse(String mainCourse);
}
