package com.yint.share.contorller;


import com.alibaba.fastjson.JSON;
import com.yint.share.common.HttpUtils;
import com.yint.share.domain.ResponseData;
import com.yint.share.domain.TushareBo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloController {

    @RequestMapping("/")
    public String home(){
        return "home";
    }

    @RequestMapping("/login")
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
    }
}
