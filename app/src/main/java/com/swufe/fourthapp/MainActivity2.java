package com.swufe.fourthapp;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;

public class MainActivity2 extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    EditText out1,out2,out3;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        out1 = findViewById(R.id.out1);
        out2 = findViewById(R.id.out2);
        out3 = findViewById(R.id.out3);

        Intent intent = getIntent();

        float dollar2 = intent.getFloatExtra("dollar_rate_key",0.0f);
        float euro2 = intent.getFloatExtra("euro_rate_key",0.0f);
        float won2 = intent.getFloatExtra("won_rate_key",0.0f);

        Log.i(TAG,"onCreate: dollar2=" + dollar2);
        Log.i(TAG,"onCreate: euro2=" + euro2);
        Log.i(TAG,"onCreate: won2=" + won2);

        //将利率显示到第二个页面
        out1.setText("" + dollar2);
        out2.setText("" + euro2);
        out3.setText("" + won2);

        Thread t = new Thread();
        t.start();
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg){
                if(msg.what==5){
                    String str = (String)msg.obj;
                    Log.i(TAG, "handleMessage: getMessage msg = "+str);
                    //show.setText(str);
                }
                super.handleMessage(msg);
            }
        };
    }

    private String inputStream2String(InputStream inputStream) throws IOException{
        final int bufferSize = 1024;
        final char[] buffer = new char[bufferSize];
        final StringBuilder out = new StringBuilder();
        Reader in = new InputStreamReader(inputStream, "gb2312");
        while(true){
            int rsz = in.read(buffer,0, buffer.length);
            if(rsz < 0)
                break;
            out.append(buffer,0, rsz);
        }
        return out.toString();
    }

    //@Override
    public void run(){
        Log.i(TAG,"run: run()......");

        //获取Msg对象，用于返回主线程
        Message msg = handler.obtainMessage(5);
        //msg.what = 5;
        msg.obj = "Hello from run()";
        handler.sendMessage(msg);

//        URL url = null;
//        try{
//            url = new URL("https://www.usd-cny.com/bankofchina.htm");
//            HttpURLConnection http = (HttpURLConnection) url.openConnection();
//            InputStream in = http.getInputStream();
//
//            String html = inputStream2String(in);
//            Log.i(TAG,"run: html=" + html);

            String url = "https://www.usd-cny.com/bankofchina.htm";
            try {
            Document doc = Jsoup.connect(url).get();
            Log.i(TAG, "run: " + doc.title());
            Elements tables = doc.getElementsByTag("table");

            org.jsoup.nodes.Element table6 = tables.get(5);
            //获取TD中的数据
            Elements tds = table6.getElementsByTag("td");
            for(int i=0;i<tds.size();i+=8){
                org.jsoup.nodes.Element td1 = tds.get(i);
                Element td2 = tds.get(i+5);

                String str1 = td1.text();
                String val = td2.text();
                Log.i(TAG, "run: " + str1 + "==>" + val);

                float v = 1000f / Float.parseFloat(val);
            }

        } catch(MalformedURLException e){
            e.printStackTrace();
        } catch(IOException e){
            e.printStackTrace();
        }
    }

}