package com.swufe.fourthapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class BasketballActivity extends AppCompatActivity implements View.OnClickListener {

    //定义数组存放分数
    private  final  int score[]={1,2,3};
    private  int lastScore_a,lastScore_b,score_a,score_b;
    private Button button1,button2,button3,button4,button5,button6;
    private Button button7;
    private TextView test_score_a,test_score_b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basketball);
        ClickButton();
    }

    private void ClickButton() {
        //获取按钮id
        button1=findViewById(R.id.button);
        button2=findViewById(R.id.button2);
        button3=findViewById(R.id.button3);
        button4=findViewById(R.id.button4);
        button5=findViewById(R.id.button5);
        button6=findViewById(R.id.button6);

        button7=findViewById(R.id.button7);

        //分数和重置
        test_score_a=findViewById(R.id.textView3);
        test_score_b=findViewById(R.id.textView4);

        //给按钮绑定事件实现监听
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        button6.setOnClickListener(this);

        button7.setOnClickListener(this);
    }

    //点击按钮进行加分和重置
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button:
                add(0,score[2]);
                break;
            case R.id.button2:
                add(0,score[1]);
                break;
            case R.id.button3:
                add(0,score[0]);
                break;
            case  R.id.button4:
                add(1,score[2]);
                break;
            case  R.id.button5:
                add(1,score[1]);
                break;
            case  R.id.button6:
                add(1,score[0]);
                break;
            case  R.id.button7:
                reset();
                break;
            default:
                break;
        }
    }

    //计算分数
    private   void  add(int Tage,int score){
        if (Tage==0||Tage==1){
            if (Tage==0){
                //TeamA加分
                lastScore_b=0;
                lastScore_a=score;
                score_a+=lastScore_a;
            }else if (Tage==1){
                //TeamB加分
                lastScore_a=0;
                lastScore_b=score;
                score_b+=lastScore_b;
            }
            ShowText();
        }
    }

    //实现重置
    private void reset() {
        score_a=0;
        score_b=0;
        ShowText();
    }

    //显示到文本框中
    private  void ShowText(){
        test_score_a.setText(Integer.toString(score_a));
        test_score_b.setText(Integer.toString(score_b));
    }
}
