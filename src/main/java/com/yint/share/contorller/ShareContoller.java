package com.yint.share.contorller;

import com.yint.share.common.DateUtil;
import com.yint.share.common.LocalCache;
import com.yint.share.common.Result;
import com.yint.share.service.ShareService;
import com.yint.share.service.impl.ShareServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

/**
 * Note:
 * Create by : yintao
 * Create time: 2018/10/31 9:53
 */
@Controller
@RequestMapping("api")
public class ShareContoller {

    private final static String prefix = "ShareContoller:";

    private static Logger logger = LoggerFactory.getLogger(ShareServiceImpl.class);

    @Autowired
    private ShareService shareService;

    @RequestMapping("/index")
    public String showApiIndex(){
        return "modules/api/index";
    }

    @RequestMapping("/getBasicInfo")
    @ResponseBody
    public Object getBasicInfo(){
        logger.info(prefix+ " 当天股票基本信息获取 start...");
        try {
            shareService.getBasicShareInfo();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Result.success();
    }

    @RequestMapping("/getDayShare")
    @ResponseBody
    public Object getDayShare(String date){
        logger.info(prefix+ " 当天信息获取 start...");
        if(StringUtils.isEmpty(date)){
            date = DateUtil.getCurDay();
        }
        shareService.getDayInfo(date);
        logger.info(prefix+ " 当天信息获取 end...");
        return Result.success();
    }

}
