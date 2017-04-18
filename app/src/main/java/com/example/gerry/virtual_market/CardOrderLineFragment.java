package com.example.gerry.virtual_market;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

public class CardOrderLineFragment extends Fragment {

    private int Images[] = {R.drawable.tomat, R.drawable.bawang_putih, R.drawable.ayam,
            R.drawable.wortel, R.drawable.ayam, R.drawable.daging, R.drawable.wortel, R.drawable.wortel, R.drawable.ayam};
    private ArrayList<OrderLine> listOrderLines = new ArrayList<>();
    private RecyclerView MyRecycleView;

    String GET_JSON_DATA_HTTP_URL = "http://192.168.100.6:8000/showOrderLine/2";
    String JSON_PRODUCT_NAME = "product_name";
    String JSON_PRODUCT_QUANTITY = "quantity";
    String JSON_PRODUCT_PRICE = "price";

    JsonArrayRequest jsonArrayRequest;
    RequestQueue requestQueue;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
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
        public void onBindViewHolder(final MyViewHolder holder, int position){
            holder.titleTextView.setText(list.get(position).getProductName());
            holder.quantityTextView.setText(list.get(position).getQuantity());
            holder.productPriceTextView.setText(list.get(position).getProductPrice());
            holder.coverImageView.setImageResource(list.get(position).getImageResourceId());
            holder.coverImageView.setTag(list.get(position).getImageResourceId());
            holder.likeImageView.setTag(R.drawable.ic_yes);
        }

        @Override
        public int getItemCount(){
            return list.size();
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView titleTextView;
        public TextView quantityTextView;
        public TextView productPriceTextView;
        public ImageView coverImageView;
        public ImageView likeImageView;
        public ImageView shareImageView;

        public MyViewHolder(View v){
            super(v);
            titleTextView = (TextView) v.findViewById(R.id.titleTextView);
            quantityTextView = (TextView) v.findViewById(R.id.quantityTextView);
            productPriceTextView = (TextView) v.findViewById(R.id.productPriceTextView);
            coverImageView = (ImageView) v.findViewById(R.id.coverImageView);
            likeImageView = (ImageView) v.findViewById(R.id.likeImageView);
            shareImageView = (ImageView) v.findViewById(R.id.shareImageView);
            likeImageView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    int id = (int) likeImageView.getTag();

                    if(id == R.drawable.ic_yes){
                        likeImageView.setTag(R.drawable.ic_yes);
                        likeImageView.setImageResource(R.drawable.ic_yes);
                        Toast.makeText(getActivity(), titleTextView.getText()+
                                " added to favorites", Toast.LENGTH_SHORT).show();
                    } else {
                        likeImageView.setTag(R.drawable.ic_yes);
                        likeImageView.setImageResource(R.drawable.ic_yes);
                        Toast.makeText(getActivity(), titleTextView.getText()+
                                " removed from favorites", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            shareImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri imageUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                            "://" + getResources().getResourcePackageName(coverImageView.getId()) +
                            '/' + "drawable" + "/" + getResources().getResourceEntryName((int) coverImageView.getTag()));
                    Intent shareIntent = new Intent();
                    shareIntent.setAction(Intent.ACTION_SEND);
                    shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
                    shareIntent.setType("image/jpeg");
                    startActivity(Intent.createChooser(shareIntent, getResources().getText(R.string.send_to)));
                }
            });
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
                orderLine.setProductName(json.getString(JSON_PRODUCT_NAME));
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
}
