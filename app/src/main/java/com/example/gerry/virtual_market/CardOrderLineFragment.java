package com.example.gerry.virtual_market;

import android.app.DownloadManager;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Gerry on 4/13/2017.
 */

public class CardOrderLineFragment extends Fragment {

    private int Images[] = {R.drawable.tomat, R.drawable.bawang_putih, R.drawable.ayam,
            R.drawable.wortel, R.drawable.ayam, R.drawable.daging, R.drawable.wortel, R.drawable.wortel, R.drawable.ayam};
    private ArrayList<OrderLine> listOrderLines = new ArrayList<>();
    private RecyclerView MyRecycleView;
    JsonArrayRequest jsonArrayRequest;
    RequestQueue requestQueue;
    String orderLineId = "";
    String productPrice;
    String ORDER_ID = "";

    // URL
    String GET_JSON_DATA_HTTP_URL = "http://192.168.100.18:8000/api/virtualmarket/orderline/";
    String POST_PRICE_DATA = "http://192.168.100.18:8000/api/virtualmarket/orderline/updatePrice";
    String POST_UPDATE_STATUS_DATA = "http://192.168.100.18:8000/api/virtualmarket/orderline/updateStatus";

    // JSON Data
    String JSON_ORDER_LINE_ID = "id";
    String JSON_PRODUCT_NAME = "product_id";
    String JSON_PRODUCT_QUANTITY = "quantity";
    String JSON_PRODUCT_PRICE = "price";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        String orderId = getArguments().getString("order_id");
        ORDER_ID = orderId;
        GET_JSON_DATA_HTTP_URL += orderId;
        initializeList();
        getActivity().setTitle("Order Line List");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_order_line_card, container, false);
        MyRecycleView = (RecyclerView) view.findViewById(R.id.orderLineRecycle);
        MyRecycleView.setHasFixedSize(true);
        LinearLayoutManager myLayoutManager = new LinearLayoutManager(getActivity());
        myLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        MyRecycleView.setLayoutManager(myLayoutManager);

        return view;
    }

    public class MyAdapter extends RecyclerView.Adapter<MyViewHolder>{
        private ArrayList<OrderLine> list;

        public MyAdapter(ArrayList<OrderLine> Data){
            list = Data;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_order_lines, parent, false);
            MyViewHolder holder = new MyViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position){
            holder.titleTextView.setText(list.get(position).getProductName());
            holder.quantityTextView.setText(list.get(position).getQuantity());
            holder.productPriceEditText.setText(list.get(position).getProductPrice());
            holder.coverImageView.setImageResource(list.get(position).getImageResourceId());
            holder.coverImageView.setTag(list.get(position).getImageResourceId());
            holder.likeImageView.setTag(R.drawable.ic_yes);

            holder.likeImageView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    orderLineId = list.get(position).getOrderLineId();
                    productPrice = holder.productPriceEditText.getText().toString();
                    Log.d("Order Line Id", orderLineId);
                    Log.d("Product Price", productPrice);
                    POST_PRICE_DATA();
                    Toast.makeText(getActivity(), "Harga " + holder.titleTextView.getText()+
                                " berhasil diupdate", Toast.LENGTH_SHORT).show();
                }
            });

            holder.shareImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    orderLineId = list.get(position).getOrderLineId();
                    POST_AVAILABILITY_STATUS();
                    Toast.makeText(getActivity(), holder.titleTextView.getText()+
                            " tidak tersedia", Toast.LENGTH_SHORT).show();
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
        public TextView quantityTextView;
        public EditText productPriceEditText;
        public ImageView coverImageView;
        public ImageView likeImageView;
        public ImageView shareImageView;

        public MyViewHolder(View v){
            super(v);
            titleTextView = (TextView) v.findViewById(R.id.titleTextView);
            quantityTextView = (TextView) v.findViewById(R.id.quantityTextView);
            productPriceEditText = (EditText) v.findViewById(R.id.productPriceTextView);
            coverImageView = (ImageView) v.findViewById(R.id.coverImageView);
            likeImageView = (ImageView) v.findViewById(R.id.likeImageView);
            shareImageView = (ImageView) v.findViewById(R.id.shareImageView);
        }
    }

    public void initializeList() {
        listOrderLines.clear();

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

                    }
                });
        requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(jsonArrayRequest);
    }

    private void JSON_PARSE_DATA_AFTER_WEBCALL(JSONArray response) {
        for(int i=0; i<response.length(); i++){
            OrderLine orderLine = new OrderLine();

            JSONObject json = null;
            try{
                json = response.getJSONObject(i);
                orderLine.setOrderLineId(json.getInt(JSON_ORDER_LINE_ID));
                orderLine.setProductName(json.getInt(JSON_PRODUCT_NAME));
                orderLine.setQuantity(json.getInt(JSON_PRODUCT_QUANTITY));
                orderLine.setProductPrice(json.getInt(JSON_PRODUCT_PRICE));
                orderLine.setImageResourceId(Images[i]);

            }catch (JSONException e){
                e.printStackTrace();
            }
            listOrderLines.add(orderLine);
        }
        if (listOrderLines.size()>0 & MyRecycleView!=null){
            MyRecycleView.setAdapter(new CardOrderLineFragment.MyAdapter(listOrderLines));
        }
    }

    private void POST_PRICE_DATA(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, POST_PRICE_DATA, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("testestestsstese", "ewrjhfjkhsdkjdfh");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                VolleyLog.d("testtesttest", "Error.." + error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", orderLineId);
                params.put("price", productPrice);

                return params;
            }
        };
        requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

    private void POST_AVAILABILITY_STATUS(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, POST_UPDATE_STATUS_DATA, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("onResponse", "POST_AVAILABILITY_STATUS");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("availability status", "Error.." + error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", orderLineId);
                params.put("status", "false");

                return params;
            }
        };
        requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }
}
