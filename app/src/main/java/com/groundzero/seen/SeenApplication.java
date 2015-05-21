package com.groundzero.seen;


import android.app.Application;

import com.groundzero.seen.utils.ParseConstants;
import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParseUser;

public class SeenApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);
        
    }

    public static void updateParseInstallation(ParseUser user){
        ParseInstallation installation = ParseInstallation.getCurrentInstallation();
        installation.put(ParseConstants.KEY_USER_ID, user.getObjectId());
        installation.saveInBackground();
    }
}