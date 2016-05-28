package com.sergiotravassos.liston.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by sergiotravassos on 28/05/16.
 */
public class CarroHelper extends SQLiteOpenHelper{

    public static final int DB_VERSION = 1;
    public static final String  DB_NAME = "carros_db";

    public static final String NOME_TABELA = "carro";

    public static final String COL_ID = "_id";
    public static final String COL_MODELO = "modelo";
    public static final String COL_FABRICANTE = "fabricante";
    public static final String COL_ANO = "ano";
    public static final String COL_MOTOR = "motor";
    public static final String COL_IMAGEM = "imagem";

    public CarroHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + NOME_TABELA + "(" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_MODELO + " TEXT NOT NULL, " +
                COL_FABRICANTE + " TEXT NOT NULL, " +
                COL_ANO + " TEXT NOT NULL, " +
                COL_MOTOR + " TEXT NOT NULL, " +
                COL_IMAGEM + " TEXT NOT NULL )");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
