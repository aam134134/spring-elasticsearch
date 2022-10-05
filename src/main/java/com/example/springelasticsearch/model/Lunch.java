package com.example.springelasticsearch.model;

import lombok.*;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class Lunch {

    @NonNull
    private String lunchId;

    @NonNull
    private String mainCourse;

    @Field(type = FieldType.Nested, includeInParent = true)
    private List<String> sides;
}
