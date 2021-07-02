package com.rahulcompany.bombaymathouse.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.rahulcompany.bombaymathouse.Databases.purchase.purchasedata;
import com.rahulcompany.bombaymathouse.R;

import java.util.ArrayList;

public class purchaseadapter extends BaseAdapter {

    ArrayList<purchasedata> purchasedata;

    public purchaseadapter(ArrayList<purchasedata> purchasedata) {
        this.purchasedata = purchasedata;
    }

    @Override
    public int getCount() {
        return purchasedata.size();
    }

    @Override
    public Object getItem(int position) {
        return purchasedata.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.listpurchase, parent, false);
        TextView code,name,date,vendor,uom,qty,rate,amt;
        code = v.findViewById(R.id.listpurchasecode);

        date = v.findViewById(R.id.listpurchasedate);
        vendor = v.findViewById(R.id.listpurchaseven);
        uom = v.findViewById(R.id.listpurchaseuom);
        qty = v.findViewById(R.id.listpurchaseqty);
        rate = v.findViewById(R.id.listpurchaserate);
        amt = v.findViewById(R.id.listpurchaseamt);

        code.setText(purchasedata.get(position).getCode() + "\n"+purchasedata.get(position).getItem());
        date.setText(purchasedata.get(position).getDate() + "");
        vendor.setText(purchasedata.get(position).getVendor() + "");
        uom.setText(purchasedata.get(position).getUom() + "");
        qty.setText(purchasedata.get(position).getQty() + "");
        rate.setText(purchasedata.get(position).getRate() + "");
        amt.setText(purchasedata.get(position).getAmt() + "");

        return v;
    }
}
