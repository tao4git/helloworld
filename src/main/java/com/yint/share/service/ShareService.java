package com.yint.share.service;

import java.io.IOException;

/**
 * Note:
 * Create by : yintao
 * Create time: 2018/10/31 9:57
 */
public interface ShareService {


    public String getBasicShareInfo() throws IOException;

    public String getDayInfo(String date);

    public Boolean checkTradeDay(String date);
}
