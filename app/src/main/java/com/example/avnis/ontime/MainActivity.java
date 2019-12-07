package com.example.avnis.ontime;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;

import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG= "MainActivity";
    CardView c1,c2,c3,c4,c5,c6;
    public static String todayString;
    public static String CHANNEL_1_ID  = "reminder";
    public static String CHANNEL_2_ID  = "update";
    public static int id = 1;
    public static int IntentId = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createNotificationChannel();


        SQLiteDatabase am=openOrCreateDatabase("am",MODE_PRIVATE,null);
        am.execSQL("create table if not exists extra(date varchar,time varchar,name varchar)");
        am.execSQL("create table if not exists monday(time varchar,name varchar,dur varchar)");
        am.execSQL("create table if not exists tuesday(time varchar,name varchar,dur varchar)");
        am.execSQL("create table if not exists wednesday(time varchar,name varchar,dur varchar)");
        am.execSQL("create table if not exists friday(time varchar,name varchar,dur varchar)");
        am.execSQL("create table if not exists thursday(time varchar,name varchar,dur varchar)");
        am.execSQL("create table if not exists saturday(time varchar,name varchar,dur varchar)");
        am.execSQL("create table if not exists sunday(time varchar,name varchar,dur varchar)");
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
