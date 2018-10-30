package com.yint.helloworld.domain;

import lombok.Data;

import java.util.List;

@Data
public class TuDataContent {

    private List<String> fields;
    private List<List<String>> items;
}
