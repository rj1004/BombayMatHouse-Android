package com.rahulcompany.bombaymathouse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.rahulcompany.bombaymathouse.Adapters.selladapter;
import com.rahulcompany.bombaymathouse.Adapters.selladapter;
import com.rahulcompany.bombaymathouse.Databases.master.MasterData;
import com.rahulcompany.bombaymathouse.Databases.master.MasterHelper;
import com.rahulcompany.bombaymathouse.Databases.sell.selldata;
import com.rahulcompany.bombaymathouse.Databases.sell.sellhelper;
import com.rahulcompany.bombaymathouse.Databases.sell.sellhelper;
import com.rahulcompany.bombaymathouse.PDFmaker.createbill;
import com.rahulcompany.bombaymathouse.PDFmaker.masterpdftask;
import com.rahulcompany.bombaymathouse.PDFmaker.sellpdftask;

import java.util.ArrayList;

public class sellActivity extends AppCompatActivity {

    ListView selllist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell);
        getSupportActionBar().setTitle("Sell");

        selllist = findViewById(R.id.selllist);
        selllist.setAdapter(new selladapter(new sellhelper(sellActivity.this).getData()));
    }

    public void sell() {

        startActivity(new Intent(sellActivity.this,BuyActivity.class));
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
                sell();
                break;
        }
        return true;
    }

    private void downloadpdf() {
        new sellpdftask(sellActivity.this).execute();

    }

    @Override
    protected void onStart() {
        super.onStart();
        selllist.setAdapter(new selladapter(new sellhelper(sellActivity.this).getData()));
    }
}