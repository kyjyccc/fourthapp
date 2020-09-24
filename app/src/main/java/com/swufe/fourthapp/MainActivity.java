package com.swufe.fourthapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    EditText inp;
    TextView out;
    private Button button1,button2,button3,button4;
    Double dollarRate = 0.1477,euroRate = 0.1256,wonRate = 171.3421;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inp = findViewById(R.id.inp);
        out = findViewById(R.id.out);
        ClickButton();
    }

    private void ClickButton() {
        //获取按钮id
        button1=findViewById(R.id.button1);
        button2=findViewById(R.id.button2);
        button3=findViewById(R.id.button3);
        button4=findViewById(R.id.button4);

        //给按钮绑定事件实现监听
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
    }

    //public  void open(View v)
    public void open(){
        Intent second = new Intent( this,MainActivity2.class);
        second.putExtra("dollar_rate_key",dollarRate);
        second.putExtra("euro_rate_key",euroRate);
        second.putExtra("won_rate_key",wonRate);

        Log.i(TAG,"openOne:dollarRate=" + dollarRate) ;
        Log.i(TAG,"openOne:euroRate=" + euroRate);
        Log.i(TAG,"openOne:wonRate=" + wonRate);

        startActivity(second);
    }

    public void transform(Double Rate){
        Double a = Double.parseDouble(inp.getText().toString());
        Double b = a * Rate;
        String str = String.valueOf(b);
        out.setText("汇率转换的结果为：" + str);
    }

    @Override
    public void onClick(View btn){
        if(inp.getText().toString()==null){
            //no input
            Toast.makeText(this, "请输入金额", Toast.LENGTH_SHORT).show();
        }
        else{
            if(btn.getId()==R.id.button1){
                transform(dollarRate);
            }
            else if(btn.getId()==R.id.button2){
                transform(euroRate);
            }
            else if(btn.getId()==R.id.button3){
                transform(wonRate);
            }
            else if(btn.getId()==R.id.button4) {
                open();
            }
            }
    }
}