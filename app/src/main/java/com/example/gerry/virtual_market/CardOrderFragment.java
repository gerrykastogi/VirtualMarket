package com.example.gerry.virtual_market;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Integer.parseInt;

/**
 * Created by Gerry on 4/13/2017.
 */

public class CardOrderFragment extends Fragment {
    private ArrayList<Order> listitems = new ArrayList<>();
    private RecyclerView MyRecycleView;
    JsonArrayRequest jsonArrayRequest;
    RequestQueue requestQueue;
    Integer ORDER_ID = 0;

    // URL
    String GET_JSON_DATA_HTTP_URL = "http://192.168.100.18:8000/api/virtualmarket/order/getData/2";
    String POST_UPDATE_STATUS_DATA = "http://192.168.100.18:8000/api/virtualmarket/order/updateDeliveryStatus/";

    // JSON data
    String JSON_NAME = "name";
    String JSON_ORDER_ID = "id";
    String JSON_TOTAL_PRODUCT = "total_product";
    String JSON_TOTAL_PRICE = "total_price";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        initializeList();
        getActivity().setTitle("Order List");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_order_card, container, false);
        MyRecycleView = (RecyclerView) view.findViewById(R.id.orderRecycle);
        MyRecycleView.setHasFixedSize(true);
        LinearLayoutManager myLayoutManager = new LinearLayoutManager(getActivity());
        myLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        MyRecycleView.setLayoutManager(myLayoutManager);

        return view;
    }

    public class MyAdapter extends RecyclerView.Adapter<MyViewHolder>{
        private ArrayList<Order> list;

        public MyAdapter(ArrayList<Order> Data){
            list = Data;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_order, parent, false);
            MyViewHolder holder = new MyViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position){
            holder.titleTextView.setText(list.get(position).getCustomerName());
            holder.totalProductsTextView.setText(list.get(position).getTotalProducts());
            holder.totalPricesTextView.setText(list.get(position).getTotalPrices());

            holder.shopButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ORDER_ID = list.get(position).getOrderId();
                    Intent intent = new Intent(view.getContext(), ListOrderLineActivity.class);
                    intent.putExtra("order_id", ORDER_ID);
                    view.getContext().startActivity(intent);
                }
            });

            holder.finishButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ORDER_ID = list.get(position).getOrderId();
                    UPDATE_ORDER_STATUS();
                    Intent intent = new Intent(v.getContext(), ConfirmationActivity.class);
                    intent.putExtra("order_id", ORDER_ID);
                    v.getContext().startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount(){
            return list.size();
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView titleTextView;
        public TextView totalProductsTextView;
        public TextView totalPricesTextView;
        public Button shopButton;
        public Button finishButton;

        public MyViewHolder(View v){
            super(v);
            titleTextView = (TextView) v.findViewById(R.id.titleTextView);
            totalProductsTextView = (TextView) v.findViewById(R.id.totalProductsTextView);
            totalPricesTextView = (TextView) v.findViewById(R.id.totalPricesTextView);
            shopButton = (Button) v.findViewById(R.id.shopButton);
            finishButton = (Button) v.findViewById(R.id.finishButton);
        }
    }

    public void initializeList() {
        listitems.clear();

        JSON_DATA_WEB_CALL();
    }

    private void JSON_DATA_WEB_CALL() {
        jsonArrayRequest = new JsonArrayRequest(GET_JSON_DATA_HTTP_URL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        JSON_PARSE_DATA_AFTER_WEBCALL(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d("get data error", "Error.." + error.getMessage());
                    }
                });
        requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(jsonArrayRequest);
    }

    private void JSON_PARSE_DATA_AFTER_WEBCALL(JSONArray response) {
        for(int i=0; i<response.length(); i++){
            Order order = new Order();

            JSONObject json = null;
            JSONObject subJson = null;
            try{
                json = response.getJSONObject(i);
                subJson = json.getJSONObject("user");
                order.setCustomerName(subJson.getString(JSON_NAME));
                order.setOrderId(json.getInt(JSON_ORDER_ID));
                order.setTotalProducts(json.getInt(JSON_TOTAL_PRODUCT));
                order.setTotalPrices(json.getInt(JSON_TOTAL_PRICE));

            }catch (JSONException e){
                e.printStackTrace();
            }
            listitems.add(order);
        }

        if (listitems.size()>0 & MyRecycleView!=null){
            MyRecycleView.setAdapter(new MyAdapter(listitems));
        }
    }

    private void UPDATE_ORDER_STATUS(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, POST_UPDATE_STATUS_DATA, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("onResponse", "UPDATE_ORDER_STATUS");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("update error", "Error.." + error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", Integer.toString(ORDER_ID));
                params.put("status", "3");

                return params;
            }
        };
        requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

}
