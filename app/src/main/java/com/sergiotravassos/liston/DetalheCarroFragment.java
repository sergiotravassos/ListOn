package com.sergiotravassos.liston;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sergiotravassos.liston.database.CarroDAO;
import com.sergiotravassos.liston.model.Carro;

import org.parceler.Parcels;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class DetalheCarroFragment extends Fragment {

    private static final String EXTRA_CARRO = "param1";

    @Bind(R.id.text_modelo)
    TextView mTextModelo;
    @Bind(R.id.text_fabricante)
    TextView mTextFabricante;
    @Bind(R.id.text_ano)
    TextView mTextAno;
    @Bind(R.id.text_motor)
    TextView mTextMotor;
    @Bind(R.id.img_capa)
    ImageView mImageCapa;
    @Bind(R.id.fab_favorito)
    FloatingActionButton mFabFavorito;

    private ShareActionProvider mShareActionProvider;

    CarroDAO mDAO;

    private Carro mCarro;

    public static DetalheCarroFragment newInstance(Carro carro) {

        DetalheCarroFragment fragment = new DetalheCarroFragment();
        Bundle args = new Bundle();
        Parcelable p = Parcels.wrap(carro);
        args.putParcelable(EXTRA_CARRO, p);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mDAO = new CarroDAO(getActivity());
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            Parcelable p = getArguments().getParcelable(EXTRA_CARRO);
            mCarro = Parcels.unwrap(p);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detalhe_carro, container, false);
        ButterKnife.bind(this, view);
        mTextModelo.setText(mCarro.modelo);
        mTextFabricante.setText(mCarro.fabricante);
        mTextAno.setText(mCarro.ano);
        mTextMotor.setText(mCarro.motor);
        Glide.with(getActivity()).load(mCarro.imagem).into(mImageCapa);
        toggleFavorito();
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_detalhe, menu);
        MenuItem item = menu.findItem(R.id.meu_item_share);
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);

        Intent it = new Intent(Intent.ACTION_SEND);
        it.putExtra(Intent.EXTRA_TEXT, mCarro.modelo);
        it.setType("text/plain");
        mShareActionProvider.setShareIntent(it);
    }

    private void toggleFavorito(){
        boolean favorito = mDAO.isFavorito(mCarro);

        mFabFavorito.setImageResource(
                favorito ? R.drawable.ic_remove : R.drawable.ic_check);
        mFabFavorito.setBackgroundTintList(
                favorito ? ColorStateList.valueOf(Color.RED) : ColorStateList.valueOf(Color.GREEN));
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.fab_favorito)
    public void favoritoClick(){
        if(mDAO.isFavorito(mCarro)){
            mDAO.excluir(mCarro);
        }else{
            mDAO.inserir(mCarro);
        }
        mFabFavorito.animate()
                .scaleX(0)
                .scaleY(0)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        toggleFavorito();
                        mFabFavorito.animate()
                                .scaleX(1)
                                .scaleY(1)
                                .setListener(null);
                    }
                });
        ((CarroApp)getActivity().getApplication()).getEventBus().post(mCarro);
    }
}
