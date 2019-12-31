package com.example.avnis.ontime;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;


public class AboutUs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);


//        Log.e("Stat",todayString);

//        SQLiteDatabase am = openOrCreateDatabase("am", android.content.Context.MODE_PRIVATE, null);
//        String query = "select * from 'thursday'";
//
//        Cursor cursor = am.rawQuery(query, null);
//        Log.e("Stat",todayString+" "+cursor.getCount());
//
//        while(cursor.moveToNext())
//        {
//            String name = cursor.getString(1);
//            String time = cursor.getString(0);
//            Log.e("Stat",name+" "+time+" "+todayString);
//            String query1 = "select * from '"+name+"' where date='"+todayString+"' and time='"+time+"' ";
//            Cursor cursor1 = am.rawQuery(query1, null);
//            if(cursor1.getCount()==0)
//            {
//
//                Log.e("Stat","inside");
//                NotificationCompat.Builder builder = new NotificationCompat.Builder(this, MainActivity.CHANNEL_3_ID)
//                        .setSmallIcon(R.drawable.ic_launcher)
//                        .setContentTitle("Update Class Status")
//                        .setContentText("Please update the satus of today's classes")
//                        .setColor(ContextCompat.getColor(this, R.color.colorPrimaryDark))
//                        .setPriority(NotificationCompat.PRIORITY_HIGH);
//
//                int id = MainActivity.NotificationID.getID();
//                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
//                notificationManager.notify(id, builder.build());
//                break;
//            }
//        }
//        return;
    }
}