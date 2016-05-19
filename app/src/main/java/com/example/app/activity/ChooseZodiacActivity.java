package com.example.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.app.R;

/**
 * Created by chen on 2016/5/14.
 */
public class ChooseZodiacActivity extends AppCompatActivity implements View.OnClickListener {

    private Button aries;

    private Button taurus;

    private Button gemini;

    private Button cancer;

    private Button leo;

    private Button virgo;

    private Button libra;

    private Button scorpio;

    private Button sagittarius;

    private Button capricorn;

    private Button aquarius;

    private Button pisces;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_zodiac);

        aries = (Button) findViewById(R.id.aries);
        taurus = (Button) findViewById(R.id.taurus);
        gemini = (Button) findViewById(R.id.gemini);
        cancer = (Button) findViewById(R.id.cancer);
        leo = (Button) findViewById(R.id.leo);
        virgo = (Button) findViewById(R.id.virgo);
        libra = (Button) findViewById(R.id.libra);
        scorpio = (Button) findViewById(R.id.scorpio);
        sagittarius = (Button) findViewById(R.id.sagittarius);
        capricorn = (Button) findViewById(R.id.capricorn);
        aquarius = (Button) findViewById(R.id.aquarius);
        pisces = (Button) findViewById(R.id.pisces);

        aries.setOnClickListener(this);
        taurus.setOnClickListener(this);
        gemini.setOnClickListener(this);
        cancer.setOnClickListener(this);
        leo.setOnClickListener(this);
        virgo.setOnClickListener(this);
        libra.setOnClickListener(this);
        scorpio.setOnClickListener(this);
        sagittarius.setOnClickListener(this);
        capricorn.setOnClickListener(this);
        aquarius.setOnClickListener(this);
        pisces.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.aries:
                Intent i1 = new Intent(this, HoroscopeActivity.class);
                i1.putExtra("zodiac", "白羊座");
                startActivity(i1);
                break;
            case R.id.taurus:
                Intent i2 = new Intent(this, HoroscopeActivity.class);
                i2.putExtra("zodiac", "金牛座");
                startActivity(i2);
                break;
            case R.id.gemini:
                Intent i3 = new Intent(this, HoroscopeActivity.class);
                i3.putExtra("zodiac", "双子座");
                startActivity(i3);
                break;
            case R.id.cancer:
                Intent i4 = new Intent(this, HoroscopeActivity.class);
                i4.putExtra("zodiac", "巨蟹座");
                startActivity(i4);
                break;
            case R.id.leo:
                Intent i5 = new Intent(this, HoroscopeActivity.class);
                i5.putExtra("zodiac", "狮子座");
                startActivity(i5);
                break;
            case R.id.virgo:
                Intent i6 = new Intent(this, HoroscopeActivity.class);
                i6.putExtra("zodiac", "处女座");
                startActivity(i6);
                break;
            case R.id.libra:
                Intent i7 = new Intent(this, HoroscopeActivity.class);
                i7.putExtra("zodiac", "天秤座");
                startActivity(i7);
                break;
            case R.id.scorpio:
                Intent i8 = new Intent(this, HoroscopeActivity.class);
                i8.putExtra("zodiac", "天蝎座");
                startActivity(i8);
                break;
            case R.id.sagittarius:
                Intent i9 = new Intent(this, HoroscopeActivity.class);
                i9.putExtra("zodiac", "射手座");
                startActivity(i9);
                break;
            case R.id.capricorn:
                Intent i10 = new Intent(this, HoroscopeActivity.class);
                i10.putExtra("zodiac", "摩羯座");
                startActivity(i10);
                break;
            case R.id.aquarius:
                Intent i11 = new Intent(this, HoroscopeActivity.class);
                i11.putExtra("zodiac", "水瓶座");
                startActivity(i11);
                break;
            case R.id.pisces:
                Intent i12 = new Intent(this, HoroscopeActivity.class);
                i12.putExtra("zodiac", "双鱼座");
                startActivity(i12);
                break;
            default:
                break;
        }
    }
}
