package com.example.avnis.ontime;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class Monday extends Fragment{
    View view;

    ListView list;
    ArrayAdapter adapter;
    ArrayList<String> listItem;

    public Monday() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.activity_monday,container,false);

        list=(ListView)view.findViewById(R.id.listView);
        listItem=new ArrayList<>();
        return view;
    }

    public void viewData(){
        SQLiteDatabase am=getActivity().openOrCreateDatabase("am",android.content.Context.MODE_PRIVATE,null);
        String query="select * from subjects";
        Cursor cursor=am.rawQuery(query,null);
        while(cursor.moveToNext())
        {
            listItem.add(cursor.getString(0));
        }
//        adapter= new  ArrayAdapter<>(this,R.layout.listlay,R.id.list_content,listItem);
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
