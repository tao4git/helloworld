package com.yint.share.domain;


import lombok.Data;

import java.util.List;

@Data
public class CommonInfo {
    private String ts_code;
    private String pro_api;
    private String start_date;
    private String end_date;
    private String asset;
    private String adj;
    private String freq;
    private List<Long> ma;

}
