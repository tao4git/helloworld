package com.yint.share.domain;

import lombok.Data;

@Data
public class ResponseData {

    private String request_id;
    private Integer code;
    private String msg;
    private TuDataContent data;
}
