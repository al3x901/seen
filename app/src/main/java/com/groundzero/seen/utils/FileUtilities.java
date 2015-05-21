package com.groundzero.seen.utils;

import android.app.Activity;
import android.content.Context;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Lwdthe1 on 1/26/2015.
 */
public class FileUtilities {
    public static void saveSettings(Activity activity, Context x, ArrayList<HashMap<String,String>> settings){
        File fileDirectory = x.getFilesDir();
        File settingsFileToWrite = new File(fileDirectory, "settings");

        InputStream in = new InputStream() {
            @Override
            public int read() throws IOException {
                return 0;
            }
        };
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(settingsFileToWrite);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally{
            if( out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void saveSettingsFile(OutputStream out, String settings){

    }
}
