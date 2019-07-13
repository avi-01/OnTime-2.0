package com.example.avnis.ontime;

import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Thursday extends Fragment{

    View view;
    FloatingActionButton add;
    ListView list;
    ArrayList<listtype> listItem;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.activity_thursday,container,false);
        final AddSubjectTime dialog= new AddSubjectTime();
        list=(ListView)view.findViewById(R.id.listView2);
        listItem=new ArrayList<>();
        viewData();
        add=(FloatingActionButton) view.findViewById((R.id.fab));
        final RemoveSubject dialog2= new RemoveSubject();
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                listtype listname= (listtype) list.getItemAtPosition(i);
                String subjectname=listname.getName();
                String timename=listname.getTime();
                Bundle args = new Bundle();
                args.putString("day", "thursday");
                args.putString("subject", subjectname);
                args.putString("time", timename);
                dialog2.setArguments(args);
                dialog2.show(getFragmentManager(), "TAG");
            }
        });
        dialog2.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                viewData();
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view1) {
                Bundle args = new Bundle();
                args.putString("day", "thursday");
                dialog.setArguments(args);
                dialog.show(getFragmentManager(), "TAG");

            }
        });
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                viewData();
            }
        });
        return view;
    }


    public void viewData(){
        listItem.clear();
        SQLiteDatabase am=getActivity().openOrCreateDatabase("am",android.content.Context.MODE_PRIVATE,null);
        String query="select * from thursday";
        Cursor cursor=am.rawQuery(query,null);
        while(cursor.moveToNext())
        {
            listtype a=new listtype(cursor.getString(0),cursor.getString(1),cursor.getString(2));
            listItem.add(a);
        }
        Collections.sort(listItem,new Comparator<listtype>() {
            @Override
            public int compare(listtype a, listtype b) {
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
        ListTypeAdapter adapter= new  ListTypeAdapter(getContext().getApplicationContext(),R.layout.time_layout_list,listItem);
        list.setAdapter(adapter);
    }

}
