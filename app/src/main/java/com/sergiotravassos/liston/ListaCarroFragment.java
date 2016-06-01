package com.sergiotravassos.liston;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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
    @Bind(R.id.swipe)
    SwipeRefreshLayout mSwipe;
    @Bind(R.id.empty)
    View mEmpty;

    ArrayList<Carro> mCarros;
    CarroAdapter mAdapter;
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

        mListView.setEmptyView(mEmpty);

        mListView.setAdapter(mAdapter);


        mSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                baixarJson();
            }
        });
        return layout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (mCarros.size() == 0 && mTask == null) {
            baixarJson();
        } else if (mTask != null && mTask.getStatus() == AsyncTask.Status.RUNNING) {
            showProgress();
        }
    }

    private void baixarJson() {
        ConnectivityManager cm = (ConnectivityManager)
                getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            mTask = new CarroTask();
            mTask.execute();
        } else {
            mSwipe.setRefreshing(false);
            Toast.makeText(getActivity(), R.string.erro_conexao, Toast.LENGTH_SHORT).show();
        }
    }

    private void showProgress() {
        mSwipe.post(new Runnable() {
            @Override
            public void run() {
                mSwipe.setRefreshing(true);
            }
        });
    }

    @OnItemClick(R.id.list_carro)
    void onItemClick(int position) {
        Carro carro = mCarros.get(position);
        if (getActivity() instanceof CliqueiNoCarroListener) {
            CliqueiNoCarroListener listener = (CliqueiNoCarroListener) getActivity();
            listener.carroFoiClicado(carro);
        }
    }

    class CarroTask extends AsyncTask<Void, Void, ArrayList<Carro>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgress();
        }

        @Override
        protected ArrayList<Carro> doInBackground(Void... params) {

            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    //http://10.0.2.2:80/carros.json
                    .url("https://dl.dropboxusercontent.com/u/58682848/carros.json")
                    .build();
            ArrayList<Carro> carros = null;
            try {
               // Thread.sleep(5000);
                Response response = client.newCall(request).execute();
                String jsonString = response.body().string();

                Log.d("Teste", jsonString);

                Gson gson = new Gson();
                carros = gson.fromJson(jsonString, new TypeToken<List<Carro>>() {
                }.getType());

                return carros;

            } catch (Exception e) {
                Log.e("Error doInBackground", e.getMessage());
            }
            return carros;
        }

        @Override
        protected void onPostExecute(ArrayList<Carro> carros) {
            super.onPostExecute(carros);

            if (carros != null) {
                mCarros = new ArrayList<>();
                mCarros = carros;

                if (getResources().getBoolean(R.bool.tablet) && mCarros.size() > 0) {
                    onItemClick(0);
                }
            }
            mSwipe.setRefreshing(false);
            mAdapter.notifyDataSetChanged();


        }
    }

}
