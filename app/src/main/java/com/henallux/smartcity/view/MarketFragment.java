package com.henallux.smartcity.view;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.henallux.smartcity.R;
import com.henallux.smartcity.dataAccess.MarketDAO;
import com.henallux.smartcity.listener.FragmentListener;
import com.henallux.smartcity.model.Market;

import java.util.ArrayList;

public class MarketFragment extends Fragment {
    private RecyclerView marketsToDisplay;
    private ListView listViewMarketsToDisplay;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_market, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View v = getView();
        //pour une recyclerView changer listView dans le layout
        /*
        marketsToDisplay = v.findViewById(R.id.MarketsRecyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(v.getContext());
        marketsToDisplay.setLayoutManager(layoutManager);
         */
        listViewMarketsToDisplay = (ListView)v.findViewById(R.id.MarketsRecyclerView);
        new LoadMarkets().execute();
    }

    private class LoadMarkets extends AsyncTask<String, Void, ArrayList<Market>> {
        private MarketDAO marketDAO;
        ArrayList<Market> markets = new ArrayList<>();

        protected ArrayList<Market> doInBackground(String... urls){
            marketDAO = new MarketDAO(getActivity().getApplicationContext());

            try{
                markets = marketDAO.getAllMarkets();
            }catch (Exception e){
                Log.i("Async",e.getMessage());
                e.printStackTrace();
            }
            Log.i("Async","Fin de doInBackGround (market)");
            return markets;
        }

        @Override
        protected void onPostExecute(final ArrayList<Market> markets) {
            /*
            RecyclerView.Adapter adapter = new MarketAdapter(markets);
            marketsToDisplay.setAdapter(adapter);
             */
            final ArrayList<String> MarketsDescriptions = arrayListMarketToArrayListString(markets);
            ArrayAdapter<String> listMarketAdapter= new ArrayAdapter<String>(
                    getActivity(),
                    android.R.layout.simple_list_item_1,
                    MarketsDescriptions
            );
            listViewMarketsToDisplay.setAdapter(listMarketAdapter);
            //gestion des clicks
            listViewMarketsToDisplay.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    /*
                    new ElementDetailFragment();
                    ElementDetailFragment elementDetailFragment = new ElementDetailFragment();
                    FragmentTransaction fragmentTransaction =getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, elementDetailFragment).commit();
                    */
                    FragmentListener fragmentListener = (FragmentListener)getActivity();
                    fragmentListener.getMarket(markets.get(position));
                }
            });
        }
    }
    private ArrayList<String> arrayListMarketToArrayListString(ArrayList<Market> marketsArrayList) {
        ArrayList<String> markets = new ArrayList<String>();
        for(Market market: marketsArrayList)
        {
            markets.add(market.toString());
        }
        return markets;
    }
}
