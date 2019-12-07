package com.example.avnis.ontime;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class InfoDetail extends AppCompatActivity {

    ListView list;
    ArrayList<InfoDetailType> listItem;
    TextView header;
    String subject="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_detail);
        list = (ListView)findViewById(R.id.listView2);
        listItem = new ArrayList<InfoDetailType>();
        header = (TextView)findViewById(R.id.textView7);
        Bundle b = getIntent().getExtras();
        if(b!=null)
        {
            subject = b.getString("subject");
            Log.e("TAG",subject);
        }
        header.setText(subject);

        SQLiteDatabase am=openOrCreateDatabase("am",MODE_PRIVATE,null);
        String query= String.format("select * from %s", subject);
        Cursor cursor=am.rawQuery(query,null);
        while(cursor.moveToNext())
        {
            String date,time,statusCode,status;
            date = cursor.getString(0);
            time = cursor.getString(1);
            statusCode = cursor.getString(2);
            Log.e("Tag",date);
            Log.e("Tag",time);
            Log.e("Tag",statusCode);
            if(statusCode.equals("1"))
                status = "Present";
            else if(statusCode.equals("0"))
                status = "Absent";
            else
                status = "Cancelled";

            Log.e("Tag",statusCode);
            InfoDetailType a = new InfoDetailType(date,time,status);
            listItem.add(a);

            Log.e("Tag","-1");
        }

        Collections.sort(listItem,new Comparator<InfoDetailType>() {
            public int compare(InfoDetailType a, InfoDetailType b) {
                String time1=a.getTime();
                String date1= a.getDate();

                String time2=b.getTime();
                String date2 = b.getDate();

                String[] arrofDate1 = date1.split("/");
                Integer da11= Integer.parseInt(arrofDate1[0]);
                Integer da12= Integer.parseInt(arrofDate1[1]);
                Integer da13= Integer.parseInt(arrofDate1[2]);

                String[] arrOfStr = time1.split(":");
                Integer htime1=Integer.parseInt(arrOfStr[0]);
                Integer mtime1=Integer.parseInt(arrOfStr[1]);
                Integer starttime1=((((da13*12 + da12)*30 + da11)*24 + htime1)*60 + mtime1)*60;

                String[] arrofDate2 = date2.split("/");
                Integer da21= Integer.parseInt(arrofDate2[0]);
                Integer da22= Integer.parseInt(arrofDate2[1]);
                Integer da23= Integer.parseInt(arrofDate2[2]);

                String[] arrOfStr2 = time2.split(":");
                Integer htime2=Integer.parseInt(arrOfStr2[0]);
                Integer mtime2=Integer.parseInt(arrOfStr2[1]);

                Integer starttime2=((((da23*12 + da22)*30 + da21)*24 + htime2)*60 + mtime2)*60;
                return starttime2.compareTo(starttime1);
            }
        });

        InfoDetailAdapter adapter = new InfoDetailAdapter(this, R.layout.detail_layout, listItem);
        list.setAdapter(adapter);

    }
}
