package com.henallux.smartcity.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.henallux.smartcity.R;
import com.henallux.smartcity.model.Market;
import com.henallux.smartcity.model.Restaurant;

import java.util.ArrayList;

public class MarketAdapter extends RecyclerView.Adapter<MarketAdapter.ViewHolder> {

    private ArrayList<Market> dataSet;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView textView;
        public ViewHolder (View v){
            super(v);
            textView = v.findViewById(R.id.MarketItemTextView);
        }
    }

    public MarketAdapter (ArrayList<Market> dataSet){
        this.dataSet = dataSet;
    }

    @Override
    public MarketAdapter.ViewHolder onCreateViewHolder (ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_market,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        holder.textView.setText(dataSet.get(position).toString());
    }

    @Override
    public int getItemCount(){
        return dataSet.size();
    }
}
