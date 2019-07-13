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
public class UpdateAdapter extends ArrayAdapter<UpdateListType> {
    private LayoutInflater mInflater;
    private ArrayList<UpdateListType> list;
    private Context mContext;
    private int mResource;

    public UpdateAdapter(Context context, int resource, ArrayList<UpdateListType> list) {
        super(context, resource, list);
        this.list=list;

        mInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mContext = context;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        view=mInflater.inflate(R.layout.update_list,null);
        UpdateListType listset= (UpdateListType) list.get(position);
        LinearLayout l=(LinearLayout)view.findViewById(R.id.linearlayout);
        TextView timev=(TextView)view.findViewById(R.id.list_content7);
        TextView namev=(TextView)view.findViewById(R.id.list_content8);
        TextView attev=(TextView)view.findViewById(R.id.list_content9);
        TextView mesv=(TextView)view.findViewById(R.id.list_content10);
        TextView perv=(TextView)view.findViewById(R.id.list_content11);
        TextView statusv=(TextView)view.findViewById(R.id.list_content12);
        timev.setText(listset.getTime());
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
        statusv.setText(listset.getStatus());
        String state=statusv.getText().toString();
        if(state.equals("PRESENT"))
        {
            l.setBackground(ContextCompat.getDrawable(getContext().getApplicationContext(),R.drawable.subejctlistgreen));
        }
        else if(state.equals("ABSENT"))
        {
            l.setBackground(ContextCompat.getDrawable(getContext().getApplicationContext(),R.drawable.subjectlistred));
        }
        else if(state.equals("CANCELLED"))
        {
            l.setBackground(ContextCompat.getDrawable(getContext().getApplicationContext(),R.drawable.subejctlistyellow));
        }
        return view;
    }
}
