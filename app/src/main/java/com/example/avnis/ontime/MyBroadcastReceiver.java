package com.example.avnis.ontime;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.app.Notification.FLAG_AUTO_CANCEL;
import static android.content.Context.ALARM_SERVICE;


public class MyBroadcastReceiver extends BroadcastReceiver {

    String todayString;
    @Override
    public void onReceive(Context context, Intent intent) {

        Date todayDate = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        todayString = formatter.format(todayDate);


        String type = "";
        type = intent.getStringExtra("type");
        String day = "";
        day = intent.getStringExtra("day");

        Log.e("Recevier","Recieved"+" "+day+" "+type);

        if(type.equals("day")){

            Log.e("Type",type+" "+System.currentTimeMillis());

            Intent intent1 = new Intent(context,MyBroadcastReceiver.class);
            intent1.putExtra("type", type);
            intent1.putExtra("day", day);

            PendingIntent pendingIntent = PendingIntent.getBroadcast(
                    context, MainActivity.NotificationID.getID(), intent1, 0);


            AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 24*60*60*1000, pendingIntent);

            SQLiteDatabase am = context.openOrCreateDatabase("am", android.content.Context.MODE_PRIVATE, null);
            String query = "select * from '"+day+"'";
            Cursor cursor = am.rawQuery(query, null);

            while(cursor.moveToNext())
            {
                String name = cursor.getString(1);
                String time = cursor.getString(0);
                Log.e("Stat",name+" "+time+" "+todayString);
                String query1 = "select * from '"+name+"' where date='"+todayString+"' and time='"+time+"' ";
                Cursor cursor1 = am.rawQuery(query1, null);
                if(cursor1.getCount()==0)
                {

                    Log.e("Stat","inside");
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(context, MainActivity.CHANNEL_3_ID)
                            .setSmallIcon(R.drawable.ic_launcher)
                            .setContentTitle("Update Class Status")
                            .setContentText("Please update the satus of today's classes")
                            .setColor(ContextCompat.getColor(context, R.color.colorPrimaryDark))
                            .setPriority(NotificationCompat.PRIORITY_HIGH);

                    int id = MainActivity.NotificationID.getID();
                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
                    notificationManager.notify(id, builder.build());
                    break;
                }
            }
            return;
        }

        String name = "";
        name= intent.getStringExtra("name");
        String timeS = "";
        timeS = intent.getStringExtra("time");
        Log.e("Recevier","Recieved"+" "+name+" "+timeS+" "+day+" "+type);

        Intent intent1 = new Intent(context, MyBroadcastReceiver.class);

        SQLiteDatabase am = context.openOrCreateDatabase("am", android.content.Context.MODE_PRIVATE, null);
        String query = "select * from '"+day+"' where time='" + timeS + "' and name='" + name + "'";
        Cursor cursor = am.rawQuery(query, null);

        if(cursor.getCount()>0) {


            Log.e("Found", "Found");

            String query2 = "select * from '"+name+"' where time='" + timeS + "' and date='" + todayString + "'";
            Cursor cursor2 = am.rawQuery(query2, null);

            if(cursor2.getCount()==0){

                if (type.equals("remind")) {

                    String query1 = "select * from subjects where name=='" + name + "'";
                    Cursor cursor1 = am.rawQuery(query1, null);

                    cursor1.moveToNext();
                    String mes;
                    String acS = cursor1.getString(1);
                    String tcS = cursor1.getString(2);

                    Integer ac = Integer.parseInt(acS);
                    Integer tc = Integer.parseInt(tcS);
                    ac *= 100;
                    tc++;
                    Integer per = (Integer) ac / tc;

                    if (per >= 75) {
                        mes = "You may this class";
                    } else {
                        mes = "You need to attend this class";
                    }
                    Log.e("Found", "remind");
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(context, MainActivity.CHANNEL_1_ID)
                            .setSmallIcon(R.drawable.ic_launcher)
                            .setContentTitle(timeS+" "+name)
                            .setContentText(mes)
                            .setColor(ContextCompat.getColor(context, R.color.colorPrimaryDark))
                            .setPriority(NotificationCompat.PRIORITY_HIGH);

                    int id = MainActivity.NotificationID.getID();
                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
                    notificationManager.notify(id, builder.build());
                    Log.e("ID:remind",id+"");

                    intent1.putExtra("name", name);
                    intent1.putExtra("type", type);
                    intent1.putExtra("time", timeS);
                    intent1.putExtra("day", day);

                    PendingIntent pendingIntent = PendingIntent.getBroadcast(
                            context, MainActivity.NotificationID.getID(), intent1, 0);

            AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000, pendingIntent);

                }
                else if(type.equals("update")){

                    int main_id = MainActivity.NotificationID.getID();
                    int id= MainActivity.NotificationID.getID();
                    Intent presentIntent = new Intent(context, MyBroadcastReceiver.class);
                    presentIntent.putExtra("name", name);
                    presentIntent.putExtra("type", "present");
                    presentIntent.putExtra("time", timeS);
                    presentIntent.putExtra("day", day);
                    presentIntent.putExtra("date", todayString);
                    presentIntent.putExtra("id", main_id);
                    PendingIntent presentPendingIntent =
                            PendingIntent.getBroadcast(context, id, presentIntent, PendingIntent.FLAG_CANCEL_CURRENT|PendingIntent.FLAG_UPDATE_CURRENT);

                    id = MainActivity.NotificationID.getID();
                    presentIntent.putExtra("type","absent");
                    PendingIntent absentPendingIntent =
                            PendingIntent.getBroadcast(context, id, presentIntent, PendingIntent.FLAG_CANCEL_CURRENT|PendingIntent.FLAG_UPDATE_CURRENT);

                    id=MainActivity.NotificationID.getID();
                    presentIntent.putExtra("type","cancel");
                    PendingIntent cancelPendingIntent =
                            PendingIntent.getBroadcast(context, id , presentIntent, PendingIntent.FLAG_CANCEL_CURRENT|PendingIntent.FLAG_UPDATE_CURRENT);

                    NotificationCompat.Builder builder = new NotificationCompat.Builder(context, MainActivity.CHANNEL_2_ID)
                            .setSmallIcon(R.drawable.ic_launcher)
                            .setContentTitle(timeS+" "+name)
                            .setContentText("Update the class status")
                            .setColor(ContextCompat.getColor(context, R.color.colorPrimaryDark))
                            .setPriority(NotificationCompat.PRIORITY_HIGH)
                            .addAction(R.drawable.present, "PRESENT",
                                    presentPendingIntent)
                            .addAction(R.drawable.absent, "ABSENT",
                                    absentPendingIntent)
                            .addAction(R.drawable.cancelled, "CANCELLED",
                                    cancelPendingIntent)
                            .setAutoCancel(true);

                    id=MainActivity.NotificationID.getID();
                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
                    notificationManager.notify(main_id, builder.build());
                    Log.e("ID",main_id+"");

                    intent1.putExtra("name", name);
                    intent1.putExtra("type", type);
                    intent1.putExtra("time", timeS);
                    intent1.putExtra("day", day);

                    PendingIntent pendingIntent = PendingIntent.getBroadcast(
                            context, MainActivity.IntentId, intent1, 0);
                    MainActivity.IntentId++;

            AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000, pendingIntent);

                }
                else if(type.equals("present")){
                    Integer notificationId = intent.getIntExtra("id",0);
                    String date = intent.getStringExtra("date");
                    Log.e("Detail",notificationId+" "+date+" "+type);


                    SundayUpdate.present(context,name,timeS,date);

                    NotificationManager notifManager= (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    notifManager.cancel(notificationId);
                }

                else if(type.equals("absent")){
                    Integer notificationId = intent.getIntExtra("id",0);
                    String date = intent.getStringExtra("date");
                    Log.e("Detail",notificationId+" "+date+" "+type);


                    SundayUpdate.absent(context,name,timeS,date);

                    NotificationManager notifManager= (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    notifManager.cancel(notificationId);
                }

                else if(type.equals("cancel")){
                    Integer notificationId = intent.getIntExtra("id",0);
                    String date = intent.getStringExtra("date");
                    Log.e("Detail",notificationId+" "+date+" "+type);


                    SundayUpdate.cancel(context,name,timeS,date);

                    NotificationManager notifManager= (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    notifManager.cancel(notificationId);
                }
            }
        }
    }
}