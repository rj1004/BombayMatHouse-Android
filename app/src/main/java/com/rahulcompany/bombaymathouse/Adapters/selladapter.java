package com.rahulcompany.bombaymathouse.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.rahulcompany.bombaymathouse.Databases.sell.selldata;
import com.rahulcompany.bombaymathouse.R;

import java.util.ArrayList;

public class selladapter extends BaseAdapter {

    ArrayList<selldata> selldata;

    public selladapter(ArrayList<selldata> selldata) {
        this.selldata = selldata;
    }

    @Override
    public int getCount() {
        return selldata.size();
    }

    @Override
    public Object getItem(int position) {
        return selldata.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.listsell, parent, false);
        TextView code,name,date,vendor,uom,qty,rate,amt,no;
        code = v.findViewById(R.id.listsellcode);

        date = v.findViewById(R.id.listselldate);
        vendor = v.findViewById(R.id.listsellcname);
        uom = v.findViewById(R.id.listselluom);
        qty = v.findViewById(R.id.listsellqty);
        rate = v.findViewById(R.id.listsellrate);
        amt = v.findViewById(R.id.listsellamt);

        code.setText(selldata.get(position).getCode() + "\n"+selldata.get(position).getItem());
        date.setText(selldata.get(position).getDate() + "\n" + selldata.get(position).getInvoice());
        vendor.setText(selldata.get(position).getVendor() + "\n" + selldata.get(position).getNo());
        uom.setText(selldata.get(position).getUom() + "");
        qty.setText(selldata.get(position).getQty() + "");
        rate.setText(selldata.get(position).getRate() + "");
        amt.setText(selldata.get(position).getAmt() + "");
        return v;
    }
}
