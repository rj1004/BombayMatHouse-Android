package com.rahulcompany.bombaymathouse.Databases.stock;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.rahulcompany.bombaymathouse.Databases.master.MasterData;
import com.rahulcompany.bombaymathouse.Databases.purchase.purchasehelper;
import com.rahulcompany.bombaymathouse.Databases.sell.sellhelper;

import java.util.ArrayList;
import java.util.Collections;

public class stockhelper extends SQLiteOpenHelper {
    Context ctx;
    private static final String DATABASE_NAME="bombaymathouse.db";
    private static final int DATABASE_VERSION = 1;

    private static final String CREATE_TABLE= " create table stock( code integer  primary key , name text , uom text , qty double , profit double)";

    private static  final String TABLE_NAME = "stock";
    public stockhelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        ctx = context;
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

    public void insertfrommaster(long code, String name, String uom, long qty) {

        SQLiteDatabase db = this.getWritableDatabase();
        onCreate(db);
        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("uom", uom);
        cv.put("code",code);
        cv.put("qty", qty);
        cv.put("profit", 0.0);

        long result = db.insert(TABLE_NAME, null, cv);
    }

    public void increqty(long code, double qty) {
        SQLiteDatabase db = this.getWritableDatabase();
        onCreate(db);
        double initqty = 0;
        Cursor c = db.rawQuery("select qty from stock where code = ?", new String[]{String.valueOf(code)});
        while (c.moveToNext()) {
            initqty=c.getDouble(c.getColumnIndex("qty"));
        }
        initqty=initqty+qty;

        ContentValues cv = new ContentValues();
        cv.put("qty", initqty);
        cv.put("profit", new sellhelper(ctx).getsell(code) - new purchasehelper(ctx).getpurchase(code));
        db.update(TABLE_NAME, cv, " code = ?", new String[]{String.valueOf(code)});
    }

    public void decrqty(long code, double qty) {
        SQLiteDatabase db = this.getWritableDatabase();
        onCreate(db);
        double initqty = 0;
        Cursor c = db.rawQuery("select qty from stock where code = ?", new String[]{String.valueOf(code)});
        while (c.moveToNext()) {
            initqty=c.getDouble(c.getColumnIndex("qty"));
        }
        initqty=initqty-qty;

        ContentValues cv = new ContentValues();
        cv.put("qty", initqty);
        cv.put("profit", new sellhelper(ctx).getsell(code) - new purchasehelper(ctx).getpurchase(code));
        db.update(TABLE_NAME, cv, " code = ?", new String[]{String.valueOf(code)});
    }

    public ArrayList<stockdata> getData(){
        onCreate(this.getWritableDatabase());
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("select * from stock", null);

        ArrayList<stockdata> data = new ArrayList<>();
        while (c.moveToNext()) {
            stockdata d = new stockdata();
            d.setCode(c.getInt(c.getColumnIndex("code")));
            d.setQty(c.getDouble(c.getColumnIndex("qty")));
            d.setItem(c.getString(c.getColumnIndex("name")));
            d.setUom(c.getString(c.getColumnIndex("uom")));
            d.setProfit(c.getDouble(c.getColumnIndex("profit")));
            data.add(d);
        }

        c.close();
        Collections.reverse(data);
        return data;
    }


}
