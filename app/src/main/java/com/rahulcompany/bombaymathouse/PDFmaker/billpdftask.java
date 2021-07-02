package com.rahulcompany.bombaymathouse.PDFmaker;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.rahulcompany.bombaymathouse.Databases.purchase.purchasehelper;
import com.rahulcompany.bombaymathouse.Databases.sell.selldata;

import java.util.ArrayList;

public class billpdftask extends AsyncTask<Void, Void, Void> {
    ProgressDialog pd;
    Context ctx;
    Activity app;
    ArrayList<selldata> list;

    public billpdftask(Context ctx, ArrayList<selldata> list,Activity app) {
        this.ctx = ctx;
        this.list = list;
        this.app = app;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pd = new ProgressDialog(ctx);
        pd.setCancelable(false);
        pd.setMessage("Generating Invoice..");
        pd.setTitle("Please Wait");
        pd.show();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        pd.dismiss();
        Toast.makeText(ctx, "PDF Saved at Bombay Mat House Folder", Toast.LENGTH_LONG).show();
        super.onPostExecute(aVoid);

        app.finish();

    }

    @Override
    protected Void doInBackground(Void... voids) {
        new createbill(ctx, list).makeinvoice();
        return null;
    }
}
