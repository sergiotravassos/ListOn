package com.sergiotravassos.liston;


import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.parceler.Parcels;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class DetalheCarroFragment extends Fragment {

    private static final String EXTRA_CARRO = "param1";

    @Bind(R.id.text_modelo)  TextView mTextModelo;
    @Bind(R.id.text_montadora) TextView mTextMontadora;

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
        super.onCreate(savedInstanceState);
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
        mTextMontadora.setText(mCarro.montadora);
        return view;
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
