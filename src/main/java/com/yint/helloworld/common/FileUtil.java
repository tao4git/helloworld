package com.yint.helloworld.common;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.util.JsonUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;

/**
 * Note: 图片特征值公共类
 * Create by : yintao
 * Create time: 2018/6/1 9:20
 */
@Component
public class FileUtil {

    private static final String prefix = " 文件服务：";

    private static final Logger log = LogManager.getLogger(FileUtil.class);


    /**
     * 保存文件
     * @param str 内容
     * @param path 文件路径
     * @throws IOException
     */
    public static void saveFile(String str, String path){
        FileWriter fw = null;
        try {
            File file = new File( path );
            fw = new FileWriter(file);
            BufferedWriter out = new BufferedWriter(fw);
            out.write(str, 0, str.length()-1);
            out.close();
        } catch (IOException e){
            log.error(prefix+" error "+e.getMessage(),e);
        }
    }
    /**
     * 保存文件
     * @param str 特征值
     * @param path 路径
     * @param filename 文件名称
     * @throws IOException
     */
    public static void saveFile(String str, String path,String filename) throws IOException{
        FileWriter fw = null;
        File file = new File( path + "/"+ filename);
        if(StringUtils.isEmpty(str)){
            log.error(prefix+" 文件内容为空..");
            return;
        }
        fw = new FileWriter(file);
        BufferedWriter out = new BufferedWriter(fw);
        out.write(str, 0, str.length()-1);
        out.close();
    }




}
