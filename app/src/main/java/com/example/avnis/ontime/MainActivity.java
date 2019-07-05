package com.example.avnis.ontime;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    CardView c1,c2,c3,c4,c5,c6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SQLiteDatabase am=openOrCreateDatabase("am",MODE_PRIVATE,null);
        am.execSQL("create table if not exists monday(time varchar,name varchar,dur varchar)");
        am.execSQL("create table if not exists tuesday(time varchar,name varchar,dur varchar)");
        am.execSQL("create table if not exists wednesday(time varchar,name varchar,dur varchar)");
        am.execSQL("create table if not exists friday(time varchar,name varchar,dur varchar)");
        am.execSQL("create table if not exists thursday(time varchar,name varchar,dur varchar)");
        am.execSQL("create table if not exists saturday(time varchar,name varchar,dur varchar)");
        am.execSQL("create table if not exists sunday(time varchar,name varchar,dur varchar)");
        am.execSQL("insert into monday values('8:30','MATH','2')");
        c1=(CardView)findViewById(R.id.card_view1);
        c2=(CardView)findViewById(R.id.card_view2);
        c3=(CardView)findViewById(R.id.card_view3);
        c4=(CardView)findViewById(R.id.card_view4);
        c5=(CardView)findViewById(R.id.card_view5);
        c6=(CardView)findViewById(R.id.card_view6);

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
}
