package com.example.avnis.ontime;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by avnis on 7/6/2019.
 */
public class InfoAdapter extends ArrayAdapter<InfoType> {
    private LayoutInflater mInflater;
    private ArrayList<InfoType> list;
    private Context mContext;
    private int mResource;

    public InfoAdapter(Context context, int resource, ArrayList<InfoType> list) {
        super(context, resource, list);
        this.list=list;

        mInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mContext = context;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        view=mInflater.inflate(R.layout.info,null);
        InfoType listset= (InfoType) list.get(position);
        LinearLayout l=(LinearLayout)view.findViewById(R.id.linearlayout);
        TextView namev=(TextView)view.findViewById(R.id.list_content8);
        TextView attev=(TextView)view.findViewById(R.id.list_content9);
        TextView mesv=(TextView)view.findViewById(R.id.list_content10);
        TextView perv=(TextView)view.findViewById(R.id.list_content11);
        namev.setText(listset.getName());
        attev.setText(listset.getAtt());
        mesv.setText(listset.getMes());
        perv.setText(listset.getPer());
        String s=perv.getText().toString();
        s=s.replace("%","");
        Integer x=Integer.parseInt(s);
        if(x>=75)
        {
            perv.setBackground(ContextCompat.getDrawable(getContext().getApplicationContext(),R.drawable.per));
        }
        else
        {
            perv.setBackground(ContextCompat.getDrawable(getContext().getApplicationContext(),R.drawable.per_red));
        }

        return view;
    }
}
