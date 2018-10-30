package com.yint.helloworld.domain;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class TushareBo {

    private String api_name;
    private String token = "a51459d8211cd73aaf71cbe35b2c897fa0468ee9f4dd4b9f8487e5df";
    private Map<String,String> params;
    private String fields;

    public static TushareBo getStoreBasicParam(){
        TushareBo tushareBo = new TushareBo();
        tushareBo.setApi_name("stock_basic");
        Map<String,String> params = new HashMap<>();
        params.put("is_hs","N");
        params.put("list_status","L");
        params.put("exchange_id","");
        tushareBo.setParams(params);
        String fields ="ts_code,symbol,name,area,industry,market,list_date";
        tushareBo.setFields(fields);
        return tushareBo;
    }
}
