package com.example.OfficeControl;

import android.renderscript.ScriptGroup;
import android.widget.Toast;
import com.example.OfficeControl.fragment.R;
import com.example.OfficeControl.vo.User;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by gm on 2016/5/30.
 */
public class LoginService{
    /**
     * 登录验证
     * @param name 姓名
     * @param password 密码
     * @return
     */
    public static String login (String name, String password){
        String path = "http://182.254.218.192/OfficeControl/userlogin.servlet";
        Map<String, String> user = new HashMap<String, String>();
        user.put("userName", name);
        user.put("password", password);
        try {
            return SendGETRequest(path, user, "UTF-8");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return e.toString();
        }
    }
    /**
     * 发送GET请求
     * @param path  请求路径
     * @param user  请求参数
     * @return 请求是否成功
     * @throws Exception
     */
    private static String SendGETRequest(String path, Map<String, String> user, String ecoding) throws Exception{
        // http://127.0.0.1:8080/Register/ManageServlet?name=1233&password=abc
        StringBuilder url = new StringBuilder(path);
        url.append("?");
        for(Map.Entry<String, String> map : user.entrySet()){
            url.append(map.getKey()).append("=");
            url.append(URLEncoder.encode(map.getValue(), ecoding));
            url.append("&");
        }
        url.deleteCharAt(url.length()-1);
        System.out.println(url);
        HttpURLConnection conn = (HttpURLConnection)new URL(url.toString()).openConnection();
        conn.setConnectTimeout(100000);
        conn.setRequestMethod("GET");

        if(conn.getResponseCode() == 200){
            InputStreamReader isr = new InputStreamReader(conn.getInputStream());
            BufferedReader buffer = new BufferedReader(isr);
            String result = buffer.readLine();
            return result;
        }
        return "error";
    }

}
