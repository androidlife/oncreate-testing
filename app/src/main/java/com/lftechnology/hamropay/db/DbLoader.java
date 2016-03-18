package com.lftechnology.hamropay.db;

import android.os.AsyncTask;

import com.lftechnology.hamropay.HamroPayApplication;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 */
public class DbLoader extends AsyncTask<String, Void, Boolean> {


    @Override
    protected Boolean doInBackground(String... params) {
        try {
            String assetFileLocation = params[0];
            String dbFileLocation = params[1];
            InputStream is = HamroPayApplication.getContext().getAssets().open(assetFileLocation);
            FileOutputStream os = new FileOutputStream(new File(dbFileLocation));
            byte[] buffer = new byte[1024];
            while (is.read(buffer) > 0)
                os.write(buffer);
            os.flush();
            os.close();
            is.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        onDbLoaded.dbLoadedFromAssets(result);
    }

    public interface OnDbLoaded {
        void dbLoadedFromAssets(boolean success);
    }

    private OnDbLoaded onDbLoaded;

    public DbLoader(OnDbLoaded onDbLoaded) {
        this.onDbLoaded = onDbLoaded;
    }


}
