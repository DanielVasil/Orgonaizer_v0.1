package com.daniel.trainer5;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DataBase extends SQLiteOpenHelper {

    private static final String db_name = "MyApp";
    private static final int db_ver = 1;
    private static final String db_table = "tasks"; //id
    private static final String db_column = "taskName"; //text
    private static final String db_date = "dateTime"; //date

    public DataBase(Context context) {
        super(context, db_name, null, db_ver);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = String.format("CREATE TABLE %s (ID INTEGER PRIMARY KEY AUTOINCREMENT," + " %s TEXT NOT NULL,"+" %s TEXT NOT NULL);", db_table, db_column, db_date);
        db.execSQL(query);



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = String.format("DELETE TABLE IF EXISTS %s)", db_table);
        db.execSQL(query);
        onCreate(db);
    }

    public void insertData(String task, String date){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues valuesT = new ContentValues();
        ContentValues valuesD = new ContentValues();
        valuesT.put(db_column, task);
        valuesD.put(db_date, date);
        db.insertWithOnConflict(db_table, null,valuesT,SQLiteDatabase.CONFLICT_REPLACE);
        db.insertWithOnConflict(db_table, null,valuesD,SQLiteDatabase.CONFLICT_REPLACE);
    }

    public void deleteData (String task){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(db_table, db_column + "= ?", new String[]{task} );
        db.close();
    }

    public ArrayList <String> getAllTasks(){
        ArrayList<String> all_tasks = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(db_table, new String[]{db_column},null,null,null,null,null);
        while(cursor.moveToNext()) {
            int index = cursor.getColumnIndex(db_column);
            all_tasks.add(cursor.getString(index));
        }
        cursor.close();
        db.close();
        return all_tasks;
    }

    public ArrayList<String> getAllDate() {
        ArrayList<String> all_date = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(db_table, new String[]{db_date}, null, null, null, null, null);
        while (cursor.moveToNext()) {
            int index = cursor.getColumnIndex(db_date);
            all_date.add(cursor.getString(index));
        }
        cursor.close();
        db.close();
        return all_date;
    }
}
