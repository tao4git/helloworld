package com.yint.helloworld.contorller;


import com.alibaba.fastjson.JSON;
import com.yint.helloworld.common.HttpUtils;
import com.yint.helloworld.domain.ResponseData;
import com.yint.helloworld.domain.TushareBo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloController {

    @RequestMapping("/")
    public String login(){
        return "login";
    }

    @RequestMapping("/index")
    public String index(){
        return "index";
    }

    public static void main(String[] args) {
        String url = "http://api.tushare.pro";
        String params = JSON.toJSONString(TushareBo.getStoreBasicParam());
        System.out.println(params);
        String result = HttpUtils.sendJsonPost(url,params);
        System.out.println(result);
        ResponseData rd = JSON.parseObject(result,ResponseData.class);
        if(rd == null){
            return;
        }
        System.out.println("result:"+rd.getData().getItems().size());
        for (int i = 0; i < rd.getData().getItems().size(); i++) {
            System.out.println(i+":"+rd.getData().getItems().get(i));
        }
//        ResponseData responseData = new ResponseData();
//        responseData.setCode(1);
//        responseData.setMsg("1");
//        TuDataContent tuDataContent = new TuDataContent();
//        List<String> strings = new ArrayList<>();
//        strings.add("1");
//        strings.add("2");
//        tuDataContent.setFields(strings);
//        List<List<String>> shareBasicList = new ArrayList<>();
//        List<String> shareBasicList1 = new ArrayList<>();
//        List<String> shareBasicList2 = new ArrayList<>();
//        shareBasicList1.add("111");
//        shareBasicList1.add("112");
//        shareBasicList2.add("221");
//        shareBasicList2.add("222");
//        shareBasicList.add(shareBasicList1);
//        shareBasicList.add(shareBasicList2);
//        tuDataContent.setItems(shareBasicList);
//        responseData.setTuDataContent(tuDataContent);
//        System.out.println(JSON.toJSONString(responseData));
    }
}
