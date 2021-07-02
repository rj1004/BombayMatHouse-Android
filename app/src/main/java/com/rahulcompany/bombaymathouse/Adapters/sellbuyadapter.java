package com.rahulcompany.bombaymathouse.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.rahulcompany.bombaymathouse.BuyActivity;
import com.rahulcompany.bombaymathouse.Databases.sell.selldata;
import com.rahulcompany.bombaymathouse.HomeActivity;
import com.rahulcompany.bombaymathouse.MainActivity;
import com.rahulcompany.bombaymathouse.R;
import com.rahulcompany.bombaymathouse.interfaces.ChangeList;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class sellbuyadapter extends BaseAdapter {

    ArrayList<selldata> list;
    ChangeList changeList;
    public sellbuyadapter(ArrayList<selldata> list, ChangeList changeList) {
        this.list = list;
        this.changeList = changeList;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        selldata d = list.get(position);
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.listbuy, parent, false);
        TextView name,q,r,a;
        name = v.findViewById(R.id.namecodeuom);
        q = v.findViewById(R.id.qty);
        r = v.findViewById(R.id.rate);
        a = v.findViewById(R.id.amt);

        name.setText(d.getCode() + "-" + d.getItem() + "-" + d.getUom());
        q.setText("QTY : "+d.getQty());
        r.setText("Rate : "+d.getRate());
        a.setText("Amt : " +d.getAmt());


        Button delete = v.findViewById(R.id.deleteitem);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 1/7/21 delete item

                changeList.remove(position);
            }
        });
        
        return v;
    }
}
