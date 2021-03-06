package com.henallux.smartcity.view;

import android.content.Intent;
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
import android.widget.SearchView;
import android.widget.Toast;

import com.henallux.smartcity.R;
import com.henallux.smartcity.dataAccess.MarketDAO;
import com.henallux.smartcity.exception.ImpossibleToFetchCommercesException;
import com.henallux.smartcity.exception.UnauthorizedException;
import com.henallux.smartcity.listener.FragmentListener;
import com.henallux.smartcity.model.Market;

import java.util.ArrayList;

public class MarketFragment extends Fragment {
    private ListView listViewMarketsToDisplay;
    private SearchView searchView;
    private LoadMarkets loadMarkets;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_market, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View v = getView();
        listViewMarketsToDisplay = v.findViewById(R.id.MarketsRecyclerView);
        if (getActivity() != null){
            loadMarkets = new LoadMarkets();
            loadMarkets.execute();
        }
        searchView = v.findViewById(R.id.searchViewMarket);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                loadMarkets = new LoadMarkets(query);
                loadMarkets.execute();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                loadMarkets = new LoadMarkets(newText);
                loadMarkets.execute();
                return true;
            }
        });
    }

    private class LoadMarkets extends AsyncTask<String, Void, ArrayList<Market>> {
        private MarketDAO marketDAO;
        ArrayList<Market> markets = new ArrayList<>();
        private String query;

        private LoadMarkets(String query) {
            this.query = query;
        }

        private LoadMarkets() {
        }

        protected ArrayList<Market> doInBackground(String... urls) {
            if (getActivity() != null)
                marketDAO = new MarketDAO(getActivity().getApplicationContext());

            try {
                if (this.query == null) {
                    markets = marketDAO.getAllMarkets("");
                } else {
                    markets = marketDAO.getAllMarkets(this.query);
                }
            }
            catch(final UnauthorizedException e){
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                getActivity().startActivity(new Intent(getActivity(), MainActivity.class));
            }
            catch (final ImpossibleToFetchCommercesException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                return null;
            } catch (final Exception e) {
                System.out.println(e.getMessage());
            }
            return markets;
        }

        @Override
        protected void onPostExecute(final ArrayList<Market> markets) {
            if (markets != null) {
                final ArrayList<String> MarketsDescriptions = arrayListMarketToArrayListString(markets);
                if (getActivity() != null && MarketsDescriptions != null) {
                    ArrayAdapter<String> listMarketAdapter = new ArrayAdapter<String>(
                            getActivity(),
                            android.R.layout.simple_list_item_1,
                            MarketsDescriptions
                    );
                    listViewMarketsToDisplay.setAdapter(listMarketAdapter);
                    listViewMarketsToDisplay.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            FragmentListener fragmentListener = (FragmentListener) getActivity();
                            fragmentListener.getMarket(markets.get(position));
                        }
                    });
                }
            }

        }
    }

    private ArrayList<String> arrayListMarketToArrayListString(ArrayList<Market> marketsArrayList) {
        ArrayList<String> markets = new ArrayList<String>();
        for (Market market : marketsArrayList) {
            markets.add(market.toString());
        }
        return markets;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if ( loadMarkets != null) {
            loadMarkets.cancel(true);
        }
    }
}
