package com.henallux.smartcity.view;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.henallux.smartcity.R;
import com.henallux.smartcity.dataAccess.BarDAO;
import com.henallux.smartcity.exception.ImpossibleToFetchBarsException;
import com.henallux.smartcity.listener.FragmentListener;
import com.henallux.smartcity.model.Bar;

import java.util.ArrayList;

public class DrinkFragment extends Fragment {
    private LoadBars loadBars;
    private ListView listViewBarsToDisplay;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_drink, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View view = getView();
        listViewBarsToDisplay = (ListView) view.findViewById(R.id.BarsRecyclerView);
        loadBars = new LoadBars();
        loadBars.execute();
    }
    private class LoadBars extends AsyncTask<String, Void, ArrayList<Bar>> {
        private BarDAO barDAO;
        ArrayList<Bar> bars = new ArrayList<>();
        protected ArrayList<Bar> doInBackground(String... urls){
            barDAO = new BarDAO(getActivity().getApplicationContext());

            try{
                bars = barDAO.getAllBars();
            }catch(final ImpossibleToFetchBarsException e){
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                return null;
            }
            catch (final Exception e){
                System.out.println(e.getMessage());
            }
            return bars;
        }

        @Override
        protected void onPostExecute(final ArrayList<Bar> bars) {
            final ArrayList<String> barsDescriptions = arrayListBarToArrayListString(bars);
            ArrayAdapter<String> listBarAdapter= new ArrayAdapter<String>(
                    getActivity(),
                    android.R.layout.simple_list_item_1,
                    barsDescriptions
            );
            listViewBarsToDisplay.setAdapter(listBarAdapter);
            //gestion des clicks
            listViewBarsToDisplay.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    FragmentListener fragmentListener = (FragmentListener)getActivity();
                    fragmentListener.getBar(bars.get(position));
                }
            });
        }
    }
    private ArrayList<String> arrayListBarToArrayListString(ArrayList<Bar> barsArrayList) {
        ArrayList<String> bars = new ArrayList<String>();
        for(Bar bar : barsArrayList)
        {
            bars.add(bar.toString());
        }
        return bars;
    }
}
