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

import com.henallux.smartcity.R;
import com.henallux.smartcity.dataAccess.BarDAO;
import com.henallux.smartcity.model.Address;
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

        protected ArrayList<Bar> doInBackground(String... urls){
            barDAO = new BarDAO(getActivity().getApplicationContext());

            ArrayList<Bar> bars = new ArrayList<>();
            try{
                //bars = barDAO.getAllBars();

                Address address = new Address("Rue de l'ange", "5550", 10);
                ArrayList<String> moyensPayements = new ArrayList<String>();
                Bar bar = new Bar();
                bar.setNomCommerce("Green fairy");
                bar.setAddress(address);
                bar.setDescription("Restauration rapide");
                bar.setMail("burgerking@yahoo.com");
                bars.add(bar);

                Address address2 = new Address("Rue de fer", "5550", 13);
                Bar bar2= new Bar();
                bar2.setNomCommerce("Le verre royale");
                bar2.setAddress(address2);
                bar2.setDescription("");
                bar2.setMail("pizza_hut@yahoo.com");
                bars.add(bar2);

            }catch (Exception e){
                Log.i("Async",e.getMessage());

                e.printStackTrace();
            }
            return bars;
        }

        @Override
        protected void onPostExecute(ArrayList<Bar> bars) {
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
                    new ElementDetailFragment();
                    ElementDetailFragment elementDetailFragment = new ElementDetailFragment();
                    FragmentTransaction fragmentTransaction =getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, elementDetailFragment).commit();
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
