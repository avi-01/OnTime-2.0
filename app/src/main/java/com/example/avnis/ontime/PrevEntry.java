package com.example.avnis.ontime;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class PrevEntry extends AppCompatActivity {
    String todayString;

    SimpleDateFormat formatter;
    TextView edittext;
    Button open;
    ImageView cal;
    Calendar myCalendar = Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prev_entry);

        open = (Button) findViewById(R.id.open);
        edittext = (TextView) findViewById(R.id.datepick);
        cal=(ImageView)findViewById(R.id.calendar);
        final java.util.Calendar calendar = java.util.Calendar.getInstance();
        Integer Today = calendar.get(java.util.Calendar.DAY_OF_WEEK);

        final Date todayDate = java.util.Calendar.getInstance().getTime();
        formatter = new SimpleDateFormat("dd/MM/yyyy");
        todayString = formatter.format(todayDate);
//                Today=Calendar.SUNDAY;

        edittext.setText(todayString);

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };
        cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dpd=new DatePickerDialog(PrevEntry.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                dpd.getDatePicker().setMaxDate(calendar.getTimeInMillis());
                dpd.show();
            }
        });

        open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String inputdate=edittext.getText().toString();
                SimpleDateFormat formattype = new SimpleDateFormat("dd/MM/yyyy");
                Date startDate = null;
                try {
                    startDate = formattype.parse(inputdate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                SimpleDateFormat outFormat = new SimpleDateFormat("EEEE");
                calendar.setTime(startDate);
                int Today=calendar.get(java.util.Calendar.DAY_OF_WEEK);
                todayString=inputdate;
                switch (Today) {
                    case Calendar.SUNDAY:
                        Intent i1 = new Intent(PrevEntry.this, SundayUpdate.class);
                        Bundle b1 = new Bundle();
                        b1.putString("date", todayString);
                        b1.putString("day", "sunday");
                        i1.putExtras(b1);
                        startActivity(i1);
                        break;
                    case java.util.Calendar.MONDAY:
                        Intent i2 = new Intent(PrevEntry.this, SundayUpdate.class);
                        Bundle b2 = new Bundle();
                        b2.putString("date", todayString);
                        b2.putString("day", "monday");
                        i2.putExtras(b2);
                        startActivity(i2);
                        break;
                    case java.util.Calendar.TUESDAY:
                        Intent i3 = new Intent(PrevEntry.this, SundayUpdate.class);
                        Bundle b3 = new Bundle();
                        b3.putString("date", todayString);
                        b3.putString("day", "tuesday");
                        i3.putExtras(b3);
                        startActivity(i3);
                        break;
                    case java.util.Calendar.WEDNESDAY:
                        Intent i4 = new Intent(PrevEntry.this, SundayUpdate.class);
                        Bundle b4 = new Bundle();
                        b4.putString("date", todayString);
                        b4.putString("day", "wednesday");
                        i4.putExtras(b4);
                        startActivity(i4);
                        break;
                    case java.util.Calendar.THURSDAY:
                        Intent i5 = new Intent(PrevEntry.this, SundayUpdate.class);
                        Bundle b5 = new Bundle();
                        b5.putString("date", todayString);
                        b5.putString("day", "thursday");
                        i5.putExtras(b5);
                        startActivity(i5);
                        break;
                    case java.util.Calendar.FRIDAY:
                        Intent i6 = new Intent(PrevEntry.this, SundayUpdate.class);
                        Bundle b6 = new Bundle();
                        b6.putString("date", todayString);
                        b6.putString("day", "friday");
                        i6.putExtras(b6);
                        startActivity(i6);
                        break;
                    case java.util.Calendar.SATURDAY:
                        Intent i7 = new Intent(PrevEntry.this, SundayUpdate.class);
                        Bundle b7 = new Bundle();
                        b7.putString("date", todayString);
                        b7.putString("day", "saturday");
                        i7.putExtras(b7);
                        startActivity(i7);
                        break;
                }
            }
        });
    }

    private void updateLabel() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        edittext.setText(sdf.format(myCalendar.getTime()));
    }
}
