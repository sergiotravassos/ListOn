package com.sergiotravassos.liston;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sergiotravassos.liston.model.Carro;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by sergiotravassos on 15/05/16.
 */
public class CarroAdapter extends ArrayAdapter<Carro> {

    public CarroAdapter(Context context, List<Carro> carros) {
        super(context, 0, carros);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Carro carro = getItem(position);

        ViewHolder viewHolder;
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.item_carro, parent, false);
            viewHolder = new ViewHolder(convertView);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }

        Glide.with(getContext()).load(carro.imagem).into(viewHolder.imageView);
        viewHolder.txtModelo.setText(carro.modelo);
        viewHolder.txtFabricante.setText(carro.fabricante);

        return convertView;
    }

    static class ViewHolder{
        @Bind(R.id.img_capa)
        ImageView imageView;
        @Bind(R.id.text_modelo)
        TextView txtModelo;
        @Bind(R.id.text_fabricante)
        TextView txtFabricante;

        public ViewHolder(View parent){
            ButterKnife.bind(this, parent);
            parent.setTag(this);
        }
    }
}
