package com.sergiotravassos.liston;

import android.content.Intent;
import android.os.Parcelable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.sergiotravassos.liston.model.Carro;

import org.parceler.Parcels;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
implements CliqueiNoCarroListener{

    @Bind(R.id.viewPager)
    ViewPager mViewPager;
    @Bind(R.id.tablayout)
    TabLayout mTabLayout;
    @Bind(R.id.toobar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);

        mViewPager.setAdapter(new CarroPager(getSupportFragmentManager()));
        mTabLayout.setupWithViewPager(mViewPager);
    }

    class CarroPager extends FragmentPagerAdapter{


        public CarroPager(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if(position == 0) return new ListaCarroFragment();
            return new ListaFavoritoFragment();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if(position == 0) return getString(R.string.aba_web);
            return getString(R.string.aba_favoritos);
        }

        @Override
        public int getCount() {
            return 2;
        }
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
