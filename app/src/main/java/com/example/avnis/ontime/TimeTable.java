package com.example.avnis.ontime;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class TimeTable extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table);
        tabLayout=(TabLayout)findViewById(R.id.tablayout);
        viewPager=(ViewPager)findViewById(R.id.viewpager);
        ViewPageAdapter adapter= new ViewPageAdapter(getSupportFragmentManager());
        adapter.AddFragment(new Monday(),"Monday");
        adapter.AddFragment(new Tuesday(),"Tuesday");
        adapter.AddFragment(new Wednesday(),"Wednesday");
        adapter.AddFragment(new Thursday(),"Thursday");
        adapter.AddFragment(new Friday(),"Friday");
        adapter.AddFragment(new Saturday(),"Saturday");
        adapter.AddFragment(new Sunday(),"Sunday");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
