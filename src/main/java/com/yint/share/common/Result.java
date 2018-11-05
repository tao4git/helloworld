package com.yint.share.common;

import com.alibaba.fastjson.JSON;

/**
 * Note:
 * Create by : yintao
 * Create time: 2018/10/31 10:41
 */
public class Result<T> {

    private Integer code;
    private String msg;
    private T data;

    public static <T> String success(){
        return JSON.toJSONString(writeMessage(200, "success",null));
    }

    public static <T> String success(T data){
        return JSON.toJSONString(writeMessage(200, "success",data));
    }

    public static <T> String fail(String msg){
        return JSON.toJSONString(writeMessage(400, msg,null));
    }

    public static <T> String fail(Integer code,String msg){
        return JSON.toJSONString(writeMessage(code, msg,null));
    }

    public static <T> Result writeMessage(Integer code, String msg, T data){
        Result re = new Result();
        re.setCode(code);
        re.setMsg(msg);
        re.setData(data);
        return re;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
