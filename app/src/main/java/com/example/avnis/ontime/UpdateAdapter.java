package com.example.avnis.ontime;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;
import static android.support.v7.widget.RecyclerView.*;
import static java.lang.Integer.parseInt;

/**
 * Created by avnis on 7/6/2019.
 */
public class UpdateAdapter extends ArrayAdapter<UpdateListType> {
    private LayoutInflater mInflater;
    private ArrayList<UpdateListType> list;
    private String Indate="";
    private Context mContext;
    private int mResource;

    public UpdateAdapter(Context context, int resource, ArrayList<UpdateListType> list,String Indate) {
        super(context, resource, list);
        this.list=list;
        this.Indate = Indate;
        mInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mContext = context;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {

        final ViewHolder mHolder;

//        if (view==null){
            view = mInflater.inflate(R.layout.update_list,null);
            mHolder = new ViewHolder();
//
            mHolder.timev=(TextView)view.findViewById(R.id.list_content7);
            mHolder.namev=(TextView)view.findViewById(R.id.list_content8);
            mHolder.attev=(TextView)view.findViewById(R.id.list_content9);
            mHolder.mesv=(TextView)view.findViewById(R.id.list_content10);
            mHolder.perv=(TextView)view.findViewById(R.id.list_content11);
            mHolder.satusv=(TextView)view.findViewById(R.id.list_content12);
//
            view.setTag(mHolder);

            Log.e("Error:","1");

            final UpdateListType listset = (UpdateListType) list.get(position);
            final LinearLayout l = (LinearLayout) view.findViewById(R.id.linearlayout);
            mHolder.timev.setText(listset.getTime());
            mHolder.namev.setText(listset.getName());
            mHolder.attev.setText(listset.getAtt());
            mHolder.mesv.setText(listset.getMes());
            mHolder.perv.setText(listset.getPer());
            Log.e("Sub", listset.getName() + " " + listset.getTime());
            final String[] s = {mHolder.perv.getText().toString()};
            s[0] = s[0].replace("%", "");
            final Integer[] x = {parseInt(s[0])};
            if (x[0] >= 75) {
                mHolder.perv.setBackground(ContextCompat.getDrawable(getContext().getApplicationContext(), R.drawable.per));
            } else {
                mHolder.perv.setBackground(ContextCompat.getDrawable(getContext().getApplicationContext(), R.drawable.per_red));
            }
            mHolder.satusv.setText(listset.getStatus());
            String state = mHolder.satusv.getText().toString();
            if (state.equals("PRESENT")) {
                l.setBackground(ContextCompat.getDrawable(getContext().getApplicationContext(), R.drawable.subejctlistgreen));
            } else if (state.equals("ABSENT")) {
                l.setBackground(ContextCompat.getDrawable(getContext().getApplicationContext(), R.drawable.subjectlistred));
            } else if (state.equals("CANCELLED")) {
                l.setBackground(ContextCompat.getDrawable(getContext().getApplicationContext(), R.drawable.subejctlistyellow));
            }


            view.setOnTouchListener(new OnSwipeTouchListener(getContext()) {

                public void onSwipeRight() {
                    Log.e("Right", listset.getName());
                    Toast.makeText(getContext(), "right", Toast.LENGTH_SHORT).show();
                    String subjectname = listset.getName();
                    String subjecttime = listset.getTime();
                    String date = Indate;
                    String acs = "";
                    String tcs = "";
                    String pers = "";
                    SQLiteDatabase am = getContext().openOrCreateDatabase("am", android.content.Context.MODE_PRIVATE, null);
                    String query = "select * from '" + subjectname + "' where date=='" + date + "' and time=='" + subjecttime + "'";
                    Cursor cursor = am.rawQuery(query, null);
                    if (cursor.getCount() > 0) {
                        cursor.moveToNext();
                        String stat = cursor.getString(2);
                        am.execSQL("update '" + subjectname + "' set status='1' where date=='" + date + "' and time=='" + subjecttime + "'");
                        if (stat.equals("0")) {
                            String query1 = "select * from subjects where name=='" + subjectname + "'";
                            Cursor cursor1 = am.rawQuery(query1, null);
                            if (cursor1.getCount() > 0) {
                                cursor1.moveToNext();
                                String ac = cursor1.getString(2);
                                String tc = cursor1.getString(1);
                                Integer aci = parseInt(ac);
                                Integer tci = parseInt(tc);
                                aci++;
                                Integer per = aci * 100;
                                per = (Integer) per / tci;
                                pers = per.toString();
                                acs = aci.toString();
                                tcs = tci.toString();
                                am.execSQL("update subjects set ac='" + acs + "' where name=='" + subjectname + "'");
                                am.execSQL("update subjects set per='" + pers + "' where name=='" + subjectname + "'");
                            }
                        } else if (stat.equals("2")) {
                            String query1 = "select * from subjects where name=='" + subjectname + "'";
                            Cursor cursor1 = am.rawQuery(query1, null);
                            if (cursor1.getCount() > 0) {
                                cursor1.moveToNext();
                                String ac = cursor1.getString(2);
                                String tc = cursor1.getString(1);
                                Integer aci = parseInt(ac);
                                Integer tci = parseInt(tc);
                                aci++;
                                tci++;
                                acs = aci.toString();
                                tcs = tci.toString();
                                Integer per = aci * 100;
                                per = (Integer) per / tci;
                                pers = per.toString();
                                am.execSQL("update subjects set ac='" + acs + "' where name=='" + subjectname + "'");
                                am.execSQL("update subjects set tc='" + tcs + "' where name=='" + subjectname + "'");
                                am.execSQL("update subjects set per='" + pers + "' where name=='" + subjectname + "'");
                            }
                        }

                    } else {
                        am.execSQL("insert into '" + subjectname + "' values('" + date + "','" + subjecttime + "','1')");
                        String query1 = "select * from subjects where name=='" + subjectname + "'";
                        Cursor cursor1 = am.rawQuery(query1, null);
                        if (cursor1.getCount() > 0) {
                            cursor1.moveToNext();
                            String ac = cursor1.getString(2);
                            String tc = cursor1.getString(1);
                            Integer aci = parseInt(ac);
                            Integer tci = parseInt(tc);
                            aci++;
                            tci++;
                            acs = aci.toString();
                            tcs = tci.toString();
                            Integer per = aci * 100;
                            per = (Integer) per / tci;
                            pers = per.toString();
                            am.execSQL("update subjects set ac='" + acs + "' where name=='" + subjectname + "'");
                            am.execSQL("update subjects set tc='" + tcs + "' where name=='" + subjectname + "'");
                            am.execSQL("update subjects set per='" + pers + "' where name=='" + subjectname + "'");
                        }
                    }

                    //                if(Integer.getInteger(pers) >=75)
                    //                {
                    //                    mHolder.perv.setBackground(ContextCompat.getDrawable(getContext().getApplicationContext(),R.drawable.per));
                    //                }
                    //                else
                    //                {
                    //                    mHolder.perv.setBackground(ContextCompat.getDrawable(getContext().getApplicationContext(),R.drawable.per_red));
                    //                }
                    String query1 = "select * from subjects where name=='" + subjectname + "'";
                    Cursor cursor1 = am.rawQuery(query1, null);
                    cursor1.moveToNext();
                    acs = cursor1.getString(2);
                    tcs = cursor1.getString(1);
                    pers = cursor1.getString(3);

                    mHolder.attev.setText("Attendance " + acs + "/" + tcs);
                    mHolder.perv.setText(pers + "%");

                    if (parseInt(pers) >= 75) {
                        mHolder.perv.setBackground(ContextCompat.getDrawable(getContext().getApplicationContext(), R.drawable.per));
                    } else {
                        mHolder.perv.setBackground(ContextCompat.getDrawable(getContext().getApplicationContext(), R.drawable.per_red));
                    }

                    Log.e("TCS", "Yes " + tcs);
                    Log.e("ACS", "Yes " + acs);
                    Log.e("PERS", "Yes " + pers);


                    mHolder.mesv.setText("You set the entry for the class");
                    mHolder.satusv.setText("PRESENT");

                    listset.setMes("You set the entry for the class");
                    listset.setAtt("Attendance " + acs + "/" + tcs);
                    listset.setPer(pers + "%");
                    listset.setStatus("PRESENT");
                    list.set(position,listset);
                    l.setBackground(ContextCompat.getDrawable(getContext().getApplicationContext(), R.drawable.subejctlistgreen));
                    Toast.makeText(getContext(), "Class Stat changed to PRESENT", Toast.LENGTH_SHORT).show();
                }

                public void onSwipeLeft() {

                    Log.e("Left", listset.getName());
                    String subjectname = listset.getName();
                    String subjecttime = listset.getTime();
                    String date = Indate;
                    String acs = "";
                    String tcs = "";
                    String pers = "";
                    SQLiteDatabase am = getContext().openOrCreateDatabase("am", android.content.Context.MODE_PRIVATE, null);
                    String query = "select * from '" + subjectname + "' where date=='" + date + "' and time=='" + subjecttime + "'";
                    Cursor cursor = am.rawQuery(query, null);
                    if (cursor.getCount() > 0) {
                        cursor.moveToNext();
                        String stat = cursor.getString(2);
                        am.execSQL("update '" + subjectname + "' set status='0' where date=='" + date + "' and time=='" + subjecttime + "'");
                        if (stat.equals("1")) {
                            String query1 = "select * from subjects where name=='" + subjectname + "'";
                            Cursor cursor1 = am.rawQuery(query1, null);
                            if (cursor1.getCount() > 0) {
                                cursor1.moveToNext();
                                String ac = cursor1.getString(2);
                                String tc = cursor1.getString(1);
                                Integer aci = parseInt(ac);
                                Integer tci = parseInt(tc);
                                aci--;
                                Integer per = aci * 100;
                                per = (Integer) per / tci;
                                tcs = tci.toString();
                                pers = per.toString();
                                acs = aci.toString();
                                am.execSQL("update subjects set ac='" + acs + "' where name=='" + subjectname + "'");
                                am.execSQL("update subjects set per='" + pers + "' where name=='" + subjectname + "'");
                            }
                        } else if (stat.equals("2")) {
                            String query1 = "select * from subjects where name=='" + subjectname + "'";
                            Cursor cursor1 = am.rawQuery(query1, null);
                            if (cursor1.getCount() > 0) {
                                cursor1.moveToNext();
                                String ac = cursor1.getString(2);
                                String tc = cursor1.getString(1);
                                Integer aci = parseInt(ac);
                                Integer tci = parseInt(tc);
                                tci++;
                                acs = aci.toString();
                                tcs = tci.toString();
                                Integer per = aci * 100;
                                per = (Integer) per / tci;
                                pers = per.toString();
                                am.execSQL("update subjects set ac='" + acs + "' where name=='" + subjectname + "'");
                                am.execSQL("update subjects set tc='" + tcs + "' where name=='" + subjectname + "'");
                                am.execSQL("update subjects set per='" + pers + "' where name=='" + subjectname + "'");
                            }
                        }
                    } else {
                        am.execSQL("insert into '" + subjectname + "' values('" + date + "','" + subjecttime + "','0')");
                        String query1 = "select * from subjects where name=='" + subjectname + "'";
                        Cursor cursor1 = am.rawQuery(query1, null);
                        if (cursor1.getCount() > 0) {
                            cursor1.moveToNext();
                            String ac = cursor1.getString(2);
                            String tc = cursor1.getString(1);
                            Integer aci = parseInt(ac);
                            Integer tci = parseInt(tc);
                            tci++;
                            acs = aci.toString();
                            tcs = tci.toString();
                            Integer per = aci * 100;
                            per = (Integer) per / tci;
                            pers = per.toString();
                            am.execSQL("update subjects set ac='" + acs + "' where name=='" + subjectname + "'");
                            am.execSQL("update subjects set tc='" + tcs + "' where name=='" + subjectname + "'");
                            am.execSQL("update subjects set per='" + pers + "' where name=='" + subjectname + "'");
                        }
                    }


                    //                if(Integer.getInteger(pers) >=75)
                    //                {
                    //                    mHolder.perv.setBackground(ContextCompat.getDrawable(getContext().getApplicationContext(),R.drawable.per));
                    //                }
                    //                else
                    //                {
                    //                    mHolder.perv.setBackground(ContextCompat.getDrawable(getContext().getApplicationContext(),R.drawable.per_red));
                    //                }

                    String query1 = "select * from subjects where name=='" + subjectname + "'";
                    Cursor cursor1 = am.rawQuery(query1, null);
                    cursor1.moveToNext();
                    acs = cursor1.getString(2);
                    tcs = cursor1.getString(1);
                    pers = cursor1.getString(3);

                    mHolder.attev.setText("Attendance " + acs + "/" + tcs);
                    mHolder.perv.setText(pers + "%");
                    if (parseInt(pers) >= 75) {
                        mHolder.perv.setBackground(ContextCompat.getDrawable(getContext().getApplicationContext(), R.drawable.per));
                    } else {
                        mHolder.perv.setBackground(ContextCompat.getDrawable(getContext().getApplicationContext(), R.drawable.per_red));
                    }

                    Log.e("TCS", "Yes " + tcs);
                    Log.e("ACS", "Yes " + acs);
                    Log.e("PERS", "Yes " + pers);
                    mHolder.satusv.setText("ABSENT");
                    l.setBackground(ContextCompat.getDrawable(getContext().getApplicationContext(), R.drawable.subjectlistred));
                    mHolder.mesv.setText("You set the entry for the class");


                    listset.setMes("You set the entry for the class");
                    listset.setAtt("Attendance " + acs + "/" + tcs);
                    listset.setPer(pers + "%");
                    listset.setStatus("ABSENT");
                    list.set(position,listset);
                    Toast.makeText(getContext(), "Class Stat changed to ABSENT", Toast.LENGTH_SHORT).show();
                }

                public void onDoubleTapped() {
                    Log.e("Double", listset.getName());

                    String subjectname = listset.getName();
                    String subjecttime = listset.getTime();
                    String date = Indate;

                    SQLiteDatabase am = getContext().openOrCreateDatabase("am", android.content.Context.MODE_PRIVATE, null);
                    String query = "select * from '" + subjectname + "' where date=='" + date + "' and time=='" + subjecttime + "'";
                    Cursor cursor = am.rawQuery(query, null);
                    if (cursor.getCount() > 0) {
                        cursor.moveToNext();
                        String stat = cursor.getString(2);
                        am.execSQL("update '" + subjectname + "' set status='2' where date=='" + date + "' and time=='" + subjecttime + "'");
                        if (stat.equals("0")) {
                            String query1 = "select * from subjects where name=='" + subjectname + "'";
                            Cursor cursor1 = am.rawQuery(query1, null);
                            if (cursor1.getCount() > 0) {
                                cursor1.moveToNext();
                                String ac = cursor1.getString(2);
                                String tc = cursor1.getString(1);
                                Integer aci = parseInt(ac);
                                Integer tci = parseInt(tc);
                                tci--;
                                Integer per;
                                if (tci == 0)
                                    per = 0;
                                else {
                                    per = aci * 100;
                                    per = (Integer) per / tci;
                                }
                                String pers = per.toString();
                                String acs = aci.toString();
                                String tcs = tci.toString();
                                am.execSQL("update subjects set ac='" + acs + "' where name=='" + subjectname + "'");
                                am.execSQL("update subjects set tc='" + tcs + "' where name=='" + subjectname + "'");
                                am.execSQL("update subjects set per='" + pers + "' where name=='" + subjectname + "'");
                            }
                        } else if (stat.equals("1")) {
                            String query1 = "select * from subjects where name=='" + subjectname + "'";
                            Cursor cursor1 = am.rawQuery(query1, null);
                            if (cursor1.getCount() > 0) {
                                cursor1.moveToNext();
                                String ac = cursor1.getString(2);
                                String tc = cursor1.getString(1);
                                Integer aci = parseInt(ac);
                                Integer tci = parseInt(tc);
                                aci--;
                                tci--;
                                String acs = aci.toString();
                                String tcs = tci.toString();
                                Integer per;
                                if (tci == 0)
                                    per = 0;
                                else {
                                    per = aci * 100;
                                    per = (Integer) per / tci;
                                }
                                String pers = per.toString();
                                am.execSQL("update subjects set ac='" + acs + "' where name=='" + subjectname + "'");
                                am.execSQL("update subjects set tc='" + tcs + "' where name=='" + subjectname + "'");
                                am.execSQL("update subjects set per='" + pers + "' where name=='" + subjectname + "'");
                            }
                        }
                    } else {
                        am.execSQL("insert into '" + subjectname + "' values('" + date + "','" + subjecttime + "','2')");
                        String query1 = "select * from subjects where name=='" + subjectname + "'";
                        Cursor cursor1 = am.rawQuery(query1, null);
                    }


                    String query1 = "select * from subjects where name=='" + subjectname + "'";
                    Cursor cursor1 = am.rawQuery(query1, null);
                    cursor1.moveToNext();
                    String acs = cursor1.getString(2);
                    String tcs = cursor1.getString(1);
                    String pers = cursor1.getString(3);

                    mHolder.attev.setText("Attendance " + acs + "/" + tcs);
                    mHolder.perv.setText(pers + "%");

                    if (parseInt(pers) >= 75) {
                        mHolder.perv.setBackground(ContextCompat.getDrawable(getContext().getApplicationContext(), R.drawable.per));
                    } else {
                        mHolder.perv.setBackground(ContextCompat.getDrawable(getContext().getApplicationContext(), R.drawable.per_red));
                    }

                    Log.e("TCS", "Yes " + tcs);
                    Log.e("ACS", "Yes " + acs);
                    Log.e("PERS", "Yes " + pers);
                    mHolder.mesv.setText("You set the entry for the class");
                    mHolder.satusv.setText("CANCELLED");


                    listset.setMes("You set the entry for the class");
                    listset.setAtt("Attendance " + acs + "/" + tcs);
                    listset.setPer(pers + "%");
                    listset.setStatus("CANCELLED");
                    list.set(position,listset);
                    l.setBackground(ContextCompat.getDrawable(getContext().getApplicationContext(), R.drawable.subejctlistyellow));
                    Toast.makeText(getContext(), "Class Stat changed to CANCELLED", Toast.LENGTH_SHORT).show();
                }


            });


            view.setTag(mHolder);

//        }
//        else {
//            mHolder = (ViewHolder) view.getTag();
//
//            Log.e("Error:", "2");
//
//        }

        return view;
    }

    private class ViewHolder {
        private TextView timev;
        private TextView namev;
        private TextView satusv;
        private TextView attev;
        private TextView perv;
        private TextView mesv;
    }


}


