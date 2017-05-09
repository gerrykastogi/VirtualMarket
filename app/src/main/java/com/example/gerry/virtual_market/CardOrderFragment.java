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
 * Created by Gerry on 4/13/2017.
 */

public class CardOrderFragment extends Fragment {

    private ArrayList<Order> listitems = new ArrayList<>();
    private RecyclerView MyRecycleView;
    String GET_JSON_DATA_HTTP_URL = "http://192.168.100.11:8000/showOrder/1";
    String JSON_NAME = "name";
    String JSON_TOTAL_PRODUCT = "total_products";
    String JSON_TOTAL_PRICES = "total_price";

    JsonArrayRequest jsonArrayRequest;
    RequestQueue requestQueue;

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
        public void onBindViewHolder(final MyViewHolder holder, int position){
            holder.titleTextView.setText(list.get(position).getCustomerName());
            holder.totalProductsTextView.setText(list.get(position).getTotalProducts());
            holder.totalPricesTextView.setText(list.get(position).getTotalPrices());
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

            shopButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), ListOrderLineActivity.class);
                    view.getContext().startActivity(intent);
                }
            });

            finishButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(itemView.getContext(), ConfirmationActivity.class);
                    v.getContext().startActivity(intent);
                }
            });
        }
    }

    public void initializeList() {
        listitems.clear();
        Log.d("Test1", "Masuk Initialize List");
        JSON_DATA_WEB_CALL();
    }

    private void JSON_DATA_WEB_CALL() {
        Log.d("Test2", "Masuk Get JSON");
        jsonArrayRequest = new JsonArrayRequest(GET_JSON_DATA_HTTP_URL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("Test3", "Masuk OnResponse");
                        JSON_PARSE_DATA_AFTER_WEBCALL(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(jsonArrayRequest);
    }

    private void JSON_PARSE_DATA_AFTER_WEBCALL(JSONArray response) {
        Log.d("Test4", "Masuk Parser");
        for(int i=0; i<response.length(); i++){
            Order order = new Order();

            JSONObject json = null;
            try{
                json = response.getJSONObject(i);
                order.setCustomerName(json.getString(JSON_NAME));
                order.setTotalProducts(json.getInt(JSON_TOTAL_PRODUCT));
                order.setTotalPrices(json.getInt(JSON_TOTAL_PRICES));

            }catch (JSONException e){
                e.printStackTrace();
            }
            listitems.add(order);
        }
        Log.d("Test5", Integer.toString(listitems.size()));
        if (listitems.size()>0 & MyRecycleView!=null){
            MyRecycleView.setAdapter(new MyAdapter(listitems));
        }
    }

}
