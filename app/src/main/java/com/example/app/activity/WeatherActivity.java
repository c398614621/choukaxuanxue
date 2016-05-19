package com.example.app.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.app.R;
import com.example.app.service.AutoUpdateService;
import com.example.app.util.DownImage;
import com.example.app.util.HttpCallbackListener;
import com.example.app.util.HttpUtil;
import com.example.app.util.Utility;

/**
 * Created by chen on 2016/5/8.
 */
public class WeatherActivity extends AppCompatActivity {

    private static final String key = "df0e2c33e65b48819bf1229b58d4cfaf";

    private LinearLayout weatherInfoLayout;

    private LinearLayout weatherInfo2Layout;
    /**
     * 用于显示城市名
     */
    private TextView cityNameText;
    /**
     * 用于显示发布时间
     */
    private TextView publishText;
    /**
     * 用于显示天气描述信息
     */
    private TextView weatherDespText;
    /**
     * 用于显示气温1
     */
    private TextView temp1Text;
    /**
     * 用于显示气温2
     */
    private TextView temp2Text;
    /**
     * 用于显示当前日期
     */
    private TextView currentDateText;
    /**
     * 用于显示实时气温
     */
    private TextView nowText;
    /**
     * 用于显示气温图标
     */
    private ImageView weatherPng;
    /**
     * 用于显示第二天天气描述
     */
    private TextView weatherD2Text;

    private TextView temp3Text;

    private TextView temp4Text;

    private TextView date2Text;
    /**
     * 用于显示第三天天气描述
     */
    private TextView weatherD3Text;

    private TextView temp5Text;

    private TextView temp6Text;

    private TextView date3Text;
    /**
     * 用于显示第四天天气描述
     */
    private TextView weatherD4Text;

    private TextView temp7Text;

    private TextView temp8Text;

    private TextView date4Text;
    /**
     * 下拉刷新
     */
    private SwipeRefreshLayout swipeRefreshLayout;
    /**
     * 是否刷新中
     */
    private boolean isRefresh = false;
    /**
     * 切换城市按钮
     */
    private Button switchCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_layout);
        //初始化各控件
        weatherInfoLayout = (LinearLayout) findViewById(R.id.weather_info_layout);
        weatherInfo2Layout = (LinearLayout) findViewById(R.id.weather_info2_layout);
        cityNameText = (TextView) findViewById(R.id.city_name);
        publishText = (TextView) findViewById(R.id.publish_text);
        weatherDespText = (TextView) findViewById(R.id.weather_desp);
        temp1Text = (TextView) findViewById(R.id.temp1);
        temp2Text = (TextView) findViewById(R.id.temp2);
        currentDateText = (TextView) findViewById(R.id.current_date);
        nowText = (TextView) findViewById(R.id.now);
        weatherPng = (ImageView) findViewById(R.id.weather_png);
        weatherD2Text = (TextView) findViewById(R.id.weather_desp2);
        temp3Text = (TextView) findViewById(R.id.temp3);
        temp4Text = (TextView) findViewById(R.id.temp4);
        date2Text = (TextView) findViewById(R.id.date2);
        weatherD3Text = (TextView) findViewById(R.id.weather_desp3);
        temp5Text = (TextView) findViewById(R.id.temp5);
        temp6Text = (TextView) findViewById(R.id.temp6);
        date3Text = (TextView) findViewById(R.id.date3);
        weatherD4Text = (TextView) findViewById(R.id.weather_desp4);
        temp7Text = (TextView) findViewById(R.id.temp7);
        temp8Text = (TextView) findViewById(R.id.temp8);
        date4Text = (TextView) findViewById(R.id.date4);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        switchCity = (Button) findViewById(R.id.switch_city);

        String countyCode = getIntent().getStringExtra("county_code");
        if (!TextUtils.isEmpty(countyCode)) {
            //有县级代码就去查询天气
            publishText.setText("同步中...");
            weatherInfoLayout.setVisibility(View.INVISIBLE);
            weatherInfo2Layout.setVisibility(View.INVISIBLE);
            cityNameText.setVisibility(View.INVISIBLE);
            queryWeather(countyCode);
        } else {
            //没有县级代码就直接显示本地天气
            showWeather();
        }

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!isRefresh) {
                    publishText.setText("同步中...");
                    isRefresh = true;
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            swipeRefreshLayout.setRefreshing(false);
                            SharedPreferences prefs = PreferenceManager.
                                    getDefaultSharedPreferences(WeatherActivity.this);
                            String cId = prefs.getString("c_id", "");
                            if (!TextUtils.isEmpty(cId)) {
                                queryW(cId);
                            }
                            isRefresh = false;
                        }
                    }, 3000);
                }
            }
        });

        switchCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WeatherActivity.this, ChooseAreaActivity.class);
                intent.putExtra("from_weather_activity", true);
                startActivity(intent);
                finish();
            }
        });
    }

    /**
     * 查询县级代码对应的天气
     */
    private void queryWeather(String countyCode) {
        String address = "https://api.heweather.com/x3/weather?cityid=CN" + countyCode +
                "&key=" + key;
        queryFromServer(address);
    }

    private void queryW(String cId) {
        String address = "https://api.heweather.com/x3/weather?cityid=" + cId + "&key=" + key;
        queryFromServer(address);
    }

    /**
     * 根据传入的地址去向服务器查询天气信息
     */
    private void queryFromServer(String address) {
        HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                //处理服务器返回的天气信息
                Utility.handleWeatherResponse(WeatherActivity.this, response);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showWeather();
                    }
                });
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        publishText.setText("同步失败");
                    }
                });
            }
        });
    }

    /**
     * 从SharedPreferences文件中读取存储的天气信息，并显示到界面上
     */
    private void showWeather() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        cityNameText.setText(prefs.getString("city_name", ""));
        publishText.setText(String.format("%s发布", prefs.getString("loc", "")));
        currentDateText.setText(prefs.getString("current_date", ""));
        nowText.setText(String.format("实时：%s°", prefs.getString("nowT", "")));
        weatherDespText.setText(prefs.getString("txt", ""));
        String url = "http://files.heweather.com/cond_icon/" + prefs.getString("code", "") + ".png";
        new DownImage(weatherPng).execute(url);
        temp1Text.setText(String.format("%s°", prefs.getString("max0", "")));
        temp2Text.setText(String.format("%s°", prefs.getString("min0", "")));
        weatherD2Text.setText(prefs.getString("weatherDesp1", ""));
        temp3Text.setText(String.format("%s°", prefs.getString("max1", "")));
        temp4Text.setText(String.format("%s°", prefs.getString("min1", "")));
        date2Text.setText(prefs.getString("date1", ""));
        weatherD3Text.setText(prefs.getString("weatherDesp2", ""));
        temp5Text.setText(String.format("%s°", prefs.getString("max2", "")));
        temp6Text.setText(String.format("%s°", prefs.getString("min2", "")));
        date3Text.setText(prefs.getString("date2", ""));
        weatherD4Text.setText(prefs.getString("weatherDesp3", ""));
        temp7Text.setText(String.format("%s°", prefs.getString("max3", "")));
        temp8Text.setText(String.format("%s°", prefs.getString("min3", "")));
        date4Text.setText(prefs.getString("date3", ""));
        weatherInfoLayout.setVisibility(View.VISIBLE);
        weatherInfo2Layout.setVisibility(View.VISIBLE);
        cityNameText.setVisibility(View.VISIBLE);
        Intent intent = new Intent(this, AutoUpdateService.class);
        startService(intent);
    }
}
