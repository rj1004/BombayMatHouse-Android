package com.rahulcompany.bombaymathouse.PDFmaker;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.rahulcompany.bombaymathouse.Databases.purchase.purchasehelper;
import com.rahulcompany.bombaymathouse.Databases.stock.stockhelper;

public class purchasepdftask extends AsyncTask<Void,Void,Void> {
    ProgressDialog pd;
    Context ctx;

    public purchasepdftask(Context ctx) {
        this.ctx = ctx;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pd = new ProgressDialog(ctx);
        pd.setCancelable(false);
        pd.setMessage("Gathering data to make pdf");
        pd.setTitle("Please Wait");
        pd.show();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        pd.dismiss();
        Toast.makeText(ctx, "PDF Saved at Bombay Mat House Folder", Toast.LENGTH_LONG).show();
        super.onPostExecute(aVoid);
    }

    @Override
    protected Void doInBackground(Void... voids) {
        new createpdf().purchase(new purchasehelper(ctx).getData());
        return null;
    }
}
