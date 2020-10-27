package com.swufe.fourthapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class TemperatureActivity extends AppCompatActivity {

    TextView out;
    EditText inp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature);
        out = findViewById(R.id.out);
        inp = findViewById(R.id.inp);
    }

    public void btn(View v){
        String str1 = inp.getText().toString();
        Double a = Double.parseDouble(str1);
        Double b = a*1.8+32.0;
        String str2 = String.valueOf(b);
        out.setText("华氏温度为：" + str2);

    }
}