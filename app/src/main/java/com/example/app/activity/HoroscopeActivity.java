package com.example.app.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.TextView;

import com.example.app.R;
import com.example.app.util.HttpCallbackListener;
import com.example.app.util.HttpUtil;
import com.example.app.util.Utility;

/**
 * Created by chen on 2016/5/14.
 */
public class HoroscopeActivity extends AppCompatActivity {

    private static final String key = "6dfead984f4a4e86c89dc8edffcca850";

    private TextView name;

    private TextView all;

    private TextView health;

    private TextView love;

    private TextView money;

    private TextView work;

    private TextView color;

    private TextView number;

    private TextView friend;

    private TextView summary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.horoscope_layout);

        name = (TextView) findViewById(R.id.name);
        all = (TextView) findViewById(R.id.all);
        health = (TextView) findViewById(R.id.health);
        love = (TextView) findViewById(R.id.love);
        money = (TextView) findViewById(R.id.money);
        work = (TextView) findViewById(R.id.work);
        color = (TextView) findViewById(R.id.color);
        number = (TextView) findViewById(R.id.number);
        friend = (TextView) findViewById(R.id.friend);
        summary = (TextView) findViewById(R.id.summary);

        String zodiac = getIntent().getStringExtra("zodiac");
        if (!TextUtils.isEmpty(zodiac)) {
            //有星座名字就去查询星座运势
            queryZodiac(zodiac);
        }
    }

    /**
     * 查询星座对应的运势
     */
    private void queryZodiac(String zodiac) {
        String address = "http://web.juhe.cn:8080/constellation/getAll?consName=" +
                zodiac + "&type=today&key=" + key;
        queryFromServer(address);
    }

    /**
     * 根据传入的地址去向服务器查询运势信息
     */
    private void queryFromServer(String address) {
        HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                Utility.handleHoroscopeResponse(HoroscopeActivity.this, response);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showHoroscope();
                    }
                });
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void showHoroscope() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        name.setText(prefs.getString("name", ""));
        all.setText(String.format("综合指数：%s", prefs.getString("all", "")));
        health.setText(String.format("健康指数：%s", prefs.getString("health", "")));
        love.setText(String.format("爱情指数：%s", prefs.getString("love", "")));
        money.setText(String.format("财运指数：%s", prefs.getString("money", "")));
        work.setText(String.format("工作指数：%s", prefs.getString("work", "")));
        color.setText(String.format("幸运色：%s", prefs.getString("color", "")));
        number.setText(String.format("幸运数字：%s", prefs.getString("number", "")));
        friend.setText(String.format("速配星座：%s", prefs.getString("friend", "")));
        summary.setText(String.format("今日概述：%s", prefs.getString("summary", "")));
    }
}
