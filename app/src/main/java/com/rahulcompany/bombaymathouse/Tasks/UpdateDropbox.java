package com.rahulcompany.bombaymathouse.Tasks;

import android.os.AsyncTask;
import android.os.Environment;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.UploadErrorException;
import com.dropbox.core.v2.files.WriteMode;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class UpdateDropbox extends AsyncTask<Void, Void, Void> {
    private final String SAMPLE_DB_NAME = "bombaymathouse.db";
    @Override
    protected Void doInBackground(Void... voids) {
        uploaddatadropbox();
        return null;
    }
    private void uploaddatadropbox() {
        final String ACCESS_TOKEN = "1QuOmVktMjkAAAAAAAAAAUoOMTdOPziRZJTZfYRKPJyRBA1NGD4QCU10i0hSIy1E";
        DbxRequestConfig config = new DbxRequestConfig("dropbox/java-tutorial", "en_US");
        DbxClientV2 client = new DbxClientV2(config, ACCESS_TOKEN);

        File data = Environment.getDataDirectory();
        String currentDBPath = "/data/"+ "com.rahulcompany.bombaymathouse" +"/databases/"+SAMPLE_DB_NAME;
        File currentDB = new File(data, currentDBPath);
        if (!currentDB.exists()) {
            return;
        }

        try (InputStream in = new FileInputStream(currentDB)) {
            FileMetadata metadata = client.files().uploadBuilder("/BombayMatHouse1/" + SAMPLE_DB_NAME)
                    .withMode(WriteMode.OVERWRITE)
                    .uploadAndFinish(in);
        } catch (Exception e) {

        }

    }
}
