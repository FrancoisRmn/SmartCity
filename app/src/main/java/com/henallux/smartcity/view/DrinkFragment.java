package com.henallux.smartcity.view;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.henallux.smartcity.R;
import com.henallux.smartcity.dataAccess.BarDAO;
import com.henallux.smartcity.exception.ImpossibleToFetchCommercesException;
import com.henallux.smartcity.exception.UnauthorizedException;
import com.henallux.smartcity.listener.FragmentListener;
import com.henallux.smartcity.model.Bar;

import java.util.ArrayList;

public class DrinkFragment extends Fragment {
    private LoadBars loadBars;
    private ListView listViewBarsToDisplay;
    private SearchView searchView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_drink, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View view = getView();
        listViewBarsToDisplay = view.findViewById(R.id.BarsRecyclerView);
        loadBars = new LoadBars();
        if (getActivity() != null)
            loadBars.execute();
        searchView = view.findViewById(R.id.barSearchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                loadBars = new LoadBars(query);
                loadBars.execute();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                loadBars =  new LoadBars(newText);
                loadBars.execute();
                return true;
            }
        });
    }

    private class LoadBars extends AsyncTask<String, Void, ArrayList<Bar>> {
        private BarDAO barDAO;
        private String query;

        private LoadBars(String query) {
            this.query = query;
        }

        private LoadBars() {
        }

        ArrayList<Bar> bars = new ArrayList<>();

        protected ArrayList<Bar> doInBackground(String... urls) {
            if (getActivity() != null)
                barDAO = new BarDAO(getActivity().getApplicationContext());

            try {
                if (this.query == null) {
                    bars = barDAO.getAllBars("");
                } else {
                    bars = barDAO.getAllBars(this.query);
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
            return bars;
        }

        @Override
        protected void onPostExecute(final ArrayList<Bar> bars) {
            if (bars != null) {
                final ArrayList<String> barsDescriptions = arrayListBarToArrayListString(bars);
                if (getActivity() != null && barsDescriptions != null) {
                    ArrayAdapter<String> listBarAdapter = new ArrayAdapter<>(
                            getActivity(),
                            android.R.layout.simple_list_item_1,
                            barsDescriptions
                    );
                    listViewBarsToDisplay.setAdapter(listBarAdapter);
                    //gestion des clicks
                    listViewBarsToDisplay.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            FragmentListener fragmentListener = (FragmentListener) getActivity();
                            fragmentListener.getBar(bars.get(position));
                        }
                    });
                }
            }
        }
    }

    private ArrayList<String> arrayListBarToArrayListString(ArrayList<Bar> barsArrayList) {
        ArrayList<String> bars = new ArrayList<>();
        for (Bar bar : barsArrayList) {
            bars.add(bar.toString());
        }
        return bars;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if ( loadBars != null) {
            loadBars.cancel(true);
        }
    }
}
