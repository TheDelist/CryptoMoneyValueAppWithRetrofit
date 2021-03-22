package com.sametkemalozdemir.retrofitjava.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sametkemalozdemir.retrofitjava.R;
import com.sametkemalozdemir.retrofitjava.model.CryptoModel;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RowHolder> {
    private ArrayList<CryptoModel> cryptoModelArrayList;
    private String[] colors={"#ffe666","#ff3f00","#f6cebf","#66ccff","#c9b8ff"};

    public RecyclerAdapter(ArrayList<CryptoModel> cryptoModelArrayList) {
        this.cryptoModelArrayList = cryptoModelArrayList;
    }

    @NonNull
    @Override
    public RowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View customView=inflater.inflate(R.layout.row_layout,parent,false);


                return new RowHolder(customView);
    }

    @Override
    public void onBindViewHolder(@NonNull RowHolder holder, int position) {
    holder.bind(cryptoModelArrayList.get(position),colors,position);
    }

    @Override
    public int getItemCount() {
        return cryptoModelArrayList.size();
    }

    public class RowHolder extends RecyclerView.ViewHolder {
        TextView textName,textPrice;


        public RowHolder(@NonNull View itemView) {
            super(itemView);


        }

        public void bind(CryptoModel model,String[] colors,Integer position){
            itemView.setBackgroundColor(Color.parseColor(colors[position % 5]));
            textName=itemView.findViewById(R.id.text_name);
            textPrice=itemView.findViewById(R.id.text_price);
            textName.setText(model.getCurrency());
            textPrice.setText(model.getPrice());

        }
    }
}
