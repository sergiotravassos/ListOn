package com.sergiotravassos.liston;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ListaCarroFragment extends Fragment {

    List<Carro> mCarros;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mCarros = new ArrayList<>();
        mCarros.add(new Carro("Mitsubish", "Pajero Full", 2016));
        mCarros.add(new Carro("Mitsubishi", "Pajero Dakar", 2016));
        mCarros.add(new Carro("Mitsubishi", "Outlander", 2016));
        mCarros.add(new Carro("Mitsubishi", "L200 Triton", 2016));
        mCarros.add(new Carro("Mitsubishi", "ASX", 2016));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_lista_carro, container, false);

        ListView listView = (ListView) layout.findViewById(R.id.list_carro);
        listView.setAdapter(new ArrayAdapter<Carro>(getActivity(), android.R.layout.simple_list_item_1, mCarros));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Carro carro = mCarros.get(position);
                if(getActivity() instanceof CliqueiNoCarroListener){
                    CliqueiNoCarroListener listener = (CliqueiNoCarroListener)getActivity();
                    listener.carroFoiClicado(carro);
                }
            }
        });

        return layout;

    }

   public interface CliqueiNoCarroListener{
        void carroFoiClicado(Carro carro);
    }

}
