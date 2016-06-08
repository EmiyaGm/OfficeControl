package com.example.OfficeControl;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.example.OfficeControl.vo.Weather;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by gm on 2016/6/2.
 */
public class WeatherService {
    private static final String BASE_URL =
            "http://api.map.baidu.com/telematics/v3/weather?location=CITY_ID&output=json&ak=7C7fhFZqqtq1uGzLnhMk0T65gjn3ZVe0";

    /**
     * 查询天气
     * @param city 城市编号
     * @return Weather
     */
    public static List<Weather> query(String city) throws IOException, JSONException {
        String urlEncodedCityName = URLEncoder.encode(city, "UTF-8");
        //替换城市id，生成具体的url
        String url = BASE_URL.replaceAll("CITY_ID", urlEncodedCityName);
        //连接
        HttpURLConnection c = (HttpURLConnection) new URL(url).openConnection();
        //输入
        InputStream is = c.getInputStream();
        //读取json文本
        String jsonText = readAllText(is, "UTF-8");
        //解析
        List<Weather> weathers = parseWeather(jsonText);
        //返回
        return weathers;
    }

    /**
     * 读取文本
     * @param is
     * @return
     */
    private static String readAllText(InputStream is, String encoding) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, encoding));
        StringBuilder builder = new StringBuilder(10240);
        String line;
        while((line = reader.readLine()) != null) {
            if (builder.length() > 0) builder.append('\n');
            builder.append(line);
        }
        reader.close();
        return builder.toString();
    }

    /**
     * 解析JSON
     * @param jsonText
     * @return
     */
    private static List<Weather> parseWeather(String jsonText) throws JSONException, IOException {
        List<Weather> weathers = new ArrayList<Weather>();
        Weather weather = null;
        //保存一天的天气信息
        JSONObject data = null;
        //保存天气图标
        Bitmap bitmap = null;
        //从JSON中读取天气信息数组
        JSONObject o = new JSONObject(jsonText);
        JSONArray datas = ((JSONObject) o.getJSONArray("results").get(0)).getJSONArray("weather_data");
        //读取天气保存到weather对象
        for(int i=0;i<datas.length();i++){
            weather = new Weather();
            data = (JSONObject) datas.get(i);
            //第一天的周几信息里包含日期和温度
            if(i==0) {
                String[] s1 = data.getString("date").split(" ");
                weather.setDay(s1[0]);
            }
            else{
                weather.setDay(data.getString("date"));
            }
            //本地设置时间，不读取
            Calendar c = Calendar.getInstance();
            c.add(Calendar.DAY_OF_MONTH,i);
            weather.setDate((c.get(Calendar.MONTH)+1)+"月"
                    +c.get(Calendar.DAY_OF_MONTH)+"日");

            bitmap = loadNetPicture(data.getString("dayPictureUrl"));
            weather.setPic1(bitmap);
            bitmap = loadNetPicture(data.getString("nightPictureUrl"));
            weather.setPic2(bitmap);
            weather.setCondition(data.getString("weather"));
            weather.setWind(data.getString("wind"));
            weather.setTemp(data.getString("temperature"));

            weathers.add(weather);
        }
        return weathers;
    }

    /**
     * 加载天气图标
     * @param urlString
     * @return
     */
    private static Bitmap loadNetPicture(String urlString) throws IOException {
        Bitmap bitmap = null;
        //连接
        HttpURLConnection c = null;
        c = (HttpURLConnection) new URL(urlString).openConnection();
        //获得输入流
        assert c != null;
        InputStream is = c.getInputStream();
        //转换为图像
        bitmap = BitmapFactory.decodeStream(is);
        return bitmap;
    }
}
