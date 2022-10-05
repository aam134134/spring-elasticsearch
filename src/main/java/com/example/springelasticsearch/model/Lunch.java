package com.example.springelasticsearch.model;

import lombok.*;

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

    private List<String> sides;
}
