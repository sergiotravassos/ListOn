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
import java.util.Arrays;
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
    CarroTask mTask;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mCarros = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_lista_carro, container, false);
        ButterKnife.bind(this, layout);

        mAdapter = new CarroAdapter(getContext(), mCarros);
        mListView.setAdapter(mAdapter);
        return layout;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(mCarros.size() == 0 && mTask == null) {
            mTask = new CarroTask();
            mTask.execute();
        }
    }

    @OnItemClick(R.id.list_carro)
    void onItemClick(int position) {
        Carro carro = mCarros.get(position);
        if (getActivity() instanceof CliqueiNoCarroListener) {
            CliqueiNoCarroListener listener = (CliqueiNoCarroListener) getActivity();
            listener.carroFoiClicado(carro);
        }
    }

    class CarroTask extends AsyncTask<Void, Void, List<Carro>> {

        @Override
        protected List<Carro> doInBackground(Void... params) {

            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url("http://10.0.2.2:80/carros.json")
                    .build();
            List<Carro> carros = null;
            try {
                Response response = client.newCall(request).execute();
                String jsonString = response.body().string();

                Log.d("Teste", jsonString);

                Gson gson = new Gson();
                carros = Arrays.asList(gson.fromJson(jsonString, Carro[].class));

                return carros;

            }catch (Exception e){
                e.printStackTrace();
            }
            return carros;
        }

        @Override
        protected void onPostExecute(List<Carro> carros) {
            super.onPostExecute(carros);

            if(carros != null) {
                mCarros.clear();


                    mCarros = carros;

                mAdapter.notifyDataSetChanged();
            }

        }
    }

}
