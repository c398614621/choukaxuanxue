package com.example.app.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.app.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn1;

    private Button btn2;

    private Button btn3;

    private Button btn4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn4 = (Button) findViewById(R.id.btn4);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn1:
                Intent i1 = new Intent(this, CalendarActivity.class);
                startActivity(i1);
                break;
            case R.id.btn2:
                Intent i2 = new Intent(this, ChooseZodiacActivity.class);
                startActivity(i2);
                break;
            case R.id.btn3:
                Intent i3 = new Intent(this, DrawActivity.class);
                startActivity(i3);
                break;
            case R.id.btn4:
                Intent i4 = new Intent(this, ChooseAreaActivity.class);
                startActivity(i4);
                break;
            default:
                break;
        }
    }
}
