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
 * Created by chen on 2016/5/18.
 */
public class LunarCalendarActivity extends AppCompatActivity {

    private static final String key = "0d53aec14af2416eb9d7c22e1ac12214";

    private TextView hours1;
    private TextView hours2;
    private TextView hours3;
    private TextView hours4;
    private TextView hours5;
    private TextView hours6;
    private TextView hours7;
    private TextView hours8;
    private TextView hours9;
    private TextView hours10;
    private TextView hours11;
    private TextView hours12;

    private TextView des1;
    private TextView des2;
    private TextView des3;
    private TextView des4;
    private TextView des5;
    private TextView des6;
    private TextView des7;
    private TextView des8;
    private TextView des9;
    private TextView des10;
    private TextView des11;
    private TextView des12;

    private TextView yi1;
    private TextView yi2;
    private TextView yi3;
    private TextView yi4;
    private TextView yi5;
    private TextView yi6;
    private TextView yi7;
    private TextView yi8;
    private TextView yi9;
    private TextView yi10;
    private TextView yi11;
    private TextView yi12;

    private TextView ji1;
    private TextView ji2;
    private TextView ji3;
    private TextView ji4;
    private TextView ji5;
    private TextView ji6;
    private TextView ji7;
    private TextView ji8;
    private TextView ji9;
    private TextView ji10;
    private TextView ji11;
    private TextView ji12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lunar_calendar);

        hours1 = (TextView) findViewById(R.id.hours1);
        hours2 = (TextView) findViewById(R.id.hours2);
        hours3 = (TextView) findViewById(R.id.hours3);
        hours4 = (TextView) findViewById(R.id.hours4);
        hours5 = (TextView) findViewById(R.id.hours5);
        hours6 = (TextView) findViewById(R.id.hours6);
        hours7 = (TextView) findViewById(R.id.hours7);
        hours8 = (TextView) findViewById(R.id.hours8);
        hours9 = (TextView) findViewById(R.id.hours9);
        hours10 = (TextView) findViewById(R.id.hours10);
        hours11 = (TextView) findViewById(R.id.hours11);
        hours12 = (TextView) findViewById(R.id.hours12);
        des1 = (TextView) findViewById(R.id.des1);
        des2 = (TextView) findViewById(R.id.des2);
        des3 = (TextView) findViewById(R.id.des3);
        des4 = (TextView) findViewById(R.id.des4);
        des5 = (TextView) findViewById(R.id.des5);
        des6 = (TextView) findViewById(R.id.des6);
        des7 = (TextView) findViewById(R.id.des7);
        des8 = (TextView) findViewById(R.id.des8);
        des9 = (TextView) findViewById(R.id.des9);
        des10 = (TextView) findViewById(R.id.des10);
        des11 = (TextView) findViewById(R.id.des11);
        des12 = (TextView) findViewById(R.id.des12);
        yi1 = (TextView) findViewById(R.id.yi1);
        yi2 = (TextView) findViewById(R.id.yi2);
        yi3 = (TextView) findViewById(R.id.yi3);
        yi4 = (TextView) findViewById(R.id.yi4);
        yi5 = (TextView) findViewById(R.id.yi5);
        yi6 = (TextView) findViewById(R.id.yi6);
        yi7 = (TextView) findViewById(R.id.yi7);
        yi8 = (TextView) findViewById(R.id.yi8);
        yi9 = (TextView) findViewById(R.id.yi9);
        yi10 = (TextView) findViewById(R.id.yi10);
        yi11 = (TextView) findViewById(R.id.yi11);
        yi12 = (TextView) findViewById(R.id.yi12);
        ji1 = (TextView) findViewById(R.id.ji1);
        ji2 = (TextView) findViewById(R.id.ji2);
        ji3 = (TextView) findViewById(R.id.ji3);
        ji4 = (TextView) findViewById(R.id.ji4);
        ji5 = (TextView) findViewById(R.id.ji5);
        ji6 = (TextView) findViewById(R.id.ji6);
        ji7 = (TextView) findViewById(R.id.ji7);
        ji8 = (TextView) findViewById(R.id.ji8);
        ji9 = (TextView) findViewById(R.id.ji9);
        ji10 = (TextView) findViewById(R.id.ji10);
        ji11 = (TextView) findViewById(R.id.ji11);
        ji12 = (TextView) findViewById(R.id.ji12);

        String now = getIntent().getStringExtra("date");
        if (!TextUtils.isEmpty(now)) {
            queryLunarCld(now);
        }
    }

    /**
     * 查询当天的良辰
     */
    private void queryLunarCld(String date) {
        String address = "http://apis.haoservice.com/lifeservice/laohuangli/h?date=" +
                date + "&key=" + key;
        //Log.d("address",address);
        queryFromServer(address);

    }

    private void queryFromServer(String address) {
        HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                Utility.handleLunarCldResponse(LunarCalendarActivity.this, response);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showLunarCld();
                    }
                });
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void showLunarCld() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        hours1.setText(prefs.getString("hours1", ""));
        hours2.setText(prefs.getString("hours2", ""));
        hours3.setText(prefs.getString("hours3", ""));
        hours4.setText(prefs.getString("hours4", ""));
        hours5.setText(prefs.getString("hours5", ""));
        hours6.setText(prefs.getString("hours6", ""));
        hours7.setText(prefs.getString("hours7", ""));
        hours8.setText(prefs.getString("hours8", ""));
        hours9.setText(prefs.getString("hours9", ""));
        hours10.setText(prefs.getString("hours10", ""));
        hours11.setText(prefs.getString("hours11", ""));
        hours12.setText(prefs.getString("hours12", ""));
        des1.setText(String.format("财喜：%s", prefs.getString("des1", "")));
        des2.setText(String.format("财喜：%s", prefs.getString("des2", "")));
        des3.setText(String.format("财喜：%s", prefs.getString("des3", "")));
        des4.setText(String.format("财喜：%s", prefs.getString("des4", "")));
        des5.setText(String.format("财喜：%s", prefs.getString("des5", "")));
        des6.setText(String.format("财喜：%s", prefs.getString("des6", "")));
        des7.setText(String.format("财喜：%s", prefs.getString("des7", "")));
        des8.setText(String.format("财喜：%s", prefs.getString("des8", "")));
        des9.setText(String.format("财喜：%s", prefs.getString("des9", "")));
        des10.setText(String.format("财喜：%s", prefs.getString("des10", "")));
        des11.setText(String.format("财喜：%s", prefs.getString("des11", "")));
        des12.setText(String.format("财喜：%s", prefs.getString("des12", "")));
        yi1.setText(String.format("宜：%s", prefs.getString("yi1", "")));
        yi2.setText(String.format("宜：%s", prefs.getString("yi2", "")));
        yi3.setText(String.format("宜：%s", prefs.getString("yi3", "")));
        yi4.setText(String.format("宜：%s", prefs.getString("yi4", "")));
        yi5.setText(String.format("宜：%s", prefs.getString("yi5", "")));
        yi6.setText(String.format("宜：%s", prefs.getString("yi6", "")));
        yi7.setText(String.format("宜：%s", prefs.getString("yi7", "")));
        yi8.setText(String.format("宜：%s", prefs.getString("yi8", "")));
        yi9.setText(String.format("宜：%s", prefs.getString("yi9", "")));
        yi10.setText(String.format("宜：%s", prefs.getString("yi10", "")));
        yi11.setText(String.format("宜：%s", prefs.getString("yi11", "")));
        yi12.setText(String.format("宜：%s", prefs.getString("yi12", "")));
        ji1.setText(String.format("忌：%s", prefs.getString("ji1", "")));
        ji2.setText(String.format("忌：%s", prefs.getString("ji2", "")));
        ji3.setText(String.format("忌：%s", prefs.getString("ji3", "")));
        ji4.setText(String.format("忌：%s", prefs.getString("ji4", "")));
        ji5.setText(String.format("忌：%s", prefs.getString("ji5", "")));
        ji6.setText(String.format("忌：%s", prefs.getString("ji6", "")));
        ji7.setText(String.format("忌：%s", prefs.getString("ji7", "")));
        ji8.setText(String.format("忌：%s", prefs.getString("ji8", "")));
        ji9.setText(String.format("忌：%s", prefs.getString("ji9", "")));
        ji10.setText(String.format("忌：%s", prefs.getString("ji10", "")));
        ji11.setText(String.format("忌：%s", prefs.getString("ji11", "")));
        ji12.setText(String.format("忌：%s", prefs.getString("ji12", "")));
    }
}
