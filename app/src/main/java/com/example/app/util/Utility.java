package com.example.app.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.app.R;
import com.example.app.db.WeatherDB;
import com.example.app.model.City;
import com.example.app.model.County;
import com.example.app.model.Province;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by chen on 2016/4/20.
 */
public class Utility {

    /**
     * 解析XML城市文件并存储到数据库
     */
    public static void handlePCCResponse(WeatherDB weatherDB, Context context)
            throws Exception {

        Province province = new Province();
        City city = new City();
        County county = new County();

        String pa;  //保存数据的临时变量

        InputStream in = context.getResources().openRawResource(R.raw.citys);

        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        XmlPullParser parser = factory.newPullParser();
        parser.setInput(in, "utf-8");

        int event = parser.getEventType();
        while (event != XmlPullParser.END_DOCUMENT) {
            switch (event) {
                case XmlPullParser.START_DOCUMENT:
                    break;
                case XmlPullParser.START_TAG:
                    String tagName = parser.getName();
                    if ("p".equals(tagName)) {
                        int count = parser.getAttributeCount();
                        Log.d("count", String.valueOf(count));
                        for (int i = 0; i < count; i++) {
                            String attrName = parser.getAttributeName(i);
                            String attrValue = parser.getAttributeValue(i);
                            if ("p_id".equals(attrName)) {
                                province.setProvinceCode(attrValue);
                                city.setProvinceId(Integer.parseInt(attrValue));
                                Log.d("p_id", attrValue);
                            }
                        }
                    }
                    if ("pn".equals(tagName)) {
                        pa = parser.nextText();
                        province.setProvinceName(pa);
                        Log.d("ProvinceName", pa);
                        Log.d("Province", province.getProvinceCode());
                        Log.d("Province", province.getProvinceName());
                        weatherDB.saveProvince(province);
                    }
                    if ("c".equals(tagName)) {
                        int count = parser.getAttributeCount();
                        for (int i = 0; i < count; i++) {
                            String attrName = parser.getAttributeName(i);
                            String attrValue = parser.getAttributeValue(i);
                            if ("c_id".equals(attrName)) {
                                city.setCityCode(attrValue);
                                county.setCityId(Integer.parseInt(attrValue));
                                //Log.d("c_id", attrValue);
                            }
                        }
                    }
                    if ("cn".equals(tagName)) {
                        pa = parser.nextText();
                        city.setCityName(pa);
                        weatherDB.saveCity(city);
                    }
                    if ("co".equals(tagName)) {
                        int count = parser.getAttributeCount();
                        for (int i = 0; i < count; i++) {
                            String attrName = parser.getAttributeName(i);
                            String attrValue = parser.getAttributeValue(i);
                            if ("co_id".equals(attrName)) {
                                county.setCountyCode(attrValue);
                                //Log.d("co_id", attrValue);
                            }
                        }
                        pa = parser.nextText();
                        county.setCountyName(pa);

                        weatherDB.saveCounty(county);
                    }
                    break;
                case XmlPullParser.END_TAG:
                    /*if ("c".equals(parser.getName())) {

                    }
                    if ("p".equals(parser.getName())) {

                    }*/
                    break;
            }
            event = parser.next();
        }
    }

    /**
     * 解析服务器返回的json天气数据，并将解析出的数据存储到本地。
     */
    public static void handleWeatherResponse(Context context, String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray weatherInfo = jsonObject.getJSONArray("HeWeather data service 3.0");
            for (int i = 0; i < weatherInfo.length(); i++) {
                JSONObject weather = weatherInfo.getJSONObject(i);
                JSONObject basic = weather.getJSONObject("basic");
                String cityName = basic.getString("city");
                String cId = basic.getString("id");
                JSONObject update = basic.getJSONObject("update");
                String loc = update.getString("loc");
                JSONArray dailyForecast = weather.getJSONArray("daily_forecast");
                String[] weatherDesp = new String[dailyForecast.length()];
                String[] date = new String[dailyForecast.length()];
                String[] max = new String[dailyForecast.length()];
                String[] min = new String[dailyForecast.length()];
                for (int j = 0; j < dailyForecast.length(); j++) {
                    JSONObject obj = dailyForecast.getJSONObject(j);
                    JSONObject cond = obj.getJSONObject("cond");
                    String txtD = cond.getString("txt_d");
                    String txtN = cond.getString("txt_n");
                    Log.d("txtD", txtD);
                    Log.d("txtN", txtN);
                    if (txtD.equals(txtN)) {
                        weatherDesp[j] = txtD;
                    } else {
                        weatherDesp[j] = txtD + "转" + txtN;
                    }
                    date[j] = obj.getString("date");
                    JSONObject tmp = obj.getJSONObject("tmp");
                    max[j] = tmp.getString("max");
                    min[j] = tmp.getString("min");
                }
                JSONObject now = weather.getJSONObject("now");
                JSONObject cond = now.getJSONObject("cond");
                String code = cond.getString("code");
                String txt = cond.getString("txt");
                String tmp = now.getString("tmp");
                //Log.d("HttpUtil", cityName);
                saveWeatherInfo(context, cityName, cId, loc, tmp, txt, code, weatherDesp[1], weatherDesp[2],
                        weatherDesp[3], max[0], max[1], max[2], max[3], min[0], min[1], min[2], min[3],
                        date[1], date[2], date[3]);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将服务器返回的所有天气信息存储到SharedPreferences文件中
     */
    public static void saveWeatherInfo(Context context, String cityName, String cId, String loc,
                                       String nowT, String txt, String code,
                                       String weatherDesp1, String weatherDesp2, String weatherDesp3,
                                       String max0, String max1, String max2, String max3,
                                       String min0, String min1, String min2, String min3,
                                       String date1, String date2, String date3) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年M月d日", Locale.CHINA);
        SharedPreferences.Editor editor = PreferenceManager
                .getDefaultSharedPreferences(context).edit();
        editor.putBoolean("city_selected", true);
        editor.putString("city_name", cityName);
        editor.putString("c_id", cId);
        editor.putString("loc", loc);
        editor.putString("current_date", sdf.format(new Date()));
        editor.putString("nowT", nowT);
        editor.putString("txt", txt);
        editor.putString("code", code);
        editor.putString("weatherDesp1", weatherDesp1);
        editor.putString("weatherDesp2", weatherDesp2);
        editor.putString("weatherDesp3", weatherDesp3);
        editor.putString("max0", max0);
        editor.putString("max1", max1);
        editor.putString("max2", max2);
        editor.putString("max3", max3);
        editor.putString("min0", min0);
        editor.putString("min1", min1);
        editor.putString("min2", min2);
        editor.putString("min3", min3);
        editor.putString("date1", date1);
        editor.putString("date2", date2);
        editor.putString("date3", date3);
        editor.apply();
    }

    /**
     * 解析服务器返回的json运势数据，并将解析出的数据存储到本地。
     */
    public static void handleHoroscopeResponse(Context context, String response) {
        try {
            JSONObject horoscope = new JSONObject(response);
            String name = horoscope.getString("name");
            String all = horoscope.getString("all");
            String health = horoscope.getString("health");
            String love = horoscope.getString("love");
            String money = horoscope.getString("money");
            String work = horoscope.getString("work");
            String color = horoscope.getString("color");
            String number = horoscope.getString("number");
            String friend = horoscope.getString("QFriend");
            String summary = horoscope.getString("summary");
            saveNameInfo(context, name, all, health, love, money, work, color, number,
                    friend, summary);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将服务器返回的星座信息存储到SharedPreferences文件中
     */
    public static void saveNameInfo(Context context, String name, String all, String health,
                                    String love, String money, String work, String color, String number, String friend,
                                    String summary) {
        SharedPreferences.Editor editor = PreferenceManager
                .getDefaultSharedPreferences(context).edit();
        editor.putString("name", name);
        editor.putString("all", all);
        editor.putString("health", health);
        editor.putString("love", love);
        editor.putString("money", money);
        editor.putString("work", work);
        editor.putString("color", color);
        editor.putString("number", number);
        editor.putString("friend", friend);
        editor.putString("summary", summary);
        editor.apply();
    }

    /**
     * 解析服务器返回的json黄历数据，并将解析出的数据存储到本地。
     */
    public static void handleLunarCldResponse(Context context, String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray("result");
            String[] hours = new String[result.length()];
            String[] des = new String[result.length()];
            String[] yi = new String[result.length()];
            String[] ji = new String[result.length()];
            for (int i = 0; i < result.length(); i++) {
                JSONObject r = result.getJSONObject(i);
                hours[i] = r.getString("hours");
                des[i] = r.getString("des");
                yi[i] = r.getString("yi");
                ji[i] = r.getString("ji");
            }
            saveLunarCld(context, hours[0], hours[1], hours[2], hours[3], hours[4], hours[5],
                    hours[6], hours[7], hours[8], hours[9], hours[10], hours[11], des[0], des[1],
                    des[2], des[3], des[4], des[5], des[6], des[7], des[8], des[9], des[10],
                    des[11], yi[0], yi[1], yi[2], yi[3], yi[4], yi[5], yi[6], yi[7], yi[8], yi[9],
                    yi[10], yi[11], ji[0], ji[1], ji[2], ji[3], ji[4], ji[5], ji[6], ji[7], ji[8],
                    ji[9], ji[10], ji[11]);
            Log.d("hours", hours[1]);
            Log.d("des", des[1]);
            Log.d("yi", yi[1]);
            Log.d("ji", ji[1]);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 将服务器返回的黄历信息存储到SharedPreferences文件中
     */
    public static void saveLunarCld(Context context, String hours1, String hours2, String hours3,
                                    String hours4, String hours5, String hours6, String hours7, String hours8, String hours9,
                                    String hours10, String hours11, String hours12, String des1, String des2, String des3,
                                    String des4, String des5, String des6, String des7, String des8, String des9, String des10,
                                    String des11, String des12, String yi1, String yi2, String yi3, String yi4, String yi5,
                                    String yi6, String yi7, String yi8, String yi9, String yi10, String yi11, String yi12,
                                    String ji1, String ji2, String ji3, String ji4, String ji5, String ji6, String ji7,
                                    String ji8, String ji9, String ji10, String ji11, String ji12) {
        SharedPreferences.Editor editor = PreferenceManager
                .getDefaultSharedPreferences(context).edit();
        editor.putString("hours1", hours1);
        editor.putString("hours2", hours2);
        editor.putString("hours3", hours3);
        editor.putString("hours4", hours4);
        editor.putString("hours5", hours5);
        editor.putString("hours6", hours6);
        editor.putString("hours7", hours7);
        editor.putString("hours8", hours8);
        editor.putString("hours9", hours9);
        editor.putString("hours10", hours10);
        editor.putString("hours11", hours11);
        editor.putString("hours12", hours12);
        editor.putString("des1", des1);
        editor.putString("des2", des2);
        editor.putString("des3", des3);
        editor.putString("des4", des4);
        editor.putString("des5", des5);
        editor.putString("des6", des6);
        editor.putString("des7", des7);
        editor.putString("des8", des8);
        editor.putString("des9", des9);
        editor.putString("des10", des10);
        editor.putString("des11", des11);
        editor.putString("des12", des12);
        editor.putString("yi1", yi1);
        editor.putString("yi2", yi2);
        editor.putString("yi3", yi3);
        editor.putString("yi4", yi4);
        editor.putString("yi5", yi5);
        editor.putString("yi6", yi6);
        editor.putString("yi7", yi7);
        editor.putString("yi8", yi8);
        editor.putString("yi9", yi9);
        editor.putString("yi10", yi10);
        editor.putString("yi11", yi11);
        editor.putString("yi12", yi12);
        editor.putString("ji1", ji1);
        editor.putString("ji2", ji2);
        editor.putString("ji3", ji3);
        editor.putString("ji4", ji4);
        editor.putString("ji5", ji5);
        editor.putString("ji6", ji6);
        editor.putString("ji7", ji7);
        editor.putString("ji8", ji8);
        editor.putString("ji9", ji9);
        editor.putString("ji10", ji10);
        editor.putString("ji11", ji11);
        editor.putString("ji12", ji12);
        editor.apply();
    }
}
