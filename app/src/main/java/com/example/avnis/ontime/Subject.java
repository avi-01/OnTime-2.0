package com.example.avnis.ontime;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class Subject extends AppCompatActivity {
    Button add;
    Dialog md;
    Dialog md1;
    ListView list;
    ArrayList<String> listItem;
    ArrayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);
        SQLiteDatabase am=openOrCreateDatabase("am",MODE_PRIVATE,null);
        am.execSQL("create table if not exists subjects(name varchar,tc int,ac int,per int)");
        add=(Button)findViewById(R.id.button);
        md=new Dialog(this);
        md1=new Dialog(this);
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

    private void editData(AdapterView<?> adapterView, View view, final int i, long l) {
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
                SQLiteDatabase am=openOrCreateDatabase("am",MODE_PRIVATE,null);
                String name=list.getItemAtPosition(i).toString();
                am.execSQL("delete from subjects where name=='"+name+"'");
                listItem.clear();
                viewData();
                md1.dismiss();
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cname = change.getText().toString();
                if(cname.equals(""))
                {
                    Toast.makeText(Subject.this, "Enter the name", Toast.LENGTH_SHORT).show();
                }
                SQLiteDatabase am=openOrCreateDatabase("am",MODE_PRIVATE,null);
                String name=list.getItemAtPosition(i).toString();
                am.execSQL("update subjects  set name='"+cname+"' where name=='"+name+"'");
                listItem.clear();
                viewData();
                md1.dismiss();
            }
        });
        close1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                md1.dismiss();
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
                    am.execSQL("create table if not exists subjects(name varchar,tc int,ac int,per int)");
                    String query="select * from subjects where name=='"+s+"'";
                    Cursor cursor=am.rawQuery(query,null);
                    if(cursor.getCount()>0)
                    {
                        Toast.makeText(Subject.this, "Subject Already Exits", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        am.execSQL("insert into subjects values('"+s+"','0','0','0')");
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
        adapter= new ArrayAdapter<>(this,R.layout.listlay,R.id.list_content,listItem);
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
}
