package com.henallux.smartcity.view;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.henallux.smartcity.R;
import com.henallux.smartcity.dataAccess.MarketDAO;
import com.henallux.smartcity.model.Address;
import com.henallux.smartcity.model.Market;

import java.util.ArrayList;

public class MarketFragment extends Fragment {
    private RecyclerView marketsToDisplay;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_market, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View v = getView();
        marketsToDisplay = v.findViewById(R.id.MarketsRecyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(v.getContext());
        marketsToDisplay.setLayoutManager(layoutManager);
        new LoadMarkets().execute();
    }

    private class LoadMarkets extends AsyncTask<String, Void, ArrayList<Market>> {
        private MarketDAO marketDAO;

        protected ArrayList<Market> doInBackground(String... urls){
            marketDAO = new MarketDAO();

            ArrayList<Market> markets = new ArrayList<>();
            try{
                markets = new MarketDAO().getAllMarkets();
            }catch (Exception e){
                Log.i("Async",e.getMessage());
                e.printStackTrace();
            }
            Log.i("Async","Fin de doInBackGround (market)");
            return markets;
        }

        @Override
        protected void onPostExecute(ArrayList<Market> markets) {
            RecyclerView.Adapter adapter = new MarketAdapter(markets);
            marketsToDisplay.setAdapter(adapter);
            Log.i("Async","Fin de onPostExecute (market)");
        }
    }
}
