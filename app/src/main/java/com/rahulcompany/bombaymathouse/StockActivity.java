package com.rahulcompany.bombaymathouse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.rahulcompany.bombaymathouse.Adapters.stockadapter;
import com.rahulcompany.bombaymathouse.Databases.purchase.purchasehelper;
import com.rahulcompany.bombaymathouse.Databases.sell.sellhelper;
import com.rahulcompany.bombaymathouse.Databases.stock.*;
import com.rahulcompany.bombaymathouse.PDFmaker.stockpdftask;




public class StockActivity extends AppCompatActivity {

    private ListView stocklist;
    private TextView profit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock);
        getSupportActionBar().setTitle("Stock");

        stocklist = findViewById(R.id.stocklist);
        stocklist.setAdapter(new stockadapter(new stockhelper(StockActivity.this).getData()));




        profit = findViewById(R.id.totalprofit);
        try {
            profit.setText("Total Profit  : " + String.valueOf(new sellhelper(StockActivity.this).gettotalsell() - new purchasehelper(StockActivity.this).gettotalpurchase()) + " Rs.");
        } catch (Exception e) {

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.download, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.download:
                downloadpdf();
                break;

        }
        return true;
    }

    private void downloadpdf() {
        new stockpdftask(StockActivity.this,profit.getText().toString()).execute();

    }

}