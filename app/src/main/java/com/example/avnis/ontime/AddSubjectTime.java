package com.example.avnis.ontime;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Calendar;

import static android.content.Context.ALARM_SERVICE;
import static java.util.Calendar.*;

/**
 * Created by avnis on 7/6/2019.
 */
public class AddSubjectTime extends DialogFragment {
    private static final String TAG= "AddSubjectTime";
    private DialogInterface.OnDismissListener onDismissListener;
    Spinner sp;
    EditText time,dur;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.addsubjecttime, container, false);
        final String myStr = getArguments().getString("day");
        sp = (Spinner) view.findViewById(R.id.spinner);
        time = (EditText) view.findViewById(R.id.editText);
        dur = (EditText) view.findViewById(R.id.editText2);
        Button add=(Button)view.findViewById(R.id.addtable);
        ImageButton close=(ImageButton)view.findViewById(R.id.imageButton3);
        ArrayList<String> list = new ArrayList<String>();
        SQLiteDatabase am = getActivity().openOrCreateDatabase("am", android.content.Context.MODE_PRIVATE, null);
        String query = "select * from subjects";
        Cursor cursor = am.rawQuery(query, null);
        while (cursor.moveToNext()) {
            list.add(cursor.getString(0));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext().getApplicationContext(), R.layout.spinner_layout, R.id.txt, list);
        sp.setAdapter(adapter);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String subject = sp.getSelectedItem().toString();
                String times= time.getText().toString();
                String durs= dur.getText().toString();
                if(subject.equals("") || times.equals("") || durs.equals(""))
                {
                    Toast.makeText(getContext().getApplicationContext(), "Fill Required", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(getContext().getApplicationContext(), "Write Duration in Correct Format(Hr)", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(getContext().getApplicationContext(), "Write Time in Given Format", Toast.LENGTH_SHORT).show();}

                        try {
                            Integer x= Integer.parseInt(arrOfStr[1]);
                            if (x >= 60) {
                                b = 100;
                            }
                        }catch(NumberFormatException e){
                            b=100;
                            Toast.makeText(getContext().getApplicationContext(), "Write Time in Given Format", Toast.LENGTH_SHORT).show();}
                    }
                    if(b!=2)
                    {
                        Toast.makeText(getContext().getApplicationContext(), "Write Time in Given Format", Toast.LENGTH_SHORT).show();
                    }
                    else if(d!=1)
                    {
                        Toast.makeText(getContext().getApplicationContext(), "Write Duration in Correct Format", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        SQLiteDatabase am = getActivity().openOrCreateDatabase("am", android.content.Context.MODE_PRIVATE, null);
                        String query = "select * from '"+myStr+"' where time='" + times + "' and name='" + subject + "'";
                        Cursor cursor = am.rawQuery(query, null);
                        if (cursor.getCount() > 0) {
                            Toast.makeText(getContext().getApplicationContext(), "Subject Already In the Timetable", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            am.execSQL("insert into '"+myStr+"' values('"+times+"','"+subject+"','"+durs+"')");
                            Toast.makeText(getContext().getApplicationContext(),"Subject Added", Toast.LENGTH_SHORT).show();
//                            ((Monday) getParentFragment()).viewData();

                            getDialog().dismiss();

                            
                            int day = SATURDAY;

                            if(myStr=="sunday") {
                                day = SUNDAY;
                            }
                            else if(myStr=="monday") {
                                day = MONDAY;
                            }
                            else if(myStr=="tuesday") {
                                day = TUESDAY;
                            }
                            else if(myStr=="wednesday") {
                                day = WEDNESDAY;
                            }
                            else if(myStr=="thursday") {
                                day = THURSDAY;
                            }
                            else if(myStr=="friday") {
                                day = FRIDAY;
                            }
                            else if(myStr=="saturday") {
                                day = SATURDAY;
                            }
                            System.out.println(day);

                            Calendar date1 = Calendar.getInstance();
                            while (date1.get(Calendar.DAY_OF_WEEK) != day) {
                                date1.add(DATE, 1);
                            }

                            System.out.println(Calendar.getInstance()+" "+ date1);

                            String[] time_A = times.split(":");
                            long time = date1.getTimeInMillis() + ((Long.parseLong(time_A[0])*60 + Long.parseLong(time_A[1]))*60)*1000 - ((date1.get(HOUR_OF_DAY)*60 + date1.get(Calendar.MINUTE))*60)*1000;
                            System.out.println(System.currentTimeMillis() +" "+time+"   "+Long.parseLong(time_A[0])+" "+Long.parseLong(time_A[1])+"   "+date1.get(HOUR_OF_DAY)+" "+date1.get(Calendar.MINUTE));
                            System.out.println(((Long.parseLong(time_A[0])*60 + Long.parseLong(time_A[1]))*60)*1000 +"  "+((date1.get(HOUR_OF_DAY)*60 + date1.get(MINUTE))*60)*1000);

                            if(time - 30*60*1000 < System.currentTimeMillis())
                            {
                                time = time + 7*24*60*60*1000 ;
                            }

                            System.out.println(System.currentTimeMillis() +" "+time);


                            Intent intent = new Intent(getContext(), MyBroadcastReceiver.class);

                            intent.putExtra("type","remind");
                            intent.putExtra("name",subject);
                            intent.putExtra("time",times);
                            intent.putExtra("day",myStr);

                            int id = MainActivity.NotificationID.getID();
                            PendingIntent pendingIntent = PendingIntent.getBroadcast(
                                    getContext(),id ,intent, PendingIntent.FLAG_CANCEL_CURRENT|PendingIntent.FLAG_UPDATE_CURRENT);

                            AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(ALARM_SERVICE);
                            alarmManager.setExact(AlarmManager.RTC_WAKEUP, time - 30*60*1000,pendingIntent);


                            Intent intent1 = new Intent(getContext(), MyBroadcastReceiver.class);
                            int id1= MainActivity.NotificationID.getID();

                            intent1.putExtra("type","update");
                            intent1.putExtra("name",subject);
                            intent1.putExtra("time",times);
                            intent1.putExtra("day",myStr);

                            PendingIntent pendingIntent1 = PendingIntent.getBroadcast(
                                    getContext(), id1,intent1, PendingIntent.FLAG_CANCEL_CURRENT|PendingIntent.FLAG_UPDATE_CURRENT);


                            alarmManager.setExact(AlarmManager.RTC_WAKEUP, time + 30*60*1000,pendingIntent1);

                        }

                    }
                }
            }
        });
        return view;
    }
    public void setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
        this.onDismissListener = onDismissListener;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (onDismissListener != null) {
            onDismissListener.onDismiss(dialog);
        }
    }
}
