package com.groundzero.seen.ui;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.GridView;

import com.google.android.glass.touchpad.GestureDetector;
import com.groundzero.seen.R;
import com.groundzero.seen.utils.FileHelper;
import com.groundzero.seen.utils.LToast;
import com.groundzero.seen.utils.ParseConstants;
import com.groundzero.seen.utils.SendMessage;
import com.parse.ParseFile;
import com.parse.ParseObject;

import java.io.File;


public class UploadActivity extends Activity {

    private final Handler mHandler = new Handler();
    protected Intent mOriginIntent;
    protected Uri mMediaUri;
    protected String mFileType;
    protected GridView mGridView;
    protected byte[] mFileBytes;
    private File mPictureFile;



    /**
     * Gesture detector used to present the options menu.
     */
    private GestureDetector mGestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        //get the sent data\
        mMediaUri = getIntent().getData();
        //get extras
        LToast.show(this, mMediaUri.toString(), "long");

        mFileType = getIntent().getExtras().getString(ParseConstants.KEY_FILE_TYPE);
        mFileBytes = FileHelper.getByteArrayFromFile(this, mMediaUri);
        LToast.showL(this, mFileBytes +" x");
        DecreaseFileSize decreaseFileSize = new DecreaseFileSize();
        decreaseFileSize.execute();


        sendTheMessage();
    }

    private void exitApp() {
        finish();
    }


    private void sendTheMessage() {
        //let the user know the message is being sent
        LToast.showL(this, "Sending message");
        //send the message
        ParseObject message = createMessage();
        if (message == null) {
            LToast.showL(this, "Failed to Send Message");
        } else {
            //get the text data from the edit text field
            send(message);
        }
    }

    protected ParseObject createMessage() {
        //create a new parse object of class Messages
        ParseObject message = new ParseObject(ParseConstants.CLASS_OPTICS);

        //add the date key/value pairs
        //filter the recipients to make sure each recipient has the current user as a friend
        message.put(ParseConstants.KEY_FILE_TYPE, mFileType);
        message.put(ParseConstants.KEY_MEDIA_URI, mMediaUri.toString());

        if (mFileBytes == null) {
            Log.d("FILEBYTES", "null");
            return message;
        } else {
            String fileName = FileHelper.getFileName(this, mMediaUri, mFileType);

            ParseFile file = new ParseFile(fileName,  mFileBytes);
            Log.d("File", file.toString() + " File Name: " + file.getName() +  file.getUrl());
            message.put(ParseConstants.KEY_FILE, file);

            return message;
        }
    }


    private void send(final ParseObject message) {
        //save the message in background so the user isn't held on this screen while it saves
        SendMessage.sendMessage(this, message);
    }


    private class DecreaseFileSize extends AsyncTask<Void, Void, byte[]> {

        @Override
        protected byte[] doInBackground(Void... params) {
            byte[] fileBytes = null;
            if (mFileBytes != null) {
                if (mFileType.equals(ParseConstants.TYPE_IMAGE)) {
                    //if the file is an image, reduce the image's size for upload
                    fileBytes = FileHelper.reduceImageForUpload(mFileBytes);
                }
                return fileBytes;
            } else {
                return null;
            }
        }

        @Override
        protected void onPostExecute(byte[] result) {
            super.onPostExecute(result);
            if (result != null) {
                mFileBytes = result;
            }
        }
    }
}
