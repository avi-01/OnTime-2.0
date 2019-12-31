package com.example.avnis.ontime;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import static java.util.Calendar.DATE;
import static java.util.Calendar.FRIDAY;
import static java.util.Calendar.HOUR_OF_DAY;
import static java.util.Calendar.MINUTE;
import static java.util.Calendar.MONDAY;
import static java.util.Calendar.SATURDAY;
import static java.util.Calendar.SUNDAY;
import static java.util.Calendar.THURSDAY;
import static java.util.Calendar.TUESDAY;
import static java.util.Calendar.WEDNESDAY;

public class MainActivity extends AppCompatActivity {

    private static final String TAG= "MainActivity";
    CardView c1,c2,c3,c4,c5,c6;
    public String todayString;
    public static String CHANNEL_1_ID  = "reminder";
    public static String CHANNEL_2_ID  = "update";
    public static String CHANNEL_3_ID  = "dayReminder";
    public static int id = 1;
    public static int IntentId = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createNotificationChannel();




        SQLiteDatabase am=openOrCreateDatabase("am",MODE_PRIVATE,null);
        am.execSQL("create table if not exists extra(date varchar,time varchar,name varchar)");
        am.execSQL("create table if not exists monday(time varchar,name varchar,dur varchar,id_remind varchar,id_update varchar)");
        am.execSQL("create table if not exists tuesday(time varchar,name varchar,dur varchar,id_remind varchar,id_update varchar)");
        am.execSQL("create table if not exists wednesday(time varchar,name varchar,dur varchar,id_remind varchar,id_update varchar)");
        am.execSQL("create table if not exists friday(time varchar,name varchar,dur varchar,id_remind varchar,id_update varchar)");
        am.execSQL("create table if not exists thursday(time varchar,name varchar,dur varchar,id_remind varchar,id_update varchar)");
        am.execSQL("create table if not exists saturday(time varchar,name varchar,dur varchar,id_remind varchar,id_update varchar)");
        am.execSQL("create table if not exists sunday(time varchar,name varchar,dur varchar,id_remind varchar,id_update varchar)");
        am.execSQL("create table if not exists subjects(name varchar,tc int,ac int,per int)");
//        am.execSQL("insert into monday values('4:20','name','2')");

        c1=(CardView)findViewById(R.id.card_view1);
        c2=(CardView)findViewById(R.id.card_view2);
        c3=(CardView)findViewById(R.id.card_view3);
        c4=(CardView)findViewById(R.id.card_view4);
        c5=(CardView)findViewById(R.id.card_view5);
        c6=(CardView)findViewById(R.id.card_view6);

        c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                Integer Today = calendar.get(Calendar.DAY_OF_WEEK);
//                Today=Calendar.SUNDAY;

                String day=Integer.toString(calendar.DAY_OF_MONTH);
                String DAY;
                Date todayDate = Calendar.getInstance().getTime();
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                todayString = formatter.format(todayDate);
                Log.e("MainActivity",todayString);
                Log.e("MainActivity",Today.toString());
                switch (Today) {
                    case Calendar.SUNDAY:
                        DAY="sunday";
                        break;
                    case Calendar.MONDAY:
                        DAY="monday";
                        break;
                    case Calendar.TUESDAY:
                        DAY="tuesday";
                        break;
                    case Calendar.WEDNESDAY:
                        DAY="wednesday";
                        break;
                    case Calendar.THURSDAY:
                        DAY="thursday";
                        break;
                    case Calendar.FRIDAY:
                        DAY="friday";
                        break;
                    case Calendar.SATURDAY:
                        DAY="saturday";
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + Today);
                }

                Intent i1= new Intent(MainActivity.this, SundayUpdate.class);
                Bundle b1= new Bundle();
                b1.putString("date", todayString);
                b1.putString("day", DAY);
                i1.putExtras(b1);
                startActivity(i1);
            }
        });

        c2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this,AttendaceInfo.class);
                startActivity(i);
            }
        });
        c3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this,Subject.class);
                startActivity(i);
            }
        });
        c4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this,TimeTable.class);
                startActivity(i);
            }
        });

        c5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this,PrevEntry.class);
                startActivity(i);
            }
        });

        c6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this,AboutUs.class);
                startActivity(i);

            }
        });
        c1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction()==MotionEvent.ACTION_DOWN) {
                    c1.setCardElevation(TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP, 2, getApplicationContext().getResources().getDisplayMetrics()));
                }
                else
                {
                    c1.setCardElevation(TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP, 8, getApplicationContext().getResources().getDisplayMetrics()));
                }
                return false;
            }
        });
        c2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction()==MotionEvent.ACTION_DOWN) {
                    c2.setCardElevation(TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP, 2, getApplicationContext().getResources().getDisplayMetrics()));
                }
                else
                {
                    c2.setCardElevation(TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP, 8, getApplicationContext().getResources().getDisplayMetrics()));
                }
                return false;
            }
        });
        c3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction()==MotionEvent.ACTION_DOWN) {
                    c3.setCardElevation(TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP, 2, getApplicationContext().getResources().getDisplayMetrics()));
                }
                else
                {
                    c3.setCardElevation(TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP, 8, getApplicationContext().getResources().getDisplayMetrics()));
                }
                return false;
            }
        });
        c4.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction()==MotionEvent.ACTION_DOWN) {
                    c4.setCardElevation(TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP, 2, getApplicationContext().getResources().getDisplayMetrics()));
                }
                else
                {
                    c4.setCardElevation(TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP, 8, getApplicationContext().getResources().getDisplayMetrics()));
                }
                return false;
            }
        });
        c5.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction()==MotionEvent.ACTION_DOWN) {
                    c5.setCardElevation(TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP, 2, getApplicationContext().getResources().getDisplayMetrics()));
                }
                else
                {
                    c5.setCardElevation(TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP, 8, getApplicationContext().getResources().getDisplayMetrics()));
                }
                return false;
            }
        });
        c6.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction()==MotionEvent.ACTION_DOWN) {
                    c6.setCardElevation(TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP, 2, getApplicationContext().getResources().getDisplayMetrics()));
                }
                else
                {
                    c6.setCardElevation(TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP, 8, getApplicationContext().getResources().getDisplayMetrics()));
                }
                return false;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        checkNotification();
    }

    private void checkNotification() {

        checkForEachday("monday", MONDAY);
        checkForEachday("tuesday", TUESDAY);
        checkForEachday("wednesday", WEDNESDAY);
        checkForEachday("thursday", THURSDAY);
        checkForEachday("friday", FRIDAY);
        checkForEachday("saturday", SATURDAY);
        checkForEachday("sunday", SUNDAY);
        checkForDailyAlarm();
    }

    private void checkForDailyAlarm() {

        SQLiteDatabase am1=openOrCreateDatabase("am",MODE_PRIVATE,null);
        am1.execSQL("create table if not exists notify(name varchar,val varchar, val1 varchar)");
        String query1 = ("select * from notify where name='dayNotify'");
        Cursor cursor1 = am1.rawQuery(query1,null);
        int id1;
        int id2;

        Log.e("notify",cursor1.getCount()+"");
        if(!cursor1.moveToNext())
        {
            id1= NotificationID.getID();
            id2= NotificationID.getID()+1;
            am1.execSQL("insert into notify values('dayNotify','"+id1+"','"+id2+"')");
        }
        else {
            id1= cursor1.getInt(1);
            id2= cursor1.getInt(2);
        }

            Log.e("notify","Count = 0");

            Intent intent1 = new Intent(MainActivity.this, MyBroadcastReceiver.class);


            Calendar calendar = Calendar.getInstance();
            Integer Today = calendar.get(Calendar.DAY_OF_WEEK);
            String DAY;
            switch (Today) {
                case Calendar.SUNDAY:
                    DAY="sunday";
                    break;
                case Calendar.MONDAY:
                    DAY="monday";
                    break;
                case Calendar.TUESDAY:
                    DAY="tuesday";
                    break;
                case Calendar.WEDNESDAY:
                    DAY="wednesday";
                    break;
                case Calendar.THURSDAY:
                    DAY="thursday";
                    break;
                case Calendar.FRIDAY:
                    DAY="friday";
                    break;
                case Calendar.SATURDAY:
                    DAY="saturday";
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + Today);
            }
            Calendar calTime = Calendar.getInstance();
            long time = calTime.getTimeInMillis() + 21*60*60*1000 - (calTime.get(Calendar.HOUR_OF_DAY)*60 + calTime.get(Calendar.MINUTE))*60*1000;



            long time1 = calTime.getTimeInMillis() + 19*60*60*1000 - (calTime.get(Calendar.HOUR_OF_DAY)*60 + calTime.get(Calendar.MINUTE))*60*1000;
//            long time1= System.currentTimeMillis() + 10*1000;

            Log.e(TAG, "checkForDailyAlarm: "+time+" "+time1 );

            if(time1<System.currentTimeMillis())
            {
                time1 = time1 + 24*60*60*1000;
                time = time + 24*60*60*1000;
            }

            intent1.putExtra("type","day");
            intent1.putExtra("day",DAY);
            intent1.putExtra("alarm_time",time);


            PendingIntent pendingIntent1 = PendingIntent.getBroadcast(MainActivity.this, id1,intent1, 0);

            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);


            Intent intent2 = new Intent(MainActivity.this, MyBroadcastReceiver.class);
            intent2.putExtra("type","day");
            intent2.putExtra("day",DAY);
            intent2.putExtra("alarm_time",time1);

            PendingIntent pendingIntent2 = PendingIntent.getBroadcast(MainActivity.this, id2 ,intent2, 0);
            AlarmManager alarmManager1 = (AlarmManager) getSystemService(ALARM_SERVICE);


            alarmManager1.setExact(AlarmManager.RTC_WAKEUP, time1,pendingIntent2);
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, time,pendingIntent1);


            Log.e("DOne","done"+" "+time+" "+time1+" "+System.currentTimeMillis());

    }

    private void checkForEachday(String subjectDay, int day) {


        SQLiteDatabase am = openOrCreateDatabase("am",MODE_PRIVATE,null);
        Cursor cursor = am.rawQuery("select * from '"+subjectDay+"'",null);

        Calendar date1 = Calendar.getInstance();
        while (date1.get(Calendar.DAY_OF_WEEK) != day) {
            date1.add(DATE, 1);
        }

        System.out.println(Calendar.getInstance()+" "+ date1);


        while(cursor.moveToNext())
        {
            String times=cursor.getString(0);
            String name =cursor.getString(1);
            String dur=cursor.getString(2);
            Integer id_remind= Integer.valueOf(cursor.getString(3));
            Integer id_update= Integer.valueOf(cursor.getString(4));

            String[] time_A = times.split(":");
            long time = date1.getTimeInMillis() + ((Long.parseLong(time_A[0])*60 + Long.parseLong(time_A[1]))*60)*1000 - ((date1.get(HOUR_OF_DAY)*60 + date1.get(Calendar.MINUTE))*60)*1000;
            System.out.println(System.currentTimeMillis() +" "+time+"   "+Long.parseLong(time_A[0])+" "+Long.parseLong(time_A[1])+"   "+date1.get(HOUR_OF_DAY)+" "+date1.get(Calendar.MINUTE));
            System.out.println(((Long.parseLong(time_A[0])*60 + Long.parseLong(time_A[1]))*60)*1000 +"  "+((date1.get(HOUR_OF_DAY)*60 + date1.get(MINUTE))*60)*1000);

            if(time - 30*60*1000 < System.currentTimeMillis())
            {
                time = time + 7*24*60*60*1000 ;
            }

            Intent intent = new Intent(MainActivity.this, MyBroadcastReceiver.class);

            intent.putExtra("type","remind");
            intent.putExtra("name",name);
            intent.putExtra("time",times);
            intent.putExtra("day",subjectDay);
            intent.putExtra("id",id_remind);
            intent.putExtra("alarm_time",time - 30*60*1000);
            Log.e(TAG, "checkForEachday: "+(time - 30*60*1000) );

            PendingIntent pendingIntent = PendingIntent.getBroadcast(
                    MainActivity.this,id_remind ,intent, PendingIntent.FLAG_CANCEL_CURRENT|PendingIntent.FLAG_UPDATE_CURRENT);

            AlarmManager alarmManager = (AlarmManager) MainActivity.this.getSystemService(ALARM_SERVICE);
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, time - 30*60*1000,pendingIntent);


            Intent intent1 = new Intent(MainActivity.this, MyBroadcastReceiver.class);

            intent1.putExtra("type","update");
            intent1.putExtra("name",name);
            intent1.putExtra("time",times);
            intent1.putExtra("day",subjectDay);
            intent1.putExtra("id",id_update);
            intent1.putExtra("alarm_time",time + 30*60*1000);

            PendingIntent pendingIntent1 = PendingIntent.getBroadcast(
                    MainActivity.this, id_update,intent1, PendingIntent.FLAG_CANCEL_CURRENT|PendingIntent.FLAG_UPDATE_CURRENT);


            alarmManager.setExact(AlarmManager.RTC_WAKEUP, time + 30*60*1000,pendingIntent1);

        }
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Reminder";
            String description = "Notify classes";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_1_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);


            CharSequence name2 = "Update";
            String description2 = "Notify to update classes attendance";
            int importance2 = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel2 = new NotificationChannel(CHANNEL_2_ID, name2, importance2);
            channel2.setDescription(description2);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            notificationManager.createNotificationChannel(channel2);


            CharSequence name3 = "DayReminder";
            String description3 = "Update Class reminder at the end of the day";
            int importance3 = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel3 = new NotificationChannel(CHANNEL_3_ID, name3, importance3);
            channel3.setDescription(description3);
            notificationManager.createNotificationChannel(channel3);
        }
    }

    public static class NotificationID {
        public static int getID() {
            Date now = new Date();
            int id = Integer.parseInt(new SimpleDateFormat("ddHHSS",  Locale.US).format(now));
            id +=(int) (Calendar.getInstance().getTimeInMillis()%10000000);
            id%=1000000007;
            return id;
        }
    }

}
