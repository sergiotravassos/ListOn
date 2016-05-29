package com.sergiotravassos.liston;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.sergiotravassos.liston.database.CarroDAO;
import com.sergiotravassos.liston.model.Carro;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

public class ListaFavoritoFragment extends Fragment {

    @Bind(R.id.list_carro)
    ListView mListView;
    @Bind(R.id.empty)
    View mEmpty;


    List<Carro> mCarros;
    ArrayAdapter<Carro> mAdapter;
    CarroDAO mDao;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mDao = new CarroDAO(getActivity());
        mCarros = mDao.listar();
        ((CarroApp)getActivity().getApplication()).getEventBus().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ((CarroApp)getActivity().getApplication()).getEventBus().unregister(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_lista_favorito, container, false);
        ButterKnife.bind(this, layout);

        mAdapter = new CarroAdapter(getContext(), mCarros);

        mListView.setEmptyView(mEmpty);

        mListView.setAdapter(mAdapter);
        return layout;

    }

    @OnItemClick(R.id.list_carro)
    void onItemClick(int position) {
        Carro carro = mCarros.get(position);
        if (getActivity() instanceof CliqueiNoCarroListener) {
            CliqueiNoCarroListener listener = (CliqueiNoCarroListener) getActivity();
            listener.carroFoiClicado(carro);
        }
    }

    @Subscribe
    public void atualizar(Carro carro){
        mCarros.clear();
        mCarros.addAll(mDao.listar());
        mAdapter.notifyDataSetChanged();
    }

}
