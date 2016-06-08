package com.example.OfficeControl;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by gm on 2016/6/1.
 */
public class InformationService{
    public static String getInfo (String name){
        String path = "http://182.254.218.192/OfficeControl/userinfo.servlet";
        try {
            return SendGETRequest(path, name, "UTF-8");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return e.toString();
        }
    }
    /**
     * 发送GET请求
     * @param path  请求路径
     * @param name  请求参数
     * @return 请求是否成功
     * @throws Exception
     */
    private static String SendGETRequest(String path,String name,String ecoding) throws Exception{
        StringBuilder url = new StringBuilder(path);
        url.append("?");
        url.append("userName=");
        url.append(URLEncoder.encode(name, ecoding));
        HttpURLConnection conn = (HttpURLConnection)new URL(url.toString()).openConnection();
        conn.setConnectTimeout(100000);
        conn.setRequestMethod("GET");

        if(conn.getResponseCode() == 200){
            InputStreamReader isr = new InputStreamReader(conn.getInputStream());
            BufferedReader buffer = new BufferedReader(isr);
            StringBuilder result = new StringBuilder();
            String data = "";
            result.append(buffer.readLine());
            while((data=buffer.readLine())!=null){
                result.append(data);
            }
            return result.toString();
        }
        return "no data";
    }
}
