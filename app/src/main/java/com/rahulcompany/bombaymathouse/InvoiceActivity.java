package com.rahulcompany.bombaymathouse;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.rahulcompany.bombaymathouse.Adapters.invoiceadapter;
import com.rahulcompany.bombaymathouse.Databases.invoice.invoicehelper;

public class InvoiceActivity extends AppCompatActivity {

    private ListView invoicelist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice);
        getSupportActionBar().setTitle("Invoices");
        invoicelist = findViewById(R.id.invoicelist);

        invoicelist.setAdapter(new invoiceadapter(new invoicehelper(InvoiceActivity.this).getInvoices()));

    }
}