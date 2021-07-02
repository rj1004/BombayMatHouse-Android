package com.rahulcompany.bombaymathouse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.rahulcompany.bombaymathouse.Adapters.sellbuyadapter;
import com.rahulcompany.bombaymathouse.Databases.master.MasterData;
import com.rahulcompany.bombaymathouse.Databases.master.MasterHelper;
import com.rahulcompany.bombaymathouse.Databases.sell.selldata;
import com.rahulcompany.bombaymathouse.Databases.sell.sellhelper;
import com.rahulcompany.bombaymathouse.PDFmaker.billpdftask;
import com.rahulcompany.bombaymathouse.interfaces.ChangeList;

import java.util.ArrayList;

public class BuyActivity extends AppCompatActivity implements ChangeList {

    private EditText name,no,q,r,a;
    private ListView lv;
    private long invoiceno;
    ArrayList<selldata> list;

    private Spinner sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);


        name= findViewById(R.id.sellcname);
        no = findViewById(R.id.sellcno);
        q = findViewById(R.id.sellqty);
        r = findViewById(R.id.sellrate);
        a = findViewById(R.id.sellamt);
        lv = findViewById(R.id.listbuy);


        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String qty = q.getText().toString();
                String ra = r.getText().toString();
                if (qty.equalsIgnoreCase("") || ra.equalsIgnoreCase("")) {
                    return;
                }

                double qty1 = Double.parseDouble(qty);
                double ra1 = Double.parseDouble(ra);

                a.setText((qty1 * ra1) + "");

            }
        });


        invoiceno = new sellhelper(BuyActivity.this).getinvoice();

        list = new ArrayList<>();

        sp = findViewById(R.id.spin);
        setDataonspinner(sp);

    }

    public void additem(View view) {
        String n, number;
        n = name.getText().toString();
        number = no.getText().toString();
        if (n.equalsIgnoreCase("") || number.equalsIgnoreCase("")) {
            Toast.makeText(BuyActivity.this, "Name or Number field is empty",Toast.LENGTH_LONG).show();
            return;
        }

        String qty = q.getText().toString();
        String ra = r.getText().toString();
        String am = a.getText().toString();

        if (qty.equalsIgnoreCase("") || ra.equalsIgnoreCase("") || am.equalsIgnoreCase("")) {
            Toast.makeText(BuyActivity.this, "qty or amt or rate field is empty",Toast.LENGTH_LONG).show();
            return;
        }



        double qty1 = Double.parseDouble(qty);
        double ra1 = Double.parseDouble(ra);
        double am1 = Double.parseDouble(am);




        selldata d = new selldata();
        d.setInvoice(invoiceno);
        d.setNo(number);
        d.setVendor(n);
        d.setQty(qty1);
        d.setAmt(am1);
        d.setRate(ra1);

        String code, item, uom;
        String[] abc = sp.getSelectedItem().toString().split("-");
        code = abc[0];
        item = abc[1];
        uom = abc[2];

        d.setCode(Long.parseLong(code));
        d.setItem(item);
        d.setUom(uom);

        list.add(d);
        lv.setAdapter(new sellbuyadapter(list,this));
        q.setText("");
        r.setText("");
        a.setText("");

    }
    private void setDataonspinner(Spinner codename) {
        ArrayList<String> data = new ArrayList<>();

        ArrayList<MasterData> d = new MasterHelper(BuyActivity.this).getData();

        for (int i = 0; i < d.size(); i++) {
            long code = d.get(i).getCode() + 2122000000;
            String s = code +"-"+ String.valueOf(d.get(i).getItem()) +"-"+ d.get(i).getUom();
            data.add(s);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(BuyActivity.this, android.R.layout.simple_spinner_item,data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        codename.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.makeinvoice, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        makesellorder();
        return true;
    }

    private void makesellorder() {
        if (list.size() != 0) {
            new billpdftask(BuyActivity.this, list,BuyActivity.this).execute();
            new sellhelper(BuyActivity.this).updatedatabase(list);
        }
        else {
            Toast.makeText(BuyActivity.this, "No Items Added", Toast.LENGTH_LONG).show();
        }


    }

    @Override
    public void remove(int position) {
        try {
            list.remove(position);
            lv.setAdapter(new sellbuyadapter(list,this));
        } catch (Exception e) {
            Log.e("exception", e.getMessage());
        }
    }
}