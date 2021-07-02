package com.rahulcompany.bombaymathouse.Databases.sell;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.rahulcompany.bombaymathouse.Databases.purchase.purchasedata;
import com.rahulcompany.bombaymathouse.Databases.stock.stockhelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;

public class sellhelper extends SQLiteOpenHelper {
    Context ctx;
    private static final String DATABASE_NAME="bombaymathouse.db";
    private static final int DATABASE_VERSION = 1;

    private static final String CREATE_TABLE= " create table sell( code integer , name text , date text , vendor text , num text , uom text , qty double , rate double , amt double , invoice integer)";

    private static  final String TABLE_NAME = "sell";
    public sellhelper(@Nullable Context context) {
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

    public void insert(long code, String name, String uom, double qty , double rate , double amt ,String vendor , String num , long invoice) {


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
        cv.put("num", num);
        cv.put("invoice",invoice);

        long result = db.insert(TABLE_NAME, null, cv);

        new stockhelper(ctx).decrqty(code, qty);

    }





    public ArrayList<selldata> getData(){
        onCreate(this.getWritableDatabase());
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("select * from sell", null);

        ArrayList<selldata> data = new ArrayList<>();
        while (c.moveToNext()) {
            selldata d = new selldata();
            d.setAmt(c.getDouble(c.getColumnIndex("amt")));
            d.setCode(c.getInt(c.getColumnIndex("code")));
            d.setDate(c.getString(c.getColumnIndex("date")));
            d.setItem(c.getString(c.getColumnIndex("name")));
            d.setQty(c.getDouble(c.getColumnIndex("qty")));
            d.setRate(c.getDouble(c.getColumnIndex("rate")));
            d.setUom(c.getString(c.getColumnIndex("uom")));
            d.setVendor(c.getString(c.getColumnIndex("vendor")));
            d.setNo(c.getString(c.getColumnIndex("num")));
            d.setInvoice(c.getInt(c.getColumnIndex("invoice")));

            data.add(d);
        }

        c.close();
        Collections.reverse(data);
        return data;
    }

    public double gettotalsell() {
        SQLiteDatabase db = this.getWritableDatabase();
        onCreate(db);

        double total_sell = 0;
        Cursor c = db.rawQuery("select amt from sell", null);
        while (c.moveToNext()) {
            total_sell = total_sell + c.getDouble(c.getColumnIndex("amt"));
        }
        return total_sell;
    }

    public double getsell(long code) {
        SQLiteDatabase db = this.getWritableDatabase();
        onCreate(db);

        double total_sell = 0;
        Cursor c = db.rawQuery("select amt from sell where code = ?", new String[]{String.valueOf(code)});
        while (c.moveToNext()) {
            total_sell = total_sell + c.getDouble(c.getColumnIndex("amt"));
        }
        return total_sell;
    }

    public long getinvoice() {
        SQLiteDatabase db = this.getWritableDatabase();
        onCreate(db);

        Cursor c = db.rawQuery("SELECT invoice FROM sell ORDER BY invoice DESC LIMIT 1", null);

        long lastinvoice = 10000;
        if (c.moveToLast()) {
            lastinvoice = c.getInt(c.getColumnIndex("invoice"));
        }

        return (lastinvoice+1);

    }


    public void updatedatabase(ArrayList<selldata> list) {
        for (int i = 0; i < list.size(); i++) {
            selldata a = list.get(i);
            insert(
                    a.getCode(),
                    a.getItem(),
                    a.getUom(),
                    a.getQty(),
                    a.getRate(),
                    a.getAmt(),
                    a.getVendor(),
                    a.getNo(),
                    a.getInvoice()
            );
        }
    }

    public HashMap<String, String> getcnamecno(long invoiceno) {
        SQLiteDatabase db = this.getWritableDatabase();
        onCreate(db);

        Cursor c = db.rawQuery("select vendor , num from sell where invoice = ?", new String[]{String.valueOf(invoiceno)});
        if (c.moveToFirst()) {
            HashMap<String, String> map = new HashMap<>();
            map.put("name", c.getString(c.getColumnIndex("vendor")));
            map.put("no", c.getString(c.getColumnIndex("num")));
            return map;
        } else {
            return new HashMap<>();
        }
    }
}
