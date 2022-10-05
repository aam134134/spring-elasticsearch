package com.example.springelasticsearch.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;

@Document(indexName = "lunch")
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class Lunch {

    @Id
    @NonNull
    private String lunchId;

    @NonNull
    private String mainCourse;

    @Field(type = FieldType.Nested, includeInParent = true)
    private List<String> sides;
}