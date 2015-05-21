package com.groundzero.seen.utils;

/**
 * Created by keithmartin on 3/12/15.
 */
public class ParseConstants {
        // Class (database table) name
        public static final String CLASS_USER = "User";
        public static final String CLASS_MESSAGES = "Messages";
        public static final String CLASS_OPTICS = "Optics";
        public static final String CLASS_FRIENDS = "Friends";
        public static final String CLASS_BLOCKS = "Blocks";

        public static final String KEY_IMAGE_NAME = "imageName";
        public static final String KEY_IMAGE_FILE = "imageFile";

        // Field names
        public static final String KEY_ID = "objectId";
        public static final String KEY_USER_ID = "userId";
        public static final String KEY_EMAIL = "email";
        //used solely for the Users class (table) in Parse.com queries. unlike the others, it is not camelcase
        public static final String KEY_USERNAME = "username";
        public static final String KEY_FRIENDS_RELATION = "friendsRelation";

        public static final String KEY_RECIPIENT_IDS = "recipientIds";
        //users who have viewed the message
        public static final String KEY_VIEWER_IDS = "viewerIds";
        public static final String KEY_HIDER_IDS = "hiderIds" ;
        public static final String KEY_SENDER_ID = "senderId";
        public static final String KEY_SENDER_NAME = "senderName";
        public static final String KEY_FILE = "file";
        public static final String KEY_FILE_TYPE = "fileType";
        public static final String KEY_MEDIA_URI = "mediaUri";
        public static final String KEY_TEXT_DATA = "textData";

        public static final String KEY_CREATED_AT = "createdAt";
        public static final String KEY_UPDATED_AT = "updatedAt";

        //miscellaneous values
        public static final String TYPE_IMAGE = "image";
        public static final String TYPE_VIDEO = "video";
        public static final String TYPE_TEXT = "text";


        public static final String KEY_FILE_PATH = "filePath";
        public static final String KEY_FILE_BYTES = "fileBytes";
    }

