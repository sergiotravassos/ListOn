package com.sergiotravassos.liston;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class DetalheCarroFragment extends Fragment {

    private static final String EXTRA_CARRO = "param1";

    private Carro mCarro;

    public static DetalheCarroFragment newInstance(Carro carro) {
        DetalheCarroFragment fragment = new DetalheCarroFragment();
        Bundle args = new Bundle();
        args.putParcelable(EXTRA_CARRO, carro);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCarro = getArguments().getParcelable(EXTRA_CARRO);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detalhe_carro, container, false);
        TextView txt = (TextView)view.findViewById(R.id.text_carro);
        txt.setText(mCarro.toString());
        return view;
    }

}
