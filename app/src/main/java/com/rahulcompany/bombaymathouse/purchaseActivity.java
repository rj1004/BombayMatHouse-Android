package com.rahulcompany.bombaymathouse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.rahulcompany.bombaymathouse.Adapters.masteradapter;
import com.rahulcompany.bombaymathouse.Adapters.purchaseadapter;
import com.rahulcompany.bombaymathouse.Databases.master.MasterData;
import com.rahulcompany.bombaymathouse.Databases.master.MasterHelper;
import com.rahulcompany.bombaymathouse.Databases.purchase.purchasehelper;
import com.rahulcompany.bombaymathouse.PDFmaker.masterpdftask;
import com.rahulcompany.bombaymathouse.PDFmaker.purchasepdftask;

import java.util.ArrayList;

public class purchaseActivity extends AppCompatActivity {



    private ListView purchaselist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase);
        getSupportActionBar().setTitle("Purchase");

        purchaselist = findViewById(R.id.purchaselist);
        purchaselist.setAdapter(new purchaseadapter(new purchasehelper(purchaseActivity.this).getData()));

    }

    private void purchase() {
        Dialog d = new Dialog(purchaseActivity.this);
        d.setContentView(R.layout.dialogpurchase);
        WindowManager.LayoutParams lp = d.getWindow().getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        d.getWindow().setAttributes(lp);

        EditText vname,qty,rate,amt;
        Button purchase;
        Spinner codename;


        vname = d.findViewById(R.id.purchaseverndorname);
        qty = d.findViewById(R.id.purchaseqty);
        rate = d.findViewById(R.id.purchaserate);
        amt = d.findViewById(R.id.purchaseamt);
        codename = d.findViewById(R.id.codename);
        purchase = d.findViewById(R.id.purchasebtn);

        amt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    double q = Double.parseDouble(qty.getText().toString());
                    double r = Double.parseDouble(rate.getText().toString());
                    double a = q * r;
                    amt.setText(a + "");
                } catch (Exception e) {

                }
            }
        });


        setDataonspinner(codename);

        purchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String mixcode = codename.getSelectedItem().toString();
                    String[] da = mixcode.split("-");
                    long code = Long.parseLong(da[0].trim());
                    String name = da[1].trim();
                    String uom = da[2].trim();

                    String qa, ra, aa;
                    qa = qty.getText().toString();
                    ra = rate.getText().toString();
                    aa = amt.getText().toString();

                    if (qa.equalsIgnoreCase("") || ra.equalsIgnoreCase("") || aa.equalsIgnoreCase("")) {
                        Toast.makeText(purchaseActivity.this, "Some Field is Empty", Toast.LENGTH_LONG).show();
                        return;
                    }
                    String vn = vname.getText().toString();
                    double q = Double.parseDouble(qa);
                    double r = Double.parseDouble(ra);
                    double a = Double.parseDouble(aa);

                    new purchasehelper(purchaseActivity.this).insert(code, name, uom, q, r, a, vn);

                    d.dismiss();
                    purchaselist.setAdapter(new purchaseadapter(new purchasehelper(purchaseActivity.this).getData()));
                } catch (Exception e) {

                }
            }
        });



        d.show();
    }

    private void setDataonspinner(Spinner codename) {
        ArrayList<String> data = new ArrayList<>();

        ArrayList<MasterData> d = new MasterHelper(purchaseActivity.this).getData();

        for (int i = 0; i < d.size(); i++) {
            long code = d.get(i).getCode() + 2122000000;
            String s = code +"-"+ String.valueOf(d.get(i).getItem()) +"-"+ d.get(i).getUom();
            data.add(s);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(purchaseActivity.this, android.R.layout.simple_spinner_item,data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        codename.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.addownload, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.download:
                downloadpdf();
                break;
            case R.id.additem:
                purchase();
                break;
        }
        return true;
    }

    private void downloadpdf() {
        new purchasepdftask(purchaseActivity.this).execute();

    }
}