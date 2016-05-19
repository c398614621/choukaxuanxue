package com.example.app.activity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.app.R;
import com.example.app.util.VibratorUtil;

import java.util.Random;

/**
 * Created by chen on 2016/5/11.
 */
public class DrawActivity extends AppCompatActivity implements View.OnClickListener {

    private Spinner spinner;

    private final String[] str = new String[]{"1%", "2%", "3%", "4%", "5%", "6%", "7%", "8%",
            "9%", "10%"};   //震率

    private LinearLayout drawLayout;

    private Button btnDrawO;

    private Button btnDrawE;

    private TextView luckinessText;

    private TextView unluckinessText;

    private boolean flag;   //判断是否震了

    private String s;

    private MediaPlayer mp; //音乐播放器

    private Button play;

    private Button pause;

    private boolean p = false;  //判断音乐是否暂停

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.draw_layout);

        spinner = (Spinner) findViewById(R.id.spinner);
        drawLayout = (LinearLayout) findViewById(R.id.draw_layout);
        btnDrawO = (Button) findViewById(R.id.btn_draw_o);
        btnDrawE = (Button) findViewById(R.id.btn_draw_e);
        luckinessText = (TextView) findViewById(R.id.luckiness);
        unluckinessText = (TextView) findViewById(R.id.unluckiness);
        play = (Button) findViewById(R.id.play);
        pause = (Button) findViewById(R.id.pause);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, str);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                s = str[position];
                Log.d("s", s);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnDrawO.setOnClickListener(this);
        btnDrawE.setOnClickListener(this);
        play.setOnClickListener(this);
        pause.setOnClickListener(this);

        luckinessText.setClickable(true);
        unluckinessText.setClickable(true);

        luckinessText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawLayout.setVisibility(View.VISIBLE);
                luckinessText.setVisibility(View.INVISIBLE);
            }
        });

        unluckinessText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawLayout.setVisibility(View.VISIBLE);
                unluckinessText.setVisibility(View.INVISIBLE);
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_draw_o:
                drawLayout.setVisibility(View.INVISIBLE);
                drawO(s);
                break;
            case R.id.btn_draw_e:
                drawLayout.setVisibility(View.INVISIBLE);
                drawE(s);
                break;
            case R.id.play:
                playMusic();
                break;
            case R.id.pause:
                pauseMusic();
                break;
            default:
                break;
        }
    }

    /**
     * 一次抽卡
     */
    private void drawO(String s) {
        char t = s.charAt(0);
        Log.d("t", String.valueOf(t));
        Random rO = new Random();
        double tO = rO.nextGaussian();
        switch (String.valueOf(t)) {
            case "1":
                if (tO > 2.33) {
                    flag = true;
                    Log.d("o", "zl");

                } else {
                    flag = false;
                    Log.d("o", String.valueOf(tO));
                }
                break;
            case "2":
                if (tO > 2.05) {
                    flag = true;
                    Log.d("o", "zl");

                } else {
                    flag = false;
                    Log.d("o", String.valueOf(tO));
                }
                break;
            case "3":
                if (tO > 1.88) {
                    flag = true;
                    Log.d("o", "zl");

                } else {
                    flag = false;
                    Log.d("o", String.valueOf(tO));
                }
                break;
            case "4":
                if (tO > 1.75) {
                    flag = true;
                    Log.d("o", "zl");

                } else {
                    flag = false;
                    Log.d("o", String.valueOf(tO));
                }
                break;
            case "5":
                if (tO > 1.64) {
                    flag = true;
                    Log.d("o", "zl");

                } else {
                    flag = false;
                    Log.d("o", String.valueOf(tO));
                }
                break;
            case "6":
                if (tO > 1.55) {
                    flag = true;
                    Log.d("o", "zl");

                } else {
                    flag = false;
                    Log.d("o", String.valueOf(tO));
                }
                break;
            case "7":
                if (tO > 1.47) {
                    flag = true;
                    Log.d("o", "zl");

                } else {
                    flag = false;
                    Log.d("o", String.valueOf(tO));
                }
                break;
            case "8":
                if (tO > 1.41) {
                    flag = true;
                    Log.d("o", "zl");

                } else {
                    flag = false;
                    Log.d("o", String.valueOf(tO));
                }
                break;
            case "9":
                if (tO > 1.35) {
                    flag = true;
                    Log.d("o", "zl");

                } else {
                    flag = false;
                    Log.d("o", String.valueOf(tO));
                }
                break;
            case "10":
                if (tO > 1.26) {
                    flag = true;
                    Log.d("o", "zl");

                } else {
                    flag = false;
                    Log.d("o", String.valueOf(tO));
                }
                break;
            default:
                break;
        }
        if (flag) {
            VibratorUtil.Vibrate(this, 500);
            luckinessText.setVisibility(View.VISIBLE);
        } else {
            unluckinessText.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 十一连抽卡
     */
    private void drawE(String s) {
        char t = s.charAt(0);
        Log.d("t", String.valueOf(t));
        switch (String.valueOf(t)) {
            case "1":
                for (int i = 0; i < 11; i++) {
                    Random rE = new Random();
                    double tE = rE.nextGaussian();
                    Log.d("e", String.valueOf(tE));
                    if (tE > 2.33) {
                        flag = true;
                        Log.d("o", "zl");
                        break;
                    } else {
                        flag = false;
                    }
                }
                break;
            case "2":
                for (int i = 0; i < 11; i++) {
                    Random rE = new Random();
                    double tE = rE.nextGaussian();
                    Log.d("e", String.valueOf(tE));
                    if (tE > 2.05) {
                        flag = true;
                        Log.d("o", "zl");
                        break;
                    } else {
                        flag = false;
                    }
                }
                break;
            case "3":
                for (int i = 0; i < 11; i++) {
                    Random rE = new Random();
                    double tE = rE.nextGaussian();
                    Log.d("e", String.valueOf(tE));
                    if (tE > 1.88) {
                        flag = true;
                        Log.d("o", "zl");
                        break;
                    } else {
                        flag = false;
                    }
                }
                break;
            case "4":
                for (int i = 0; i < 11; i++) {
                    Random rE = new Random();
                    double tE = rE.nextGaussian();
                    Log.d("e", String.valueOf(tE));
                    if (tE > 1.76) {
                        flag = true;
                        Log.d("o", "zl");
                        break;
                    } else {
                        flag = false;
                    }
                }
                break;
            case "5":
                for (int i = 0; i < 11; i++) {
                    Random rE = new Random();
                    double tE = rE.nextGaussian();
                    Log.d("e", String.valueOf(tE));
                    if (tE > 1.64) {
                        flag = true;
                        Log.d("o", "zl");
                        break;
                    } else {
                        flag = false;
                    }
                }
                break;
            case "6":
                for (int i = 0; i < 11; i++) {
                    Random rE = new Random();
                    double tE = rE.nextGaussian();
                    Log.d("e", String.valueOf(tE));
                    if (tE > 1.55) {
                        flag = true;
                        Log.d("o", "zl");
                        break;
                    } else {
                        flag = false;
                    }
                }
                break;
            case "7":
                for (int i = 0; i < 11; i++) {
                    Random rE = new Random();
                    double tE = rE.nextGaussian();
                    Log.d("e", String.valueOf(tE));
                    if (tE > 1.47) {
                        flag = true;
                        Log.d("o", "zl");
                        break;
                    } else {
                        flag = false;
                    }
                }
                break;
            case "8":
                for (int i = 0; i < 11; i++) {
                    Random rE = new Random();
                    double tE = rE.nextGaussian();
                    Log.d("e", String.valueOf(tE));
                    if (tE > 1.41) {
                        flag = true;
                        Log.d("o", "zl");
                        break;
                    } else {
                        flag = false;
                    }
                }
                break;
            case "9":
                for (int i = 0; i < 11; i++) {
                    Random rE = new Random();
                    double tE = rE.nextGaussian();
                    Log.d("e", String.valueOf(tE));
                    if (tE > 1.35) {
                        flag = true;
                        Log.d("o", "zl");
                        break;
                    } else {
                        flag = false;
                    }
                }
                break;
            case "10":
                for (int i = 0; i < 11; i++) {
                    Random rE = new Random();
                    double tE = rE.nextGaussian();
                    Log.d("e", String.valueOf(tE));
                    if (tE > 1.26) {
                        flag = true;
                        Log.d("o", "zl");
                        break;
                    } else {
                        flag = false;
                    }
                }
                break;
            default:
                break;
        }
        if (flag) {
            VibratorUtil.Vibrate(this, 500);
            luckinessText.setVisibility(View.VISIBLE);
        } else {
            unluckinessText.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 播放音乐
     */
    private void playMusic() {
        if (p) {
            mp.start();
        } else {
            mp = MediaPlayer.create(this, R.raw.music);
            mp.start();
            mp.setLooping(true);
            p = false;
        }
    }

    /**
     * 暂停音乐
     */
    private void pauseMusic() {
        mp.pause();
        p = true;
    }

    @Override
    protected void onDestroy() {
        mp.stop();
        mp.release();
        super.onDestroy();
    }
}
