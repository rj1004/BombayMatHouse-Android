package com.rahulcompany.bombaymathouse.Adapters;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rahulcompany.bombaymathouse.Databases.purchase.purchasehelper;
import com.rahulcompany.bombaymathouse.Databases.sell.sellhelper;
import com.rahulcompany.bombaymathouse.Databases.stock.stockdata;
import com.rahulcompany.bombaymathouse.R;

import java.util.ArrayList;

public class stockadapter extends BaseAdapter {

    ArrayList<stockdata> data;

    public stockadapter(ArrayList<stockdata> data){
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
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.liststock, parent, false);
        TextView code, name, uom,profit;
        TextView qty;
        code = v.findViewById(R.id.liststockcode);

        uom = v.findViewById(R.id.liststockuom);
        qty = v.findViewById(R.id.liststockqty);
        profit = v.findViewById(R.id.liststockprofit);


        stockdata d = data.get(position);
        code.setText(d.getCode()+"\n"+d.getItem());

        uom.setText(d.getUom());
        qty.setText(d.getQty()+"");
        try {
            profit.setText(String.valueOf(d.getProfit()));
            //profit.setText(String.valueOf(new sellhelper(parent.getContext()).getsell(data.get(position).getCode()) - new purchasehelper(parent.getContext()).getpurchase(data.get(position).getCode())));
        } catch (Exception e) {

        }
        return v;
    }


}
