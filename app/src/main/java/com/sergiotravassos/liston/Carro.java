package com.sergiotravassos.liston;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sergiotravassos on 14/05/16.
 */
public class Carro implements Parcelable{

    String montadora;
    String modelo;
    Integer anoFabric;

    public Carro(String montadora, String modelo, Integer anoFabric){
        this.montadora = montadora;
        this.modelo = modelo;
        this.anoFabric = anoFabric;
    }

    protected Carro(Parcel in) {
        montadora = in.readString();
        modelo = in.readString();
    }

    public static final Creator<Carro> CREATOR = new Creator<Carro>() {
        @Override
        public Carro createFromParcel(Parcel in) {
            return new Carro(in);
        }

        @Override
        public Carro[] newArray(int size) {
            return new Carro[size];
        }
    };

    @Override
    public String toString() {
        return montadora + "- " + modelo + " - " + anoFabric;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(montadora);
        dest.writeString(modelo);
    }
}
