package com.myon.jukka.tekusahkotracker;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Jukka on 01/11/2015.
 */
public class DatabaseHelper extends SQLiteOpenHelper {


    public static final String DATABASE_NAME = "teku_SahkoDB.db";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    //DB luonti, luoko helper sen jos sitä ei ole olemassa?
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table graphs " +
                        "(id integer primary key, graph_name text, graph_type integer, graph_obj_text text,);" +
                "create table graph_lines" +
                        "(id integer primary key, graph_line_tag integer graph_id integer);" +
                "create table graph_connections" +
                        "(id integer primary key, graph_line_id integer, graph_id integer)"
        );
    }

    //Luo databse uusix
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS graphs");
        db.execSQL("DROP TABLE IF EXISTS graph_lines");
        db.execSQL("DROP TABLE IF EXISTS graph_connections");
        onCreate(db);
    }
}
