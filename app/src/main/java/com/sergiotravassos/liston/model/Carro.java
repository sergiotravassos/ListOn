package com.sergiotravassos.liston.model;

/**
 * Created by sergiotravassos on 14/05/16.
 */
import java.io.Serializable;


public class Carro implements Serializable{

    public String modelo;
    public String fabricante;
    public String ano;
    public String motor;
    public String imagem;

    public Carro (String modelo, String fabricante, String ano, String motor, String imagem) {
        this.modelo = modelo;
        this.fabricante = fabricante;
        this.ano = ano;
        this.motor = motor;
        this.imagem = imagem;
    }

    @Override
    public String toString() {
        return this.modelo + " (" + this.fabricante + ") - " + this.motor + " - " + this.ano;
    }
}
