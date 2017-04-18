package com.example.gerry.virtual_market;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

public class ListOrderLineActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_order_line);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragmentOrderLineContainer);

        if(fragment==null){
            fragment = new CardOrderLineFragment();
            ;
            fm.beginTransaction().add(R.id.fragmentOrderLineContainer, fragment).commit();
        }
    }
}
