package com.example.avnis.ontime;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;


public class Subject extends AppCompatActivity {
    Button add;
    Dialog md;
    Dialog md1;
    Dialog md2;
    ListView list;
    ArrayList<String> listItem;
    ArrayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);

//        Intent intent3 = new Intent(Subject.this, MyBroadcastReceiver.class);
//        intent3.putExtra("type","day");
//        intent3.putExtra("day","thursday");
//
//        PendingIntent pendingIntent3 = PendingIntent.getBroadcast(Subject.this, MainActivity.NotificationID.getID(),intent3, 0);
//        AlarmManager alarmManager3 = (AlarmManager) getSystemService(ALARM_SERVICE);
//
//        alarmManager3.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 5*1000,pendingIntent3);

//        Calendar calTime1 = Calendar.getInstance();
//        long time3 = calTime1.getTimeInMillis() + 21*60*60*1000 - (calTime1.get(Calendar.HOUR_OF_DAY)*60 + calTime1.get(Calendar.MINUTE))*60*1000;
//
//        Log.e("DATE",getDate(time3, "dd/MM/yyyy HH:mm:ss.SSS"));



        SQLiteDatabase am=openOrCreateDatabase("am",MODE_PRIVATE,null);
        am.execSQL("create table if not exists subjects(name varchar,tc int,ac int,per int)");
        add=(Button)findViewById(R.id.button);
        md=new Dialog(this);
        md1=new Dialog(this);
        md2=new Dialog(this);
        list=(ListView)findViewById(R.id.listView);
        listItem=new ArrayList<>();
        viewData();
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                editData(adapterView,view,i,l);

            }
        });
    }

    private void editData(final AdapterView<?> adapterView, View view, final int i, final long l) {
        md1.setContentView(R.layout.editsubject);
        md1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ImageButton close1;
        final EditText change;
        Button remove,edit;
        md1.show();
        change=(EditText)md1.findViewById(R.id.editText2);
        String current=list.getItemAtPosition(i).toString();
        change.setText(current);
        remove=(Button)md1.findViewById(R.id.remove);
        edit=(Button)md1.findViewById(R.id.button3);
        close1=(ImageButton)md1.findViewById(R.id.imageButton2);
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeData(adapterView,view,i);
                md1.dismiss();
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String cname = change.getText().toString();
                    if (cname.equals("")) {
                        Toast.makeText(Subject.this, "Enter the name", Toast.LENGTH_SHORT).show();
                    }
                    SQLiteDatabase am = openOrCreateDatabase("am", MODE_PRIVATE, null);
                    String name = list.getItemAtPosition(i).toString();
                    String query="select * from subjects where name=='"+cname+"'";
                    Cursor cursor=am.rawQuery(query,null);
                    if(cursor.getCount()>0)
                    {
                        Toast.makeText(Subject.this, "Subject Already Exits", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    am.execSQL("update subjects  set name='" + cname + "' where name=='" + name + "'");
                    for (int i = 0; i < 7; i++) {
                        String lday = "";
                        if (i == 0) {
                            lday = "sunday";
                        } else if (i == 1) {
                            lday = "monday";
                        } else if (i == 2) {
                            lday = "wednesday";
                        } else if (i == 3) {
                            lday = "thursday";
                        } else if (i == 4) {
                            lday = "friday";
                        } else if (i == 5) {
                            lday = "saturday";
                        } else if (i == 6) {
                            lday = "tuesday";
                        }
                        am.execSQL("update '" + lday + "' set name='" + cname + "' where name=='" + name + "'");

                    }

                    am.execSQL("update extra set name='" + cname + "' where name=='" + name + "'");
                    am.execSQL("ALTER TABLE '" + name + "' RENAME TO '" + cname + "'");
                    listItem.clear();
                    viewData();
                    md1.dismiss();
                }catch (Exception e)
                {
                    Log.e("EXCEPTION", "onClick: " + e);
                }
            }
        });
        close1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                md1.dismiss();
            }
        });
    }


    private void removeData(AdapterView<?> adapterView, View view, final int i) {
        md2.setContentView(R.layout.activity_remove_subject);
        md2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ImageButton close1;
        Button remove,cancel;
        TextView warning;
        md2.show();
        remove=(Button)md2.findViewById(R.id.remove);
        cancel=(Button)md2.findViewById(R.id.cancel);
        close1=(ImageButton)md2.findViewById(R.id.imageButton3);
        warning= (TextView)md2.findViewById(R.id.textView);

        String name=list.getItemAtPosition(i).toString();
        warning.setText("Do you want to remove the subject '"+name+"'");
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase am=openOrCreateDatabase("am",MODE_PRIVATE,null);
                String name=list.getItemAtPosition(i).toString();
                am.execSQL("delete from subjects where name=='"+name+"'");
                for(int i=0;i<7;i++)
                {
                    String lday = "";
                    if(i==0)
                    {
                        lday= "sunday";
                    }
                    else if(i==1)
                    {
                        lday="monday";
                    }
                    else if(i==2)
                    {
                        lday="wednesday";
                    }
                    else if(i==3)
                    {
                        lday="thursday";
                    }
                    else if(i==4)
                    {
                        lday="friday";
                    }
                    else if(i==5)
                    {
                        lday="saturday";
                    }
                    else if(i==6)
                    {
                        lday="tuesday";
                    }
                    am.execSQL("delete from '"+lday+"' where name=='"+name+"'");

                }
                am.execSQL("delete from extra where name=='"+name+"'");
                listItem.clear();
                viewData();
                md2.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                md2.dismiss();
            }
        });
        close1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                md2.dismiss();
            }
        });
    }




    public void ShowPopup(View v) {
        md.setContentView(R.layout.addsubject);
        md.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ImageButton close;
        final EditText subject;
        Button add;
        close=(ImageButton)md.findViewById(R.id.imageButton);
        add=(Button)md.findViewById(R.id.button2);
        subject=(EditText)md.findViewById(R.id.editText);
        md.show();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s=subject.getText().toString();
                if(s.equals(""))
                {
                    Toast.makeText(Subject.this, "Please Enter a Subject", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    SQLiteDatabase am=openOrCreateDatabase("am",MODE_PRIVATE,null);
                    am.execSQL("create table if not exists subjects(name varchar,tc varchar,ac varchar,per varchar)");
                    String query="select * from subjects where name=='"+s+"'";
                    Cursor cursor=am.rawQuery(query,null);
                    if(cursor.getCount()>0)
                    {
                        Toast.makeText(Subject.this, "Subject Already Exits", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        am.execSQL("insert into subjects values('"+s+"','0','0','0')");
                        am.execSQL("create table if not exists '"+s+"'(date varchar,time varchar,status varchar)");
                        Toast.makeText(Subject.this,"Subject Added", Toast.LENGTH_SHORT).show();
                        listItem.clear();
                        viewData();
                        md.dismiss();
                    }
                }




            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                md.dismiss();
            }
        });

    }

    public void viewData(){
        SQLiteDatabase am=openOrCreateDatabase("am",MODE_PRIVATE,null);
        String query="select * from subjects";
        Cursor cursor=am.rawQuery(query,null);
        while(cursor.moveToNext())
        {
            listItem.add(cursor.getString(0));
        }
        adapter= new ArrayAdapter<>(this,R.layout.listlay,R.id.list_content7,listItem);
        list.setAdapter(adapter);
    }

    private void setOnClick(final Button btn, final int i){
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Do whatever you want(str can be used here)

            }
        });
    }

    public static String getDate(long milliSeconds, String dateFormat)
    {
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }
}
