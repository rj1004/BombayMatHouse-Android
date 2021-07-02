package com.rahulcompany.bombaymathouse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.rahulcompany.bombaymathouse.Adapters.masteradapter;
import com.rahulcompany.bombaymathouse.Databases.master.MasterData;
import com.rahulcompany.bombaymathouse.Databases.master.MasterHelper;
import com.rahulcompany.bombaymathouse.PDFmaker.createpdf;
import com.rahulcompany.bombaymathouse.PDFmaker.masterpdftask;
import com.rahulcompany.bombaymathouse.Utils.FileUtils;

import java.io.File;

public class MasterActivity extends AppCompatActivity {

    private MasterHelper helper;
    private ListView masterlist;

     TextView imagename;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master);
        getSupportActionBar().setTitle("Material Master");

        helper = new MasterHelper(MasterActivity.this);
        masterlist = findViewById(R.id.masterlist);

        helper.getWritableDatabase();
        masterlist.setAdapter(new masteradapter(helper.getData()));

    }

    public void addmaterial() {
        Dialog d = new Dialog(MasterActivity.this);
        d.setContentView(R.layout.dialogaddmaster);
        WindowManager.LayoutParams lp = d.getWindow().getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        d.getWindow().setAttributes(lp);

        EditText name;
        Button save;
        Spinner uom;


        name = d.findViewById(R.id.name);
        uom = d.findViewById(R.id.uom);
        save = d.findViewById(R.id.save);
        imagename = d.findViewById(R.id.imagename);

        Button browse = d.findViewById(R.id.browse);
        browse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChooser();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String n = name.getText().toString();
                String u=uom.getSelectedItem().toString();
                String i = imagename.getText().toString();


                if (n.equalsIgnoreCase("") || i.equalsIgnoreCase("image name")){

                    Toast.makeText(MasterActivity.this, "Image field or Material Name Field can not be empty.", Toast.LENGTH_LONG).show();
                    return;
                }
                helper.insertdata(n, u, i);
                d.dismiss();
                masterlist.setAdapter(new masteradapter(helper.getData()));
            }
        });


        d.show();
    }

    void imageChooser(){

        // create an instance of the
        // intent of the type image
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        // pass the constant to compare it
        // with the returned requestCode
        startActivityForResult(Intent.createChooser(i, "Select Picture"), 100);
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            // compare the resultCode with the
            // SELECT_PICTURE constant
            if (requestCode == 100) {
                // Get the url of the image from data
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    // update the preview image in the layout

                    imagename.setText(FileUtils.getPath(MasterActivity.this, selectedImageUri));
                }
            }
        }
    }

//    public static String getRealPathFromURI_API19(Context context, Uri uri){
//        String filePath = "";
//        String wholeID = DocumentsContract.getDocumentId(uri);
//
//        // Split at colon, use second item in the array
//        String id = wholeID.split(":")[1];
//
//        String[] column = { MediaStore.Images.Media.DATA };
//
//        // where id is equal to
//        String sel = MediaStore.Images.Media._ID + "=?";
//
//        Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
//                column, sel, new String[]{ id }, null);
//
//        int columnIndex = cursor.getColumnIndex(column[0]);
//
//        if (cursor.moveToFirst()) {
//            filePath = cursor.getString(columnIndex);
//        }
//        cursor.close();
//        return filePath;
//    }

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
                addmaterial();
                break;
        }
        return true;
    }

    private void downloadpdf() {
        new masterpdftask(MasterActivity.this).execute();

    }
}