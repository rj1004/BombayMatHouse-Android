package com.rahulcompany.bombaymathouse.Databases.purchase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.rahulcompany.bombaymathouse.Databases.stock.stockdata;
import com.rahulcompany.bombaymathouse.Databases.stock.stockhelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class purchasehelper extends SQLiteOpenHelper {
    Context ctx;
    private static final String DATABASE_NAME="bombaymathouse.db";
    private static final int DATABASE_VERSION = 1;

    private static final String CREATE_TABLE= " create table purchase( code integer , name text , date text , vendor text , uom text , qty double , rate double , amt double)";

    private static  final String TABLE_NAME = "purchase";
    public purchasehelper(@Nullable Context context) {
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

    public void insert(long code, String name, String uom, double qty , double rate , double amt ,String vendor ) {


        String pattern = "dd-MM-yyyy";
        DateFormat format = new SimpleDateFormat(pattern);
        String date = format.format(new Date());;


        SQLiteDatabase db = this.getWritableDatabase();
        onCreate(db);
        ContentValues cv = new ContentValues();
        cv.put("code", code);
        cv.put("name", name);
        cv.put("date",date);
        cv.put("vendor", vendor);
        cv.put("uom",uom);
        cv.put("qty",qty);
        cv.put("rate",rate);
        cv.put("amt", amt);

        long result = db.insert(TABLE_NAME, null, cv);

        new stockhelper(ctx).increqty(code, qty);
    }





    public ArrayList<purchasedata> getData(){
        onCreate(this.getWritableDatabase());
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("select * from purchase", null);

        ArrayList<purchasedata> data = new ArrayList<>();
        while (c.moveToNext()) {
            purchasedata d = new purchasedata();
            d.setAmt(c.getDouble(c.getColumnIndex("amt")));
            d.setCode(c.getInt(c.getColumnIndex("code")));
            d.setDate(c.getString(c.getColumnIndex("date")));
            d.setItem(c.getString(c.getColumnIndex("name")));
            d.setQty(c.getDouble(c.getColumnIndex("qty")));
            d.setRate(c.getDouble(c.getColumnIndex("rate")));
            d.setUom(c.getString(c.getColumnIndex("uom")));
            d.setVendor(c.getString(c.getColumnIndex("vendor")));

            data.add(d);
        }

        c.close();
        Collections.reverse(data);
        return data;
    }

    public double gettotalpurchase() {
        SQLiteDatabase db = this.getWritableDatabase();
        onCreate(db);

        double total_purchase = 0;
        Cursor c = db.rawQuery("select amt from purchase", null);
        while (c.moveToNext()) {
            total_purchase = total_purchase + c.getDouble(c.getColumnIndex("amt"));
        }
        return total_purchase;
    }
    public double getpurchase(long code) {
        SQLiteDatabase db = this.getWritableDatabase();
        onCreate(db);

        double total_purchase = 0;
        Cursor c = db.rawQuery("select amt from purchase where code = ?", new String[]{String.valueOf(code)});
        while (c.moveToNext()) {
            total_purchase = total_purchase + c.getDouble(c.getColumnIndex("amt"));
        }
        return total_purchase;
    }

}
