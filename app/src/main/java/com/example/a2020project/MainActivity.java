package com.example.a2020project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Tablayout
        TabLayout tabs = (TabLayout) findViewById(R.id.tab_layout);
        tabs.addTab(tabs.newTab().setText("Monitor"));
        tabs.addTab(tabs.newTab().setText("Log"));
        tabs.addTab(tabs.newTab().setText("Chart"));
        tabs.setTabGravity(tabs.GRAVITY_FILL);

        //Adapter
        final ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        final TabAdapter tabAdapter = new TabAdapter(getSupportFragmentManager(), 3);
        viewPager.setAdapter(tabAdapter);

        //탭 선택 이벤트
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs));
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.main_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item)
    {
        Toast toast = Toast.makeText(getApplicationContext(),"", Toast.LENGTH_LONG);

        switch(item.getItemId())
        {
            case R.id.menu1:
                toast.setText("로그아웃 기능 추가 예정");
                break;
            case R.id.menu2:
                toast.setText("새로고침 기능 추가 예정");
                break;
            case R.id.menu3:
                toast.setText("Select Menu3");
                break;
        }

        toast.show();

        return super.onOptionsItemSelected(item);
    }
}

