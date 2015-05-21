package com.groundzero.seen.utils;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;

import com.groundzero.seen.R;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

/**
 * Created by lwdthe1 on 1/12/2015.
 */
public class CameraConstants {
    public static final int TAKE_PHOTO_REQUEST = 0;
    public static final int TAKE_VIDEO_REQUEST = 1;
    public static final int PICK_PHOTO_REQUEST = 2;
    public static final int PICK_VIDEO_REQUEST = 3;

    public static final int MEDIA_TYPE_IMAGE = 4;
    public static final int MEDIA_TYPE_VIDEO = 5;

    public static final int FILE_SIZE_LIMIT = 1024*1024*10; //10MB


    //camera helper methods

    /**
     * this method is used by any activity that needs to launch the camera
     * to capture pictures or videos
     * @param x
     * @param mediaType
     * @param TAG
     * @return
     */
    public static Uri getOutputMediaFileUri(Context x, int mediaType, String TAG ) {
        //make sure extrenal storage is available
        if( isExternalStorageAvailable() ){
            //get the URL
            String appName = x.getString(R.string.app_name);

            //Get the exteranl storage directory to where the app's files are saved on the device
            File mediaStorageDir = new File(
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                    appName);
            //attempt to create our subdirectory in our app's folder on the device
            if( !mediaStorageDir.exists() ){
                //if failed to create the directory, exit
                if ( !mediaStorageDir.mkdirs()){
                    //Log.e(TAG, "Failed to create file directory");
                    return null;
                }
            }

            //create a unique file name and create the file
            File mediaFile;
            Date now = new Date();
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(now);
            Random rGen = new Random(10000);
            int randFileEnd = rGen.nextInt();

            String path = mediaStorageDir.getPath() + File.separator;
            if(mediaType == CameraConstants.MEDIA_TYPE_IMAGE){
                mediaFile = new File(path + "IMG_" + timestamp + randFileEnd + ".jpg");
            } else if( mediaType == CameraConstants.MEDIA_TYPE_VIDEO){
                mediaFile = new File(path + "VID_" + timestamp + randFileEnd + ".mp4");
            } else {
                return null;
            }

            //now create the uri to the new file  to be returned
            Uri uriFromFile = Uri.fromFile(mediaFile);
            //Log.d(TAG, "File: " + uriFromFile);
            //return the new file's uri
            return uriFromFile;
        } else {
            LToast.show(x.getApplicationContext(), "no external storage", "short");
            //if there is no external storage available, return null
            //the user cannot proceed with capturing a picture or video
            return null;
        }
    }

    /**
     * used by the getOutputMediaFileUri() method to
     * make sure there is available external storage on the user's device
     * @return
     */
    public static boolean isExternalStorageAvailable() {
        String state = Environment.getExternalStorageState();
        if(state.equals(Environment.MEDIA_MOUNTED)){
            return true;
        } else{
            return false;
        }
    }

}
