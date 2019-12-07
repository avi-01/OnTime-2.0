package com.example.avnis.ontime;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class InfoDetailAdapter extends ArrayAdapter<InfoDetailType> {
    private LayoutInflater mInflater;
    private ArrayList<InfoDetailType> list;
    private Context mContext;
    private int mResource;

    public InfoDetailAdapter(Context context, int resource, ArrayList<InfoDetailType> list) {
        super(context, resource, list);
        this.list=list;

        mInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mContext = context;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        view=mInflater.inflate(R.layout.detail_layout,null);
        InfoDetailType listset=list.get(position);
        TextView datev=(TextView)view.findViewById(R.id.list_content8);
        TextView timev=(TextView)view.findViewById(R.id.list_content7);
        TextView statusv=(TextView)view.findViewById(R.id.list_content12);
        datev.setText(listset.getDate());
        timev.setText(listset.getTime());
        statusv.setText(listset.getStatus());
        String stat= listset.getStatus();
        if(stat.equals("Present"))
        {
                statusv.setTextColor(Color.GREEN);
        }
        else if(stat.equals("Absent"))
        {
            statusv.setTextColor(Color.RED);
        }
        else
        {
            statusv.setTextColor(-7000);
        }


        return view;
    }

}
