package com.yint.helloworld.service;

import java.io.IOException;

/**
 * Note:
 * Create by : yintao
 * Create time: 2018/10/31 9:57
 */
public interface ShareService {


    public void getBasicShareInfo() throws IOException;

    public void getDayInfo(String date);
}
