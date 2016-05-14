package com.sergiotravassos.liston;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity
implements ListaCarroFragment.CliqueiNoCarroListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void carroFoiClicado(Carro carro) {
        DetalheCarroFragment dcf = DetalheCarroFragment.newInstance(carro);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.detalhe, dcf, "detalhe")
                .commit();
    }
}
