package com.swufe.fourthapp;

import android.app.Activity;
import android.app.ListActivity;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class RateListActivity extends ListActivity implements Runnable {

    private static final String TAG = "RateListActivity";
    ListView ratelist;
    Handler handler;
    String olddate,newdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_list);

//        String[] list_data = {"阿拉酋地拉姆==>184.22","澳大利亚元==>504.4","巴西里亚尔==>182.89","加拿大元==>518.99","瑞士法郎==>682.74","丹麦克朗==>106.55","欧元==>794.03","英镑==>892.97","港币==>86.25","印尼卢比==>0.0467","印度卢比==>9.8138","日元==>6.1029","韩国元==>0.6031"};
//        ListAdapter adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list_data);
//        setListAdapter(adapter);

        ratelist = (ListView) findViewById(R.id.ratelist);

        //开启子线程
        Thread t = new Thread();
        t.start();

        //实现进程间消息同步
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 5) {
                    //listItems = new ArrayList<>
                    ArrayList<HashMap<String, String>> listItems = (ArrayList<HashMap<String, String>>) msg.obj;
                    SimpleAdapter listItemAdapter = new SimpleAdapter(RateListActivity.this, listItems, R.layout.rate_list_item,
                            new String[]{"ItemTitle", "ItemDetail"}, new int[]{R.id.itemTitle, R.id.itemDetail});
                    ratelist.setAdapter(listItemAdapter);
                    //String str = (String) msg.obj;
                    //Log.i(TAG, "handleMessage:getMessage meg = " + str);
                }
                super.handleMessage(msg);
            }
        };

        // 每天更新一次汇率
        // 判断Myrate文件里面所存的日期与现在的日期对比，判断是否更新
        SharedPreferences sp = getSharedPreferences("Myrate", Activity.MODE_PRIVATE);
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        newdate = format.format(date);
        olddate = sp.getString("date", "");
        Log.i(TAG,"onCreate:the old_date=" + olddate);
        //如果日期不匹配，则更新
        if(!newdate.equals(olddate)){
            Log.i(TAG,"onCreate:the new_date=" + newdate);
            //开启子线程
            Thread t1 = new Thread(this);
            t1.start();
            //线程间消息同步
            handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    if (msg.what == 5) {
                        List<String> list = (List<String>) msg.obj;
                        ListAdapter adapter = new ArrayAdapter<String>(RateListActivity.this, android.R.layout.simple_list_item_1, list);
                        ratelist.setAdapter(adapter);

                        //String str = (String) msg.obj;
                        //Log.i(TAG, "handleMessage:getMessage meg = " + str);
                    }
                    super.handleMessage(msg);

                }
            };
        }
    }


    @Override
    public void run() {
        Log.i(TAG, "run:run()......");
        Message msg = handler.obtainMessage(5);
        try {
            String url = "http://www.usd-cny.com/bankofchina.htm";
            Document doc = Jsoup.connect(url).get();
            Log.i(TAG, "run: " + doc.title());
            Elements tables = doc.getElementsByTag("table");
            Element table0 = tables.get(0);
            // 获取 TD 中的数据
            Elements tds = table0.getElementsByTag("td");
            //List<String> list2 = new ArrayList<String>();
            ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
            for (int i = 0; i < tds.size(); i += 6) {
                Element td1 = tds.get(i);
                Element td2 = tds.get(i + 5);
                String str1 = td1.text();
                String val = td2.text();
                Log.i(TAG, "run: " + str1 + "==>" + val);
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("ItemTitle", str1);
                map.put("ItemDetail", val);
                list.add(map);
                float v = 100f / Float.parseFloat(val);
                float rate = (float) (Math.round(v * 100)) / 100;
            }
            msg.obj = list;
            handler.sendMessage(msg);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}



