package com.groundzero.seen.utils;

import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.groundzero.seen.R;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;

/**
 * Created by Lwdthe1 on 1/24/2015.
 */
public abstract class SendMessage implements SaveCallback {
    public static void sendMessage(final Activity activity, final ParseObject message){

        message.saveInBackground(new SendMessage() {
            @Override
            public void done(ParseException e) {
                if(e==null) {
                    //message sent successfully
                    LToast.show(activity, activity.getString(R.string.message_sent_notice), "long");
                } else {
                    //message send failed
                    LToast.showL(activity, activity.getString(R.string.message_delivery_error_title));
                }
            }
        });
    }

    public static void enableSendButtons(Activity activity, Menu menu, final ImageButton sendTextButton){
        MenuItem sendMenuItem = menu.getItem(0);
        sendMenuItem.setVisible(true);
        sendTextButton.setVisibility(View.VISIBLE);
        sendTextButton.setClickable(true);
    }
}
