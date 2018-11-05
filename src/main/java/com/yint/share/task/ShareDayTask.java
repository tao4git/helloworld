package com.yint.share.task;

import com.yint.share.service.ShareService;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
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

    @Resource
    private ShareService shareService;

    /**
     * 更新
     */
    @Scheduled(cron="0 0 18 * * ?")
    public void getShareBasicInfoTask(){

        String curDay =  DateFormatUtils.format(new Date(), dayFm);
        Boolean flag = shareService.checkTradeDay(curDay);
        if(!flag){
            logger.info(prefix+ " today is weekend,no data to get,do something else..."+curDay);
            return;
        }
        logger.info(prefix+ " getShareBasicInfoTask start..."+curDay);
        try {
            shareService.getBasicShareInfo();
        } catch (IOException e) {
            logger.error(prefix+" getShareBasicInfoTask getBasicShareInfo error,msg="+e.getMessage() );
        }

        shareService.getDayInfo(curDay);

    }
}
