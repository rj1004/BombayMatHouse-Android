package com.rahulcompany.bombaymathouse;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.UploadErrorException;
import com.rahulcompany.bombaymathouse.Databases.master.MasterHelper;
import com.rahulcompany.bombaymathouse.PDFmaker.createpdf;
import com.rahulcompany.bombaymathouse.Tasks.UpdateDropbox;
import com.rahulcompany.bombaymathouse.Utils.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;

public class HomeActivity extends AppCompatActivity {

    private final String SAMPLE_DB_NAME = "bombaymathouse.db";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    @Override
    protected void onStart() {
        super.onStart();

//        new createpdf().master(new MasterHelper(HomeActivity.this).getData());

        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(HomeActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 200);
        }

        new UpdateDropbox().execute();
    }



    public void master(View view) {
        startActivity(new Intent(HomeActivity.this, MasterActivity.class));
    }

    public void stock(View view) {
        startActivity(new Intent(HomeActivity.this, StockActivity.class));
    }

    public void purchase(View view) {
        startActivity(new Intent(HomeActivity.this, purchaseActivity.class));
    }

    public void sell(View view) {
        startActivity(new Intent(HomeActivity.this, sellActivity.class));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 200) {
            for (int i=0; i < grantResults.length; i++) {
                if (i == PackageManager.PERMISSION_GRANTED) {
                    continue;
                } else {
                    onStart();
                    break;
                }
            }
        }
    }

    public void invoice(View view) {
        startActivity(new Intent(HomeActivity.this, InvoiceActivity.class));
    }

    public void export(View view) {

        File sd = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Bombay Mat House/Exported Data/");
        if (!sd.exists()) {
            sd.mkdirs();
        }
        File data = Environment.getDataDirectory();
        String currentDBPath = "/data/"+ "com.rahulcompany.bombaymathouse" +"/databases/"+SAMPLE_DB_NAME;
        String backupDBPath = SAMPLE_DB_NAME;
        File currentDB = new File(data, currentDBPath);
        File backupDB = new File(sd, backupDBPath);
        copy(currentDB, backupDB);

    }

    public void importd(View view) {
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.setType("application/octet-stream");
        Intent chooser = Intent.createChooser(i, "Import Data from ");
        startActivityForResult(chooser, 101);
    }


    public void copy(File sourcefile, File destfile) {
        ProgressDialog pd = new ProgressDialog(HomeActivity.this);
        pd.setMessage("Exporting Data..");
        pd.setTitle("Please Wait");
        pd.setCancelable(false);
        pd.show();
        FileChannel source=null;
        FileChannel destination=null;
        try {
            source = new FileInputStream(sourcefile).getChannel();
            destination = new FileOutputStream(destfile).getChannel();
            destination.transferFrom(source, 0, source.size());
            source.close();
            destination.close();
        } catch(IOException e) {
            Toast.makeText(HomeActivity.this, "Failed", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

        Toast.makeText(HomeActivity.this, "Success", Toast.LENGTH_LONG).show();
        pd.dismiss();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101) {
            if (resultCode == RESULT_OK) {
                Uri selected = data.getData();
                File source = FileUtils.getFile(HomeActivity.this, selected);
                File datafile = Environment.getDataDirectory();
                String destpath = "/data/" + "com.rahulcompany.bombaymathouse" + "/databases/";
                File abc = new File(datafile, destpath);
                if (!abc.exists()) {
                    abc.mkdirs();
                }
                File dest = new File(abc, SAMPLE_DB_NAME);
                if (!dest.exists()) {
                    try {
                        dest.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                copy(source, dest);

            } else {
                Toast.makeText(HomeActivity.this, "You Canceled", Toast.LENGTH_LONG).show();
            }
        }
    }
}