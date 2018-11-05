package com.yint.share.service.impl;

import com.alibaba.fastjson.JSON;
import com.yint.share.common.*;
import com.yint.share.domain.*;
import com.yint.share.service.ShareService;
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
    @Value("${tushare.token}")
    private String token;

    @Override
    public String getBasicShareInfo() throws IOException {
        String str = LocalCache.getBasicShareCache().getIfPresent(DateUtil.getCurDay());
        if(StringUtils.isNotEmpty(str)){
            logger.info(prefix+ "缓存中已有当天数据,直接返回...");
            return str;
        }
        String params = JSON.toJSONString(buildStoreBasicParam());
        logger.info(prefix+" getBasicShareInfo 入参:" + params);
        String result = HttpUtils.sendJsonPost(apiUrl,params);
        ResponseData rd = JSON.parseObject(result,ResponseData.class);
        if(rd == null){
            logger.error(prefix+" getBasicShareInfo 返回结果解析为空 "+result);
            return null;
        }
        List<ShareBasic> shareDayList = new ArrayList<>();
        ShareBasic shareBasic = null;
        for (int i = 0; i < rd.getData().getItems().size(); i++) {
            List<String> dataList = rd.getData().getItems().get(i);
            if(CollectionUtils.isEmpty(dataList)){
                return null;
            }
            shareBasic = new ShareBasic();
            shareBasic.setTs_code(dataList.get(0));
            shareBasic.setSymbol(dataList.get(1));
            shareBasic.setName(dataList.get(2));
            shareBasic.setArea(dataList.get(3));
            shareBasic.setIndustry(dataList.get(4));
            shareBasic.setMarket(dataList.get(5));
            shareBasic.setList_date(dataList.get(6));
            shareDayList.add(shareBasic);
        }
        logger.info(prefix+" getBasicShareInfo :"+shareDayList.size() );
        //转换成json格式，保存到文件中
        FileUtil.saveFile(JSON.toJSONString(shareDayList),filePath,"basic_"+ DateUtil.getCurDay());
        LocalCache.getBasicShareCache().put(DateUtil.getCurDay(),JSON.toJSONString(shareDayList));
        return JSON.toJSONString(shareDayList);
    }

    @Override
    public String getDayInfo(String date) {
        String str =LocalCache.getShareDayCache().getIfPresent(DateUtil.getCurDay());
        if(StringUtils.isNotEmpty(str)){
            logger.info(prefix+ "缓存中已有当天数据,直接返回...");
            return str;
        }
        String params = JSON.toJSONString(buildStoreDayParam(date));
        logger.info(prefix+" getDayInfo 入参:" + params);
        String result = HttpUtils.sendJsonPost(apiUrl,params);
        ResponseData rd = JSON.parseObject(result,ResponseData.class);
        List<ShareDay> dayList = new ArrayList<>();
        ShareDay shareDay = null;
        for (int i = 0; i < rd.getData().getItems().size(); i++) {
            List<String> dataList = rd.getData().getItems().get(i);
            if(CollectionUtils.isEmpty(dataList)){
                return null;
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
        logger.info(prefix+" getDayInfo size="+dayList.size());
        //转换成json格式，保存到文件中s
        try {
            FileUtil.saveFile(JSON.toJSONString(dayList),filePath,"day_"+ DateUtil.getCurDay());
        } catch (IOException e) {
            e.printStackTrace();
        }
        LocalCache.getShareDayCache().put(DateUtil.getCurDay(),JSON.toJSONString(dayList));
        return JSON.toJSONString(dayList);
    }

    @Override
    public Boolean checkTradeDay(String date) {
        TradeDay tradeDay = null;
        String str =LocalCache.getTradeDayCache().getIfPresent(Constant.TRAND_DAY_KEY+date);
        if(StringUtils.isEmpty(str)){
            logger.info(prefix+ "缓存中无数据,通过接口加载缓存...");
            String params = JSON.toJSONString(buildTradeDayParam());
            logger.info(prefix+" checkTradeDay 入参:" + params);
            String result = HttpUtils.sendJsonPost(apiUrl,params);
            ResponseData rd = JSON.parseObject(result,ResponseData.class);
            TradeDay tradeDayData = null;
            for (int i = 0; i < rd.getData().getItems().size(); i++) {
                List<String> dataList = rd.getData().getItems().get(i);
                if(CollectionUtils.isEmpty(dataList)){
                    return null;
                }
                tradeDayData = new TradeDay();
                tradeDayData.setExchange(dataList.get(0));
                tradeDayData.setCal_date(dataList.get(1));
                tradeDayData.setIs_open(Integer.parseInt(dataList.get(2)));
                tradeDayData.setPretrade_date(dataList.get(3));
                LocalCache.getTradeDayCache().put(Constant.TRAND_DAY_KEY+tradeDayData.getCal_date(),JSON.toJSONString(tradeDayData));
            }
            str = LocalCache.getTradeDayCache().getIfPresent(Constant.TRAND_DAY_KEY+date);
        }
        TradeDay tradeDay1 = JSON.parseObject(str,TradeDay.class);
        if(tradeDay1.getIs_open().equals(0)){
            return false;
        }else if(tradeDay1.getIs_open().equals(1)){
            return true;
        }
        return false;
    }

    private TushareBo buildTradeDayParam(){
        Map<String,String> params = new HashMap<>();
        String year = DateUtil.getCurDay().substring(0,4);
        params.put("start_date",year+"0101");
        params.put("end_date",year+"1231");
        String fields ="exchange,cal_date,is_open,pretrade_date";
        return initParam("trade_cal",fields,params);
    }

    /**
     * 获取当天数据
     */
    public void getTodayInfo() throws Exception {
        //从缓存中获取当天所有股票列表
        try {
            String str = getBasicShareInfo();
            List<ShareBasic> basicList = JSON.parseArray(str,ShareBasic.class);
            if(CollectionUtils.isEmpty(basicList)){
                logger.error(prefix+" basicList is empty..");
                return;
            }
            for (int i = 0; i < basicList.size(); i++) {
                //循环股票代码，获取当天数据及均值
                ShareBasic shareBasic = basicList.get(i);
                String params = JSON.toJSONString(buildCommonMaParam(shareBasic.getTs_code(),DateUtil.getCurDay(),DateUtil.getCurDay()));
                logger.info(prefix+" getDayInfo 入参:" + params);
                String result = HttpUtils.sendJsonPost(apiUrl,params);
            }
        } catch (IOException e) {
           logger.error(prefix+" getTodayInfo error msg="+e.getMessage());
           throw new Exception("getTodayInfo运行异常:"+e.getMessage());
        }
    }


    /**
     * 获取历史数据
     * @return
     */
    public void getHisShareInfo(String tsCode){

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

    private String buildCommonDayParam(String tsCode,String startDate,String endDate){
        CommonInfo commonInfo = new CommonInfo();
        commonInfo.setStart_date(startDate);
        commonInfo.setEnd_date(endDate);
        commonInfo.setTs_code(tsCode);
        commonInfo.setPro_api(token);
        commonInfo.setAdj("qfq");
        return JSON.toJSONString(commonInfo);
    }

    private String buildCommonMaParam(String tsCode,String startDate,String endDate){
        CommonInfo commonInfo = new CommonInfo();
        commonInfo.setStart_date(startDate);
        commonInfo.setEnd_date(endDate);
        commonInfo.setTs_code(tsCode);
        commonInfo.setPro_api(token);
        List<Long> malist = new ArrayList<>();
        malist.add(5L);
        malist.add(20L);
        malist.add(60L);
        commonInfo.setMa(malist);
        return JSON.toJSONString(commonInfo);
    }


}
