package com.rahulcompany.bombaymathouse.Databases.master;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.rahulcompany.bombaymathouse.Databases.stock.stockhelper;

import java.util.ArrayList;
import java.util.Collections;

public class MasterHelper extends SQLiteOpenHelper {
    Context ctx;
    private static final String DATABASE_NAME="bombaymathouse.db";
    private static final int DATABASE_VERSION = 1;

    private static final String CREATE_TABLE= " create table master( code integer  primary key autoincrement, name text , uom text , image text)";
    private static final String TABLE_NAME = "master";
    public MasterHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        ctx=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CREATE_TABLE);
        } catch (Exception e) {
            Log.d("exception", e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public boolean insertdata(String name,String uom ,String imagepath) {
        SQLiteDatabase db = this.getWritableDatabase();
        onCreate(db);
        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("uom", uom);
        cv.put("image", imagepath);

        long result = db.insert(TABLE_NAME, null, cv);
        if (result == -1) {
            return false;
        }

        new stockhelper(ctx).insertfrommaster(result + 2122000000, name, uom, 0);
        return true;
    }

    public ArrayList<MasterData> getData(){
        onCreate(this.getWritableDatabase());
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("select * from master", null);

        ArrayList<MasterData> data = new ArrayList<>();
        while (c.moveToNext()) {
            MasterData d = new MasterData();
            d.setCode(c.getInt(c.getColumnIndex("code")));
            d.setImagepath(c.getString(c.getColumnIndex("image")));
            d.setItem(c.getString(c.getColumnIndex("name")));
            d.setUom(c.getString(c.getColumnIndex("uom")));
            data.add(d);
        }

        c.close();
        Collections.reverse(data);
        return data;
    }




}
