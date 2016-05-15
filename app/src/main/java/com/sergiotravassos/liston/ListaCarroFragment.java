package com.sergiotravassos.liston;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;
import com.sergiotravassos.liston.model.Carro;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ListaCarroFragment extends Fragment {

    @Bind(R.id.list_carro)
    ListView mListView;
    List<Carro> mCarros;
    ArrayAdapter<Carro> mAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_lista_carro, container, false);
        ButterKnife.bind(this, layout);

        mCarros = new ArrayList<>();
        mAdapter = new ArrayAdapter<>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                mCarros);
        mListView.setAdapter(mAdapter);
        return layout;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        new CarroTask().execute();
    }

    @OnItemClick(R.id.list_carro)
    void onItemClick(int position) {
        Carro carro = mCarros.get(position);
        if (getActivity() instanceof CliqueiNoCarroListener) {
            CliqueiNoCarroListener listener = (CliqueiNoCarroListener) getActivity();
            listener.carroFoiClicado(carro);
        }
    }


    public interface CliqueiNoCarroListener {
        void carroFoiClicado(Carro carro);
    }

    class CarroTask extends AsyncTask<Void, Void, Carro> {

        @Override
        protected Carro doInBackground(Void... params) {

            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url("https://dl.dropboxusercontent.com/u/58682848/carros.json")
                    .build();
            try {
                Response response = client.newCall(request).execute();
                String jsonString = response.body().string();

                Log.d("Teste", jsonString);

                Gson gson = new Gson();
                Carro carro = gson.fromJson(jsonString, Carro.class);

                return carro;

            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Carro carros) {
            super.onPostExecute(carros);

            if(carros != null) {
                mCarros.clear();

                for (Carro carro : mCarros) {
                    mCarros.add(carro);
                }
                mAdapter.notifyDataSetChanged();
            }

        }
    }

}
