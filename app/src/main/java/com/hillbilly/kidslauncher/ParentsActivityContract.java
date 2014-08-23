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
     * Base MIME Type for a single row
     */
    public static final String SINGLE_MIME = "vnd.android.cursor.item";

    /**
     * Base MIME Type for Multiple Rows
     */
    public static final String MULTI_MIME = "vnd.android.cursor.dir";

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
        /**
         * Create Table SQL Statement
         */
        public static final String CREATE_SQL = "CREATE TABLE " + TABLE_NAME + " (" + Columns._ID + " INTEGER PRIMARY KEY AUTONUMBER, " + Columns.NAME + " TEXT, " + Columns.HOME_NUMBER + " TEXT, " + Columns.MOBILE_NUMBER + " TEXT);";
        /**
         * MIME Type for a single item
         */
        public static final String SINGLE_MIME = ParentsActivityContract.SINGLE_MIME + "/person";

        /**
         * The Columns contained in the Table
         */
        public static final class Columns implements BaseColumns {

            public static final String NAME = "name";

            public static final String HOME_NUMBER = "hnum";

            public static final String MOBILE_NUMBER = "mnum";

        }

        /**
         * MIME Type for multiple items
         */
        public static final String MULTI_MIME = ParentsActivityContract.MULTI_MIME + "/people";

        static {
            MATCHER.addURI(AUTHORITY, TABLE_NAME, CONTENT);
            MATCHER.addURI(AUTHORITY, TABLE_NAME + "/#", CONTENT_ID);
        }


    }

}
