package com.example.gerry.virtual_market;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class ListOrderLineActivity extends AppCompatActivity {
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_order_line);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        getSupportActionBar().setTitle("Barang yang Dipesan");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Intent intent = getIntent();
        Integer orderId = intent.getIntExtra("order_id", 0);

        Bundle bundle = new Bundle();
        bundle.putString("order_id", Integer.toString(orderId));

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragmentOrderLineContainer);

        if(fragment==null){
            fragment = new CardOrderLineFragment();
            fragment.setArguments(bundle);
            ;
            fm.beginTransaction().add(R.id.fragmentOrderLineContainer, fragment).commit();
        }
    }
}
