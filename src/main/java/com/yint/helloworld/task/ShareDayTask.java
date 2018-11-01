package com.yint.helloworld.task;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Note:
 * Create by : yintao
 * Create time: 2018/10/31 10:04
 */
@Component
public class ShareDayTask {


    private static final Logger logger = LoggerFactory.getLogger(ShareDayTask.class);

    private static final String prefix = "ShareDayTask:";

    private static final String dayFm = "yyyyMMdd";
    private static final String datefm = "yyyy-MM-dd HH:mm:ss";

    /**
     * 更新
     */
    @Scheduled(cron="0 30 18 * * ?")
    public void getShareBasicInfoTask(){
        logger.info(prefix+ DateFormatUtils.format(new Date(), dayFm));
        logger.info(prefix+ DateFormatUtils.format(new Date(), datefm));
    }
}
