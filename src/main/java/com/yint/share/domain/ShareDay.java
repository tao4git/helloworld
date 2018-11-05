package com.yint.share.domain;

import lombok.Data;

/**
 * Note:
 * Create by : yintao
 * Create time: 2018/10/31 16:20
 */
@Data
public class ShareDay {

    private String ts_code;
    private String trade_date;
    private Float open;
    private Float high;
    private Float low;
    private Float close;
    private Float pre_close;
    private Float change;
    private Float pct_change;
    private Float vol;
    private Float amount;

}
