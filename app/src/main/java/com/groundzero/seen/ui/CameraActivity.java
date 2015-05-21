package com.groundzero.seen.ui;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.FileObserver;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MotionEvent;

import com.google.android.glass.touchpad.Gesture;
import com.google.android.glass.touchpad.GestureDetector;


import com.groundzero.seen.utils.ParseConstants;
import com.parse.ParseFile;
import com.parse.ParseObject;

import com.google.android.glass.content.Intents;
import com.groundzero.seen.R;
import com.groundzero.seen.utils.CameraUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
/* written by Andres Ramos.
 * all the essential code for the application is here, a lot of extra classes in the project because we originally planned
 * to take a different approach.
 */

public class CameraActivity extends Activity {

    private GestureDetector mGestureDetector;
    String thumbnailPath;
    private boolean notUploaded = true;

    private final GestureDetector.BaseListener mBaseListener = new GestureDetector.BaseListener() {
        @Override

        public boolean onGesture(Gesture gesture) {
            switch (gesture) {
                case SWIPE_LEFT:
                    takePhoto();
                    return true;
                case TAP:
                    takePhoto();
                    return true;
                case SWIPE_RIGHT:
                    takePhoto();
                    return true;
                default:
                    return false;
            }

        }
    };

    @Override
    protected void onCreate(Bundle bundle) {

        super.onCreate(bundle);
        mGestureDetector = new GestureDetector(this).setBaseListener(mBaseListener);
        setContentView(R.layout.activity_camera);
        takePhoto();

    }

    public boolean onGenericMotionEvent(MotionEvent event) {
        return mGestureDetector.onMotionEvent(event);
    }

    private void takePhoto() {
        //create an intent to launch the phone's camera to take a picture
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Log.d("TakePhoto()", "method ");

        startActivityForResult(intent, CameraUtils.TAKE_PHOTO_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CameraUtils.TAKE_PHOTO_REQUEST && resultCode == RESULT_OK) {
            thumbnailPath = data.getStringExtra(Intents.EXTRA_THUMBNAIL_FILE_PATH);


            String picturePath  = data.getStringExtra(Intents.EXTRA_PICTURE_FILE_PATH);
            Log.d("onActivityResult ()", picturePath);

            processPicture(picturePath);
            finish();

    }
        super.onActivityResult(requestCode, resultCode, data);
    }


    private void processPicture(final String picturePath){
        final File pictureFile = new File(picturePath);
        if(notUploaded){
        if(pictureFile.exists()){

            Log.d("processPicture", "the file exists! -- processing image");
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            Bitmap bitmap = BitmapFactory.decodeFile(pictureFile.getAbsolutePath(),bmOptions);

            // Convert it to byte
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            // Compress image to lower quality scale 1 - 100
            bitmap.compress(Bitmap.CompressFormat.JPEG,85,stream);
            byte[] image =stream.toByteArray();

            ParseFile pFile = new ParseFile("firstUpload",image);
            pFile.saveInBackground();

            // Create a New Class called "ImageUpload" in Parse
            String name;
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            name = "SeenImgUpload : " + dateFormat.format(date);

            ParseObject imgupload = new ParseObject("ImageUpload");
            // Create a column named "ImageName" and set the string
            imgupload.put(ParseConstants.KEY_IMAGE_NAME, name);

            // Create a column named "ImageFile" and insert the image
            imgupload.put(ParseConstants.KEY_IMAGE_FILE, pFile);

            // Create the class and the columns
            imgupload.saveInBackground();

            notUploaded = false;
            Log.d("processPicture", "Is now uploaded" + notUploaded);

        }else  {

            Log.d("processPicture", "the file does not exist! attempting to create");
            final File parentDirectory = pictureFile.getParentFile();
            FileObserver observer = new FileObserver(parentDirectory.getPath() , FileObserver.CLOSE_WRITE | FileObserver.MOVED_TO) {
                private boolean isFileWritten;
                @Override
                public void onEvent(int event, String path) {
                    if(!isFileWritten){
                        stopWatching();

                        // Now that the file is ready, recursively call
                        // processPictureWhenReady again (on the UI thread).
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                processPicture(picturePath);
                            }
                        });
                    }
                }
            };
            observer.startWatching();
        }
      }

    }

}
