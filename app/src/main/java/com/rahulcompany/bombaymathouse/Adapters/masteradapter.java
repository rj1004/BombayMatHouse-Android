package com.rahulcompany.bombaymathouse.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.rahulcompany.bombaymathouse.Databases.master.MasterData;
import com.rahulcompany.bombaymathouse.MasterActivity;
import com.rahulcompany.bombaymathouse.R;

import java.util.ArrayList;

public class masteradapter extends BaseAdapter {

    ArrayList<MasterData> data;

    public masteradapter(ArrayList<MasterData> data){
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
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.listmaster, parent, false);
        TextView code, name, uom;
        ImageView imagepath;
        code = v.findViewById(R.id.listmastercode);
        name = v.findViewById(R.id.listmastername);
        uom = v.findViewById(R.id.listmasteruom);
        imagepath = v.findViewById(R.id.listmasterimage);


        MasterData d = data.get(position);
        code.setText((String.valueOf(2122000000 + d.getCode())));
        name.setText(d.getItem());
        uom.setText(d.getUom());

        Glide.with(parent.getContext()).load(BitmapFactory.decodeFile(d.getImagepath())).into(imagepath);

        return v;
    }


}
