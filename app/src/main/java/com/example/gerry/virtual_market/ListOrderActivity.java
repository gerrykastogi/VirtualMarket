package com.example.gerry.virtual_market;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Gerry on 4/13/2017.
 */

public class ListOrderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_order);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragmentOrderContainer);

        if (fragment == null) {
            fragment = new CardOrderFragment();
            ;
            fm.beginTransaction().add(R.id.fragmentOrderContainer, fragment).commit();
        }
    }
}
