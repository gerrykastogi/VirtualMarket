package com.example.gerry.virtual_market;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Gerry on 4/17/2017.
 */

public class ConfirmationActivity extends AppCompatActivity {

    private ArrayList<Confirmation> listitems = new ArrayList<>();
    public TextView nameTextView;
    public TextView addressTextView;
    public TextView phoneTextView;
    public Button backButton;
    public Button confirmationButton;
    String GET_JSON_DATA_HTTP_URL = "http://192.168.100.18:8000/api/virtualmarket/confirmation/";
    String JSON_CUSTOMER_NAME = "name";
    String JSON_CUSTOMER_PHONE = "phone_number";
    String JSON_CUSTOMER_ADDRESS = "address";

    JsonArrayRequest jsonArrayRequest;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

        Intent intent = getIntent();
        Integer orderId = intent.getIntExtra("order_id", 0);
        GET_JSON_DATA_HTTP_URL += Integer.toString(orderId);

        Log.d("URL", GET_JSON_DATA_HTTP_URL);

        getConfirmationData();

        nameTextView = (TextView) findViewById(R.id.name);
        addressTextView = (TextView) findViewById(R.id.address);
        phoneTextView = (TextView) findViewById(R.id.phone);
        backButton = (Button) findViewById(R.id.back_button);
        confirmationButton = (Button) findViewById(R.id.confirm_button);


    }

    public void getConfirmationData() {
        listitems.clear();

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
        }
    }
}
