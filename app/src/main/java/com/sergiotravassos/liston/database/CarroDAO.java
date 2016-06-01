package com.sergiotravassos.liston.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sergiotravassos.liston.model.Carro;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sergiotravassos on 28/05/16.
 */
public class CarroDAO {

    CarroHelper carroDbHelper;

    public CarroDAO(Context context){
        carroDbHelper = new CarroHelper(context);
    }

    public void inserir(Carro carro){

        SQLiteDatabase db = carroDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CarroHelper.COL_MODELO, carro.modelo);
        values.put(CarroHelper.COL_ANO, carro.ano);
        values.put(CarroHelper.COL_FABRICANTE, carro.fabricante);
        values.put(CarroHelper.COL_MOTOR, carro.motor);
        values.put(CarroHelper.COL_IMAGEM, carro.imagem);

        long id = db.insert(CarroHelper.NOME_TABELA, null, values);

        if (id == -1){
            throw new RuntimeException("Erro ao inserir o registro no banco de dados.");
        }

        db.close();

    }

    public void excluir(Carro carro){

        SQLiteDatabase db = carroDbHelper.getWritableDatabase();

        db.delete(CarroHelper.NOME_TABELA,
                CarroHelper.COL_MODELO + " = ? AND " + CarroHelper.COL_FABRICANTE + " = ?",
                new String[]{ carro.modelo, carro.fabricante });

        db.close();

    }

    public ArrayList<Carro> listar(){

        SQLiteDatabase db = carroDbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + CarroHelper.NOME_TABELA + " ORDER BY " + CarroHelper.COL_MODELO,
                null);

        int idx_modelo = cursor.getColumnIndex(CarroHelper.COL_MODELO);
        int idx_ano = cursor.getColumnIndex(CarroHelper.COL_ANO);
        int idx_fabricante = cursor.getColumnIndex(CarroHelper.COL_FABRICANTE);
        int idx_motor = cursor.getColumnIndex(CarroHelper.COL_MOTOR);
        int idx_imagem = cursor.getColumnIndex(CarroHelper.COL_IMAGEM);

        ArrayList<Carro> carros = new ArrayList<Carro>();

        while (cursor.moveToNext()){
            String modelo = cursor.getString(idx_modelo);
            String ano = cursor.getString(idx_ano);
            String fabricante = cursor.getString(idx_fabricante);
            String motor = cursor.getString(idx_motor);
            String imagem = cursor.getString(idx_imagem);

            Carro carro = new Carro(modelo, fabricante, ano, motor, imagem);
            carros.add(carro);
        }

        cursor.close();
        db.close();

        return carros;
    }

    public boolean isFavorito(Carro carro){
        SQLiteDatabase db = carroDbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT _id FROM " + CarroHelper.NOME_TABELA + " WHERE " + CarroHelper.COL_MODELO + " = ? AND " +
                        CarroHelper.COL_FABRICANTE + " = ?",
                new String[]{ carro.modelo, carro.fabricante });

        boolean existe = cursor.getCount() > 0;

        cursor.close();
        db.close();

        return existe;
    }

}
