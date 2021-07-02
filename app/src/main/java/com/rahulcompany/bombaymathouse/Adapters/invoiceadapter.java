package com.rahulcompany.bombaymathouse.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.rahulcompany.bombaymathouse.Databases.invoice.invoicedata;
import com.rahulcompany.bombaymathouse.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

import javax.xml.xpath.XPath;

public class invoiceadapter extends BaseAdapter {

    ArrayList<invoicedata> data;
    Context ctx;

    public invoiceadapter(ArrayList<invoicedata> data) {
        this.data = data;

    }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ctx=parent.getContext();
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.listinvoice, parent, false);

        TextView invoice,c;
        invoice = v.findViewById(R.id.listinvoicecode);
        c = v.findViewById(R.id.listinvoicecnameno);
        ImageButton share = v.findViewById(R.id.listinvoiceshare);
        Button sendother = v.findViewById(R.id.listinvoicesend);

        invoicedata d = data.get(position);

        invoice.setText(d.getNumber() + "");
        c.setText(d.getCname() + "\n" + d.getCno());
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWhatsApp(d.getFilepath(),d.getCno());
            }
        });

        sendother.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendother(d.getFilepath());
            }
        });
        return v;
    }
    private void openWhatsApp(String path,String no) {
        String smsNumber = "91"+no; // E164 format without '+' sign
        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.setType("application/pdf");
        sendIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(path));
        sendIntent.putExtra("jid", smsNumber + "@s.whatsapp.net"); //phone number without "+" prefix
        sendIntent.setPackage("com.whatsapp");
        if (sendIntent.resolveActivity(ctx.getPackageManager()) == null) {
            Toast.makeText(ctx, "Error/n", Toast.LENGTH_SHORT).show();
            return;
        }
        ctx.startActivity(sendIntent);
    }

    private void sendother(String path) {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("application/pdf");
        i.putExtra(Intent.EXTRA_STREAM, Uri.parse(path));
        if (i.resolveActivity(ctx.getPackageManager()) == null) {
            Toast.makeText(ctx, "Error/n", Toast.LENGTH_SHORT).show();
            return;
        }
        ctx.startActivity(Intent.createChooser(i, "Send Invoice to"));
    }
}
