package com.yint.share.domain;

import lombok.Data;

@Data
public class TradeDay {

    private String exchange;
    private String cal_date;
    private Integer is_open;
    private String pretrade_date;
}
