package com.yint.share.domain;

import lombok.Data;

import java.util.List;

@Data
public class TuDataContent {

    private List<String> fields;
    private List<List<String>> items;
}
