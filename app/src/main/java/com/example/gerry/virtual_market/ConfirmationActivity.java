package com.example.gerry.virtual_market;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Gerry on 4/17/2017.
 */

public class ConfirmationActivity extends AppCompatActivity {
    private ArrayList<Confirmation> listitems = new ArrayList<>();
    public TextView nameTextView;
    public TextView addressTextView;
    public TextView phoneTextView;
    public TextView totalPriceTextView;
    public Button backButton;
    public Button confirmationButton;
    String GET_JSON_DATA_HTTP_URL = "http://192.168.100.15:8001/api/virtualmarket/confirmation/";
    String urlUpdateStatus = "http://192.168.100.15:8001/api/virtualmarket/order/updateConfirmationStatus";
    String JSON_CUSTOMER_NAME = "name";
    String JSON_CUSTOMER_PHONE = "phone_number";
    String JSON_CUSTOMER_ADDRESS = "address";
    String JSON_TOTAL_PRICE = "total_price";
    Integer orderId = 0;

    JsonArrayRequest jsonArrayRequest;
    RequestQueue requestQueue;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        getSupportActionBar().setTitle("Order Masuk");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Intent intent = getIntent();
        orderId = intent.getIntExtra("order_id", 0);
        GET_JSON_DATA_HTTP_URL += Integer.toString(orderId);

        Log.d("URL", GET_JSON_DATA_HTTP_URL);

        getConfirmationData();

        nameTextView = (TextView) findViewById(R.id.name);
        addressTextView = (TextView) findViewById(R.id.address);
        phoneTextView = (TextView) findViewById(R.id.phone);
        totalPriceTextView = (TextView) findViewById(R.id.total);
        backButton = (Button) findViewById(R.id.back_button);
        confirmationButton = (Button) findViewById(R.id.confirm_button);

        confirmationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                POST_AVAILABILITY_STATUS();
                Intent intent = new Intent(v.getContext(), ListOrderActivity.class);
                v.getContext().startActivity(intent);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void getConfirmationData() {
        listitems.clear();
        Log.d("masuk getdata", "getConfirmationData: ");
        JSON_DATA_WEB_CALL();
    }

    private void JSON_DATA_WEB_CALL() {
        jsonArrayRequest = new JsonArrayRequest(GET_JSON_DATA_HTTP_URL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("Test1", "Masuk onResponse confirmation activity");
                        JSON_PARSE_DATA_AFTER_WEBCALL(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Test2", "Masuk error confirmation activity");
                    }
                });
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    private void JSON_PARSE_DATA_AFTER_WEBCALL(JSONArray response) {
        Log.d("Test3", "Masuk parser confirmation activity");
        for(int i=0; i<response.length(); i++){
            Confirmation confirmation = new Confirmation();

            JSONObject json = null;
            JSONObject subJsonUser = null;
            try{
                json = response.getJSONObject(i);
                subJsonUser = json.getJSONObject("user");
                confirmation.setCustomerName(subJsonUser.getString(JSON_CUSTOMER_NAME));
                confirmation.setCustomerAddress(subJsonUser.getString(JSON_CUSTOMER_ADDRESS));
                confirmation.setCustomerPhone(subJsonUser.getString(JSON_CUSTOMER_PHONE));
                confirmation.setTotalPrice(json.getInt(JSON_TOTAL_PRICE));
                Log.d("Test4", confirmation.getCustomerName());
                Log.d("Test5", confirmation.getCustomerAddress());
                Log.d("Test6", confirmation.getCustomerPhone());

            }catch (JSONException e){
                e.printStackTrace();
            }
            listitems.add(confirmation);

            nameTextView.setText(listitems.get(0).getCustomerName());
            addressTextView.setText(listitems.get(0).getCustomerAddress());
            phoneTextView.setText(listitems.get(0).getCustomerPhone());
            totalPriceTextView.setText(listitems.get(0).getTotalPrice());
        }
    }

    private void POST_AVAILABILITY_STATUS(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlUpdateStatus, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("update status", "update bro");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                VolleyLog.d("update error", "Error.." + error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", Integer.toString(orderId));

                return params;
            }
        };
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
