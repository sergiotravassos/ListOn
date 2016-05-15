package com.sergiotravassos.liston;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.sergiotravassos.liston.model.Carro;

import org.parceler.Parcels;

public class DetalheCarroActivity extends AppCompatActivity {

    public static final  String EXTRA_CARRO = "carro";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_carro);

        Carro carro = Parcels.unwrap(getIntent().getParcelableExtra(EXTRA_CARRO));

        DetalheCarroFragment dcf = DetalheCarroFragment.newInstance(carro);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.detalhe, dcf, "detalhe")
                .commit();
    }
}
