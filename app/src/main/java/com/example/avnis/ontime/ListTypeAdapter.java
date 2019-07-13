package com.example.avnis.ontime;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by avnis on 7/6/2019.
 */
public class ListTypeAdapter extends ArrayAdapter<listtype> {
    private LayoutInflater mInflater;
    private ArrayList<listtype> list;
    private Context mContext;
    private int mResource;

    public ListTypeAdapter(Context context, int resource, ArrayList<listtype> list) {
        super(context, resource, list);
        this.list=list;
        mInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mResource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView= mInflater.inflate(mResource,parent, false);
        listtype lists=list.get(position);
        if(lists !=null)
        {

            TextView tvtime=(TextView)convertView.findViewById(R.id.list_content);
            TextView tvname=(TextView)convertView.findViewById(R.id.list_content2);
            TextView tvdur=(TextView)convertView.findViewById(R.id.list_content3);

            if(tvtime!=null)
            {
                tvtime.setText((lists.getTime()));
            }

            if(tvname!=null)
            {
                tvname.setText((lists.getName()));
            }

            if(tvdur!=null)
            {
                tvdur.setText((lists.getDur()));
            }

        }
        return convertView;
    }
}
