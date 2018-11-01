package com.yint.helloworld.service.impl;

import com.alibaba.fastjson.JSON;
import com.yint.helloworld.common.DateUtil;
import com.yint.helloworld.common.FileUtil;
import com.yint.helloworld.common.HttpUtils;
import com.yint.helloworld.common.LocalCache;
import com.yint.helloworld.domain.ResponseData;
import com.yint.helloworld.domain.ShareDay;
import com.yint.helloworld.domain.TushareBo;
import com.yint.helloworld.service.ShareService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Note:
 * Create by : yintao
 * Create time: 2018/10/31 9:57
 */
@Service(value = "shareService")
public class ShareServiceImpl implements ShareService {

    private final static String prefix = "shareService";

    private static Logger logger = LoggerFactory.getLogger(ShareServiceImpl.class);

    @Value("${tushare.api.url}")
    private String apiUrl;
    @Value("${tushare.file.path}")
    private String filePath;

    @Override
    public void getBasicShareInfo() throws IOException {
        String params = JSON.toJSONString(buildStoreBasicParam());
        logger.info(prefix+" getBasicShareInfo 入参:" + params);
        String result = HttpUtils.sendJsonPost(apiUrl,params);
        ResponseData rd = JSON.parseObject(result,ResponseData.class);
        if(rd == null){
            logger.error(prefix+" getBasicShareInfo 返回结果解析为空 "+result);
            return;
        }
        //转换成json格式，保存到文件中
        FileUtil.saveFile(JSON.toJSONString(rd),filePath,"basic_"+ DateUtil.getCurDay());
        LocalCache.getBasicShareCache().put(DateUtil.getCurDay(),JSON.toJSONString(rd));
    }

    @Override
    public void getDayInfo(String date) {
        String params = JSON.toJSONString(buildStoreDayParam(date));
        logger.info(prefix+" getDayInfo 入参:" + params);
        String result = HttpUtils.sendJsonPost(apiUrl,params);
        ResponseData rd = JSON.parseObject(result,ResponseData.class);
        List<ShareDay> dayList = new ArrayList<>();
        ShareDay shareDay = null;
        for (int i = 0; i < rd.getData().getItems().size(); i++) {
            List<String> dataList = rd.getData().getItems().get(i);
            if(CollectionUtils.isEmpty(dataList)){
                return;
            }
            shareDay = new ShareDay();
            shareDay.setTs_code(dataList.get(0));
            shareDay.setTrade_date(dataList.get(1));
            shareDay.setOpen(Float.parseFloat(dataList.get(2)));
            shareDay.setHigh(Float.parseFloat(dataList.get(3)));
            shareDay.setLow(Float.parseFloat(dataList.get(4)));
            shareDay.setClose(Float.parseFloat(dataList.get(5)));
            shareDay.setPre_close(Float.parseFloat(dataList.get(6)));
            shareDay.setChange(Float.parseFloat(dataList.get(7)));
            shareDay.setPct_change(Float.parseFloat(dataList.get(8)));
            shareDay.setVol(Float.parseFloat(dataList.get(9)));
            shareDay.setAmount(Float.parseFloat(dataList.get(10)));
            dayList.add(shareDay);
        }
        logger.info(prefix+dayList.size());
        //转换成json格式，保存到文件中s
        try {
            FileUtil.saveFile(JSON.toJSONString(dayList),filePath,"day_"+ DateUtil.getCurDay());
        } catch (IOException e) {
            e.printStackTrace();
        }
        LocalCache.getShareDayCache().put(DateUtil.getCurDay(),JSON.toJSONString(dayList));
    }

    private TushareBo initParam(String apiName,String fields,Map<String,String> paramMap){
        TushareBo tushareBo = new TushareBo();
        tushareBo.setApi_name(apiName);
        tushareBo.setFields(fields);
        tushareBo.setParams(paramMap);
        return tushareBo;
    }

    private TushareBo buildStoreBasicParam(){
        Map<String,String> params = new HashMap<>();
        params.put("is_hs","N");
        params.put("list_status","L");
        params.put("exchange_id","");
        String fields ="ts_code,symbol,name,area,industry,market,list_date";
        return initParam("stock_basic",fields,params);
    }

    private TushareBo buildStoreDayParam(String date){
        Map<String,String> params = new HashMap<>();
        params.put("trade_date",date);
        String fields ="ts_code,trade_date,open,high,low,close,pre_close,change,pct_change,vol,amount";
        return initParam("daily",fields,params);
    }
}
