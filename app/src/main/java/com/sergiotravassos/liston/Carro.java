package com.sergiotravassos.liston;

import org.parceler.Parcel;

/**
 * Created by sergiotravassos on 14/05/16.
 */

@Parcel
public class Carro {

    public Carro(){

    }

    String montadora;
    String modelo;
    Integer anoFabric;

    public Carro(String montadora, String modelo, Integer anoFabric){
        this.montadora = montadora;
        this.modelo = modelo;
        this.anoFabric = anoFabric;
    }

    @Override
    public String toString() {
        return montadora + "- " + modelo + " - " + anoFabric;
    }

}
