package com.example.avnis.ontime;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class FridayUpdate extends AppCompatActivity {
    ListView list;
    ArrayList<UpdateListType> listItem;
    FloatingActionButton add;
    String date="";
    Dialog md,md2;
    String day="friday";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friday_update);
        md=new Dialog(this);
        md2=new Dialog(this);
        Bundle b = getIntent().getExtras();
        if(b!=null)
        {
            date=b.getString("date");
        }
        list=(ListView)findViewById(R.id.listView2);
        add=(FloatingActionButton)findViewById(R.id.fab);
        listItem=new ArrayList<>();
        viewData();
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ShowPopUp(adapterView,view,i,l);
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddExtraClass();
            }
        });
    }



    public void viewData(){
        listItem.clear();
        SQLiteDatabase am=openOrCreateDatabase("am",android.content.Context.MODE_PRIVATE,null);
        String query="select * from '"+day+"'";
        Cursor cursor=am.rawQuery(query,null);
        while(cursor.moveToNext())
        {
            int flag=0;
            String names,times,attes,mess,pers,statuss;
            names=cursor.getString(1);
            times=cursor.getString(0);
            String query2="select * from '"+names+"' where date=='"+date+"' and time=='"+times+"'";
            Cursor cursor2=am.rawQuery(query2,null);
            if(cursor2.getCount()>0)
            {
                flag=1;
                cursor2.moveToNext();
                String stat=cursor2.getString(2);
                if(stat.equals("0"))
                    statuss="ABSENT";
                else if(stat.equals("1"))
                    statuss="PRESENT";
                else
                {
                    statuss="CANCELLED";
                }
            }
            else
            {
                statuss="ADD ENTRY";
            }
            String query3="select * from subjects where name=='"+names+"'";
            Cursor cursor3=am.rawQuery(query3,null);
            if (cursor3.getCount()>0) {
                cursor3.moveToNext();
                String tc = cursor3.getString(1);
                String ac = cursor3.getString(2);
                attes = "Attendance " + ac + "/" + tc;
                Integer tci=Integer.parseInt(tc);
                Integer aci=Integer.parseInt(ac);
                tci++;
                Integer pper;
                pper=aci*100;
                pper=(Integer)pper/tci;
                if(pper<75)
                    mess = "You need to attend this class";
                else
                    mess = "You may leave this class today";
                pers = cursor3.getString(3)+"%";
                if(flag!=0)
                    mess="You set the entry for the class";
            }
            else
            {
                attes = "Attendance " + 0 + "/" + 0;
                mess = "You need to attend this class";
                pers = "0%";
            }
//            UpdateListType a=new UpdateListType("8:30","MATH","Attendance 0/0","You need to attend this class","75%","Present");
            UpdateListType a=new UpdateListType(times,names,attes,mess,pers,statuss);
            listItem.add(a);
        }
//        UpdateListType b=new UpdateListType("9:30","MATH","Attendance 0/0","You need to attend this class","75%","Present");
//        listItem.add(b);
//        UpdateListType c=new UpdateListType("7:30","MATH","Attendance 0/0","You need to attend this class","75%","Present");
//        listItem.add(c);
//        UpdateListType d=new UpdateListType("10:00","MATH","Attendance 0/0","You need to attend this class","75%","Present");
//        listItem.add(d);
//        UpdateListType e=new UpdateListType("1:30","MATH","Attendance 0/0","You need to attend this class","75%","Present");
//        listItem.add(e);
        Collections.sort(listItem,new Comparator<UpdateListType>() {
            @Override
            public int compare(UpdateListType a, UpdateListType b) {
                String time1=a.getTime();
                String[] arrOfStr = time1.split(":");
                Integer htime1=Integer.parseInt(arrOfStr[0]);
                Integer mtime1=Integer.parseInt(arrOfStr[1]);
                Integer starttime1=(htime1*60 + mtime1)*60;
                String time2=b.getTime();
                String[] arrOfStr2 = time2.split(":");
                Integer htime2=Integer.parseInt(arrOfStr2[0]);
                Integer mtime2=Integer.parseInt(arrOfStr2[1]);
                Integer starttime2=(htime2*60 + mtime2)*60;
                return starttime1.compareTo(starttime2);
            }
        });
        UpdateAdapter adapter= new UpdateAdapter(this,R.layout.update_list,listItem);
        list.setAdapter(adapter);
    }

//    class CustomAdapter extends BaseAdapter{
//
//
//        @Override
//        public View getView(int i, View view, ViewGroup viewGroup) {
//            view=getLayoutInflater().inflate(R.layout.update_list,null);
//            UpdateListType listset= (UpdateListType) listItem.get(i);
//            TextView timev=(TextView)view.findViewById(R.id.list_content7);
//            TextView namev=(TextView)view.findViewById(R.id.list_content8);
//            TextView attev=(TextView)view.findViewById(R.id.list_content9);
//            TextView mesv=(TextView)view.findViewById(R.id.list_content10);
//            TextView perv=(TextView)view.findViewById(R.id.list_content11);
//            TextView statusv=(TextView)view.findViewById(R.id.list_content12);
//            timev.setText(listset.getTime());
//            namev.setText(listset.getName());
//            attev.setText(listset.getAtt());
//            mesv.setText(listset.getMes());
//            perv.setText(listset.getPer());
//            String s=perv.getText().toString();
//            s=s.replace("%","");
//            Integer x=Integer.parseInt(s);
//            if(x>=75)
//            {
//                perv.setBackground(ContextCompat.getDrawable(FridayUpdate.this,R.drawable.per));
//            }
//            else
//            {
//                perv.setBackground(ContextCompat.getDrawable(FridayUpdate.this,R.drawable.per_red));
//            }
//            statusv.setText(listset.getStatus());
//            return view;
//        }
//
//        @Override
//        public int getCount() {
//            return listItem.size();
//        }
//
//        @Override
//        public Object getItem(int i) {
//            return null;
//        }
//
//        @Override
//        public long getItemId(int i) {
//            return 0;
//        }
//    }


    public void AddExtraClass() {
        md2.setContentView(R.layout.addextraclass);
        final Spinner sp;
        final EditText time,dur;
        sp = (Spinner) md2.findViewById(R.id.spinner);
        time = (EditText) md2.findViewById(R.id.editText);
        dur = (EditText) md2.findViewById(R.id.editText2);
        Button present=(Button)md2.findViewById(R.id.present);
        Button absent=(Button)md2.findViewById(R.id.absent);
        ImageButton close=(ImageButton)md2.findViewById(R.id.imageButton3);
        ArrayList<String> list = new ArrayList<String>();
        SQLiteDatabase am = openOrCreateDatabase("am", android.content.Context.MODE_PRIVATE, null);
        String query = "select * from subjects";
        Cursor cursor = am.rawQuery(query, null);
        while (cursor.moveToNext()) {
            list.add(cursor.getString(0));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_layout, R.id.txt, list);
        sp.setAdapter(adapter);
        md2.show();

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                md2.dismiss();
            }
        });


        present.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String subject = sp.getSelectedItem().toString();
                String times= time.getText().toString();
                String durs= dur.getText().toString();
                if(subject.equals("") || times.equals("") || durs.equals(""))
                {
                    Toast.makeText(FridayUpdate.this, "Fill Required", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    int d=1;
                    try{
                        Integer f=Integer.parseInt(durs);
                        if(f>=24 || f<=0)
                        {d=0;}
                    }catch (NumberFormatException e){
                        d=8;
                        Toast.makeText(FridayUpdate.this, "Write Duration in Correct Format(Hr)", Toast.LENGTH_SHORT).show();
                    }
                    String[] arrOfStr = times.split(":");
                    int b=0;
                    for(String ab :arrOfStr)
                    {
                        b=b+1;
                    }
                    if(b==2)
                    {

                        try {
                            Integer x = Integer.parseInt(arrOfStr[0]);
                            if (x >= 24) {
                                b = 100;
                            }
                        }catch(NumberFormatException e){
                            b=100;
                            Toast.makeText(FridayUpdate.this, "Write Time in Given Format", Toast.LENGTH_SHORT).show();}

                        try {
                            Integer x= Integer.parseInt(arrOfStr[1]);
                            if (x >= 60) {
                                b = 100;
                            }
                        }catch(NumberFormatException e){
                            b=100;
                            Toast.makeText(FridayUpdate.this, "Write Time in Given Format", Toast.LENGTH_SHORT).show();}
                    }
                    if(b!=2)
                    {
                        Toast.makeText(FridayUpdate.this, "Write Time in Given Format", Toast.LENGTH_SHORT).show();
                    }
                    else if(d!=1)
                    {
                        Toast.makeText(FridayUpdate.this, "Write Duration in Correct Format", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        SQLiteDatabase am = openOrCreateDatabase("am", android.content.Context.MODE_PRIVATE, null);
                        String query = "select * from '"+day+"' where time='" + times + "' and name='" + subject + "'";
                        Cursor cursor = am.rawQuery(query, null);
                        String query2="select * from '"+subject+"' where date=='"+date+"' and time=='"+times+"'";
                        Cursor cursor2=am.rawQuery(query2,null);
                        if (cursor.getCount() > 0) {
                            Toast.makeText(FridayUpdate.this, "Subject Already In the Timetable", Toast.LENGTH_SHORT).show();
                        }
                        else if(cursor2.getCount()>0) {
                            cursor2.moveToNext();
                            String stat = cursor2.getString(2);
                            am.execSQL("update '" + subject + "' set status='1' where date=='" + date + "' and time=='" + times + "'");
                            if (stat.equals("0")) {
                                String query5 = "select * from subjects where name=='" + subject + "'";
                                Cursor cursor5 = am.rawQuery(query5, null);
                                if (cursor5.getCount() > 0) {
                                    cursor5.moveToNext();
                                    String ac = cursor5.getString(2);
                                    String tc = cursor5.getString(1);
                                    Integer aci = Integer.parseInt(ac);
                                    Integer tci = Integer.parseInt(tc);
                                    aci++;
                                    Integer per = aci * 100;
                                    per = (Integer) per / tci;
                                    String pers = per.toString();
                                    String acs = aci.toString();
                                    am.execSQL("update subjects set ac='" + acs + "' where name=='" + subject + "'");
                                    am.execSQL("update subjects set per='" + pers + "' where name=='" + subject + "'");
                                }
                            }

                            Toast.makeText(FridayUpdate.this,"Extra Class Added for "+subject+"'PRESENT'", Toast.LENGTH_SHORT).show();
                            viewData();
                            md2.dismiss();
                        }
                        else {
                            am.execSQL("insert into '"+subject+"' values('"+date+"','"+times+"','1')");
                            String query1="select * from subjects where name=='"+subject+"'";
                            Cursor cursor1=am.rawQuery(query1,null);
                            if(cursor1.getCount()>0)
                            {
                                cursor1.moveToNext();
                                String ac = cursor1.getString(2);
                                String tc = cursor1.getString(1);
                                Integer aci=Integer.parseInt(ac);
                                Integer tci=Integer.parseInt(tc);
                                aci++;
                                tci++;
                                String acs=aci.toString();
                                String tcs=tci.toString();
                                Integer per=aci*100;
                                per=(Integer)per/tci;
                                String pers=per.toString();
                                am.execSQL("update subjects set ac='"+acs+"' where name=='"+subject+"'");
                                am.execSQL("update subjects set tc='"+tcs+"' where name=='"+subject+"'");
                                am.execSQL("update subjects set per='"+pers+"' where name=='"+subject+"'");
                            }
                            Toast.makeText(FridayUpdate.this,"Extra Class Added for "+subject+"'PRESENT'", Toast.LENGTH_SHORT).show();
//                            ((friday) getParentFragment()).viewData();
                            viewData();
                            md2.dismiss();
                        }

                    }
                }
            }
        });
        absent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String subject = sp.getSelectedItem().toString();
                String times= time.getText().toString();
                String durs= dur.getText().toString();
                if(subject.equals("") || times.equals("") || durs.equals(""))
                {
                    Toast.makeText(FridayUpdate.this, "Fill Required", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    int d=1;
                    try{
                        Integer f=Integer.parseInt(durs);
                        if(f>=24 || f<=0)
                        {d=0;}
                    }catch (NumberFormatException e){
                        d=8;
                        Toast.makeText(FridayUpdate.this, "Write Duration in Correct Format(Hr)", Toast.LENGTH_SHORT).show();
                    }
                    String[] arrOfStr = times.split(":");
                    int b=0;
                    for(String ab :arrOfStr)
                    {
                        b=b+1;
                    }
                    if(b==2)
                    {

                        try {
                            Integer x = Integer.parseInt(arrOfStr[0]);
                            if (x >= 24) {
                                b = 100;
                            }
                        }catch(NumberFormatException e){
                            b=100;
                            Toast.makeText(FridayUpdate.this, "Write Time in Given Format", Toast.LENGTH_SHORT).show();}

                        try {
                            Integer x= Integer.parseInt(arrOfStr[1]);
                            if (x >= 60) {
                                b = 100;
                            }
                        }catch(NumberFormatException e){
                            b=100;
                            Toast.makeText(FridayUpdate.this, "Write Time in Given Format", Toast.LENGTH_SHORT).show();}
                    }
                    if(b!=2)
                    {
                        Toast.makeText(FridayUpdate.this, "Write Time in Given Format", Toast.LENGTH_SHORT).show();
                    }
                    else if(d!=1)
                    {
                        Toast.makeText(FridayUpdate.this, "Write Duration in Correct Format", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        SQLiteDatabase am = openOrCreateDatabase("am", android.content.Context.MODE_PRIVATE, null);
                        String query = "select * from '"+day+"' where time=='" + times + "' and name=='" + subject + "'";
                        String query2="select * from '"+subject+"' where date=='"+date+"' and time=='"+times+"'";
                        Cursor cursor2=am.rawQuery(query2,null);
                        Cursor cursor = am.rawQuery(query, null);
                        if (cursor.getCount() > 0) {
                            Toast.makeText(FridayUpdate.this, "Subject Already In the Timetable", Toast.LENGTH_SHORT).show();
                        }
                        else if(cursor2.getCount()>0) {
                            cursor2.moveToNext();
                            String stat = cursor2.getString(2);
                            am.execSQL("update '" + subject + "' set status='0' where date=='" + date + "' and time=='" + times + "'");
                            if (stat.equals("1")) {
                                String query5 = "select * from subjects where name=='" + subject + "'";
                                Cursor cursor5 = am.rawQuery(query5, null);
                                if (cursor5.getCount() > 0) {
                                    cursor5.moveToNext();
                                    String ac = cursor5.getString(2);
                                    String tc = cursor5.getString(1);
                                    Integer aci = Integer.parseInt(ac);
                                    Integer tci = Integer.parseInt(tc);
                                    aci--;
                                    Integer per = aci * 100;
                                    per = (Integer) per / tci;
                                    String pers = per.toString();
                                    String acs = aci.toString();
                                    am.execSQL("update subjects set ac='" + acs + "' where name=='" + subject + "'");
                                    am.execSQL("update subjects set per='" + pers + "' where name=='" + subject + "'");
                                }
                            }
                            Toast.makeText(FridayUpdate.this,"Extra Class Added for "+subject+" 'ABSENT'", Toast.LENGTH_SHORT).show();
                            viewData();
                            md2.dismiss();
                        }
                        else {
                            am.execSQL("insert into '"+subject+"' values('"+date+"','"+times+"','0')");
                            Toast.makeText(FridayUpdate.this,"Extra Class Added for "+subject+" 'ABSENT'", Toast.LENGTH_SHORT).show();
//                            ((friday) getParentFragment()).viewData();
                            String query1="select * from subjects where name=='"+subject+"'";
                            Cursor cursor1=am.rawQuery(query1,null);
                            if(cursor1.getCount()>0)
                            {
                                cursor1.moveToNext();
                                String ac = cursor1.getString(2);
                                String tc = cursor1.getString(1);
                                Integer aci=Integer.parseInt(ac);
                                Integer tci=Integer.parseInt(tc);
                                tci++;
                                String acs=aci.toString();
                                String tcs=tci.toString();
                                Integer per=aci*100;
                                per=(Integer)per/tci;
                                String pers=per.toString();
                                am.execSQL("update subjects set ac='"+acs+"' where name=='"+subject+"'");
                                am.execSQL("update subjects set tc='"+tcs+"' where name=='"+subject+"'");
                                am.execSQL("update subjects set per='"+pers+"' where name=='"+subject+"'");
                            }
                            viewData();
                            md2.dismiss();
                        }
                    }
                }
            }
        });


    }
    public void ShowPopUp(AdapterView<?> adapterView, View v, int i, long l) {
        md.setContentView(R.layout.markatt);
        ImageButton close;
        TextView mes;
        Button present,absent,cancel;
        UpdateListType listget;
        listget= (UpdateListType) adapterView.getItemAtPosition(i);
        final String subjectname=listget.getName();
        final String subjecttime=listget.getTime();
//        String subjectname="MATH";
        mes=(TextView)md.findViewById(R.id.textView);
        close=(ImageButton)md.findViewById(R.id.imageButton3);
        present=(Button)md.findViewById(R.id.present);
        absent=(Button)md.findViewById(R.id.absent);
        cancel=(Button)md.findViewById(R.id.cancelled);
        md.show();
        mes.setText("Mark Attendance for "+subjectname);
        present.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SQLiteDatabase am=openOrCreateDatabase("am",android.content.Context.MODE_PRIVATE,null);
                String query="select * from '"+subjectname+"' where date=='"+date+"' and time=='"+subjecttime+"'";
                Cursor cursor=am.rawQuery(query,null);
                if(cursor.getCount()>0)
                {
                    cursor.moveToNext();
                    String stat=cursor.getString(2);
                    am.execSQL("update '"+subjectname+"' set status='1' where date=='"+date+"' and time=='"+subjecttime+"'");
                    if(stat.equals("0"))
                    {
                        String query1="select * from subjects where name=='"+subjectname+"'";
                        Cursor cursor1=am.rawQuery(query1,null);
                        if(cursor1.getCount()>0)
                        {
                            cursor1.moveToNext();
                            String ac = cursor1.getString(2);
                            String tc = cursor1.getString(1);
                            Integer aci=Integer.parseInt(ac);
                            Integer tci=Integer.parseInt(tc);
                            aci++;
                            Integer per=aci*100;
                            per=(Integer)per/tci;
                            String pers=per.toString();
                            String acs=aci.toString();
                            am.execSQL("update subjects set ac='"+acs+"' where name=='"+subjectname+"'");
                            am.execSQL("update subjects set per='"+pers+"' where name=='"+subjectname+"'");
                        }
                    }
                    else if(stat.equals("2"))
                    {
                        String query1="select * from subjects where name=='"+subjectname+"'";
                        Cursor cursor1=am.rawQuery(query1,null);
                        if(cursor1.getCount()>0)
                        {
                            cursor1.moveToNext();
                            String ac = cursor1.getString(2);
                            String tc = cursor1.getString(1);
                            Integer aci=Integer.parseInt(ac);
                            Integer tci=Integer.parseInt(tc);
                            aci++;
                            tci++;
                            String acs=aci.toString();
                            String tcs=tci.toString();
                            Integer per=aci*100;
                            per=(Integer)per/tci;
                            String pers=per.toString();
                            am.execSQL("update subjects set ac='"+acs+"' where name=='"+subjectname+"'");
                            am.execSQL("update subjects set tc='"+tcs+"' where name=='"+subjectname+"'");
                            am.execSQL("update subjects set per='"+pers+"' where name=='"+subjectname+"'");
                        }
                    }
                }

                else
                {
                    am.execSQL("insert into '"+subjectname+"' values('"+date+"','"+subjecttime+"','1')");
                    String query1="select * from subjects where name=='"+subjectname+"'";
                    Cursor cursor1=am.rawQuery(query1,null);
                    if(cursor1.getCount()>0)
                    {
                        cursor1.moveToNext();
                        String ac = cursor1.getString(2);
                        String tc = cursor1.getString(1);
                        Integer aci=Integer.parseInt(ac);
                        Integer tci=Integer.parseInt(tc);
                        aci++;
                        tci++;
                        String acs=aci.toString();
                        String tcs=tci.toString();
                        Integer per=aci*100;
                        per=(Integer)per/tci;
                        String pers=per.toString();
                        am.execSQL("update subjects set ac='"+acs+"' where name=='"+subjectname+"'");
                        am.execSQL("update subjects set tc='"+tcs+"' where name=='"+subjectname+"'");
                        am.execSQL("update subjects set per='"+pers+"' where name=='"+subjectname+"'");
                    }
                }
                Toast.makeText(FridayUpdate.this, "Class Stat changed to PRESENT", Toast.LENGTH_SHORT).show();
                viewData();
                md.dismiss();
            }
        });


        absent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SQLiteDatabase am=openOrCreateDatabase("am",android.content.Context.MODE_PRIVATE,null);
                String query="select * from '"+subjectname+"' where date=='"+date+"' and time=='"+subjecttime+"'";
                Cursor cursor=am.rawQuery(query,null);
                if(cursor.getCount()>0)
                {
                    cursor.moveToNext();
                    String stat=cursor.getString(2);
                    am.execSQL("update '"+subjectname+"' set status='0' where date=='"+date+"' and time=='"+subjecttime+"'");
                    if(stat.equals("1"))
                    {
                        String query1="select * from subjects where name=='"+subjectname+"'";
                        Cursor cursor1=am.rawQuery(query1,null);
                        if(cursor1.getCount()>0)
                        {
                            cursor1.moveToNext();
                            String ac = cursor1.getString(2);
                            String tc = cursor1.getString(1);
                            Integer aci=Integer.parseInt(ac);
                            Integer tci=Integer.parseInt(tc);
                            aci--;
                            Integer per=aci*100;
                            per=(Integer)per/tci;
                            String pers=per.toString();
                            String acs=aci.toString();
                            am.execSQL("update subjects set ac='"+acs+"' where name=='"+subjectname+"'");
                            am.execSQL("update subjects set per='"+pers+"' where name=='"+subjectname+"'");
                        }
                    }
                    else if(stat.equals("2"))
                    {
                        String query1="select * from subjects where name=='"+subjectname+"'";
                        Cursor cursor1=am.rawQuery(query1,null);
                        if(cursor1.getCount()>0)
                        {
                            cursor1.moveToNext();
                            String ac = cursor1.getString(2);
                            String tc = cursor1.getString(1);
                            Integer aci=Integer.parseInt(ac);
                            Integer tci=Integer.parseInt(tc);
                            tci++;
                            String acs=aci.toString();
                            String tcs=tci.toString();
                            Integer per=aci*100;
                            per=(Integer)per/tci;
                            String pers=per.toString();
                            am.execSQL("update subjects set ac='"+acs+"' where name=='"+subjectname+"'");
                            am.execSQL("update subjects set tc='"+tcs+"' where name=='"+subjectname+"'");
                            am.execSQL("update subjects set per='"+pers+"' where name=='"+subjectname+"'");
                        }
                    }
                }

                else
                {
                    am.execSQL("insert into '"+subjectname+"' values('"+date+"','"+subjecttime+"','0')");
                    String query1="select * from subjects where name=='"+subjectname+"'";
                    Cursor cursor1=am.rawQuery(query1,null);
                    if(cursor1.getCount()>0)
                    {
                        cursor1.moveToNext();
                        String ac = cursor1.getString(2);
                        String tc = cursor1.getString(1);
                        Integer aci=Integer.parseInt(ac);
                        Integer tci=Integer.parseInt(tc);
                        tci++;
                        String acs=aci.toString();
                        String tcs=tci.toString();
                        Integer per=aci*100;
                        per=(Integer)per/tci;
                        String pers=per.toString();
                        am.execSQL("update subjects set ac='"+acs+"' where name=='"+subjectname+"'");
                        am.execSQL("update subjects set tc='"+tcs+"' where name=='"+subjectname+"'");
                        am.execSQL("update subjects set per='"+pers+"' where name=='"+subjectname+"'");
                    }
                }
                Toast.makeText(FridayUpdate.this, "Class Stat changed to ABSENT", Toast.LENGTH_SHORT).show();
                viewData();
                md.dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SQLiteDatabase am=openOrCreateDatabase("am",android.content.Context.MODE_PRIVATE,null);
                String query="select * from '"+subjectname+"' where date=='"+date+"' and time=='"+subjecttime+"'";
                Cursor cursor=am.rawQuery(query,null);
                if(cursor.getCount()>0)
                {
                    cursor.moveToNext();
                    String stat=cursor.getString(2);
                    am.execSQL("update '"+subjectname+"' set status='2' where date=='"+date+"' and time=='"+subjecttime+"'");
                    if(stat.equals("0"))
                    {
                        String query1="select * from subjects where name=='"+subjectname+"'";
                        Cursor cursor1=am.rawQuery(query1,null);
                        if(cursor1.getCount()>0)
                        {
                            cursor1.moveToNext();
                            String ac = cursor1.getString(2);
                            String tc = cursor1.getString(1);
                            Integer aci=Integer.parseInt(ac);
                            Integer tci=Integer.parseInt(tc);
                            tci--;
                            Integer per;
                            if(tci==0)
                                per=0;
                            else
                            {
                                per=aci*100;
                                per=(Integer)per/tci;
                            }
                            String pers=per.toString();
                            String acs=aci.toString();
                            String tcs=tci.toString();
                            am.execSQL("update subjects set ac='"+acs+"' where name=='"+subjectname+"'");
                            am.execSQL("update subjects set tc='"+tcs+"' where name=='"+subjectname+"'");
                            am.execSQL("update subjects set per='"+pers+"' where name=='"+subjectname+"'");
                        }
                    }
                    else if(stat.equals("1"))
                    {
                        String query1="select * from subjects where name=='"+subjectname+"'";
                        Cursor cursor1=am.rawQuery(query1,null);
                        if(cursor1.getCount()>0)
                        {
                            cursor1.moveToNext();
                            String ac = cursor1.getString(2);
                            String tc = cursor1.getString(1);
                            Integer aci=Integer.parseInt(ac);
                            Integer tci=Integer.parseInt(tc);
                            aci--;
                            tci--;
                            String acs=aci.toString();
                            String tcs=tci.toString();
                            Integer per;
                            if(tci==0)
                                per=0;
                            else
                            {
                                per=aci*100;
                                per=(Integer)per/tci;
                            }
                            String pers=per.toString();
                            am.execSQL("update subjects set ac='"+acs+"' where name=='"+subjectname+"'");
                            am.execSQL("update subjects set tc='"+tcs+"' where name=='"+subjectname+"'");
                            am.execSQL("update subjects set per='"+pers+"' where name=='"+subjectname+"'");
                        }
                    }
                }

                else
                {
                    am.execSQL("insert into '"+subjectname+"' values('"+date+"','"+subjecttime+"','2')");
                    String query1="select * from subjects where name=='"+subjectname+"'";
                    Cursor cursor1=am.rawQuery(query1,null);
                }
                Toast.makeText(FridayUpdate.this, "Class Stat changed to CANCELLED", Toast.LENGTH_SHORT).show();
                viewData();
                md.dismiss();
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                md.dismiss();
            }
        });

    }
}
