package com.hillbilly.kidslauncher;

import android.content.UriMatcher;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by craighillbeck on 22/08/2014.
 */
public final class ParentsActivityContract {

    /**
     * Scheme
     */
    public static final String SCHEME = "content://";

    /**
     * Authority
     */
    public static final String AUTHORITY = "com.hillbilly.kidslauncher.provider";

    /**
     * Base URI for the Content
     */
    public static final Uri BASE_URI = (new Uri.Builder()).scheme(SCHEME).authority(AUTHORITY).build();

    /**
     * URIMatcher for the Content Provider
     */
    public static final UriMatcher MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    /**
     * The Contacts Table
     */
    public static final class ContactsTable {

        /**
         * The Table Name
         */
        public static final String TABLE_NAME = "contacts";

        /**
         * The URI for the Table
         */
        public static final Uri CONTENT_URI = BASE_URI.buildUpon().appendPath(TABLE_NAME).build();

        /**
         * Content Matcher ID for the Table
         */
        public static final int CONTENT = 1;

        /**
         * Content Matcher ID for a single row
         */
        public static final int CONTENT_ID = 2;

        static {
            MATCHER.addURI(AUTHORITY, TABLE_NAME, CONTENT);
            MATCHER.addURI(AUTHORITY, TABLE_NAME + "/#", CONTENT_ID);
        }

        /**
         * Create Table SQL Statement
         */
        public static final String CREATE_SQL = "CREATE TABLE " + TABLE_NAME + " (" + Columns._ID + " INTEGER PRIMARY KEY AUTONUMBER, " + Columns.NAME + " TEXT, " + Columns.HOME_NUMBER + " TEXT, " + Columns.MOBILE_NUMBER + " TEXT);";

        /**
         * The Columns contained in the Table
         */
        public static final class Columns implements BaseColumns {

            public static final String NAME = "name";

            public static final String HOME_NUMBER = "hnum";

            public static final String MOBILE_NUMBER = "mnum";

        }

    }

}
