package com.rahulcompany.bombaymathouse.Databases.invoice;

import android.content.Context;
import android.os.Environment;

import com.rahulcompany.bombaymathouse.Databases.sell.sellhelper;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class invoicehelper {

    Context ctx;

    public invoicehelper(Context ctx) {
        this.ctx = ctx;
    }

    public ArrayList<invoicedata> getInvoices() {
        ArrayList<invoicedata> data = new ArrayList<>();

        File f = new File(Environment.getExternalStorageDirectory() + "/Bombay Mat House/Invoices");
        if (!f.exists()) {
            return data;
        }

        File[] is = f.listFiles();
        for (int i = 0; i < is.length; i++) {
            if (!is[i].isFile()) {
                continue;
            }

            String name = is[i].getName();
//            String no = name.split(".")[0];
            String no = name.substring(0,name.indexOf("."));
            Long invoice_no = Long.parseLong(no);
            invoicedata id = new invoicedata();
            id.setNumber(invoice_no);
            id.setFilepath(is[i].getAbsolutePath());
            data.add(setnameno(id));
        }

        Collections.reverse(data);
        return data;
    }

    private invoicedata setnameno(invoicedata id) {

        HashMap<String, String> map = new sellhelper(ctx).getcnamecno(id.getNumber());
        if (map != null) {
            id.setCname(map.get("name"));
            id.setCno(map.get("no"));
        } else {
            id.setCno("");
            id.setCname("");
        }
        return id;
    }
}
