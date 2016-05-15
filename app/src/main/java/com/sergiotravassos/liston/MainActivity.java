package com.sergiotravassos.liston;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.parceler.Parcels;

public class MainActivity extends AppCompatActivity
implements ListaCarroFragment.CliqueiNoCarroListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void carroFoiClicado(Carro carro) {
        if(getResources().getBoolean(R.bool.tablet)) {
            DetalheCarroFragment dcf = DetalheCarroFragment.newInstance(carro);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.detalhe, dcf, "detalhe")
                    .commit();
        }else {
            Intent it = new Intent(this, DetalheCarroActivity.class);
            Parcelable p = Parcels.wrap(carro);
            it.putExtra(DetalheCarroActivity.EXTRA_CARRO, p);
            startActivity(it);
        }
    }
}
