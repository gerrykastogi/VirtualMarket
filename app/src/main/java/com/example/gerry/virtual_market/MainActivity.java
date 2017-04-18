package com.example.gerry.virtual_market;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button launchListOrderActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        launchListOrderActivity = (Button) findViewById(R.id.order_list_button);
        launchListOrderActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchActivity();
            }
        });
    }

    private void launchActivity(){
        Intent intent = new Intent(this, ListOrderActivity.class);
        startActivity(intent);
    }
}
