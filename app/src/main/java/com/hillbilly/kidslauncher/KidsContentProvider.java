package com.hillbilly.kidslauncher;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

public class KidsContentProvider extends ContentProvider {

    private static final int VER = 1;
    private final static String DBNAME = "KidsLaunch";
    /**
     * A pointer to the current Database Helper Object
     */
    private OpenHelper helper;

    public KidsContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int result = -1;
        String tableName = "";

        switch (ParentsActivityContract.MATCHER.match(uri)) {
            case ParentsActivityContract.ContactsTable.CONTENT:
                tableName = ParentsActivityContract.ContactsTable.TABLE_NAME;
                break;
            case ParentsActivityContract.ContactsTable.CONTENT_ID:
                tableName = ParentsActivityContract.ContactsTable.TABLE_NAME;
                selection += ParentsActivityContract.ContactsTable.Columns._ID + "=" + uri.getLastPathSegment();
                break;
            default:
                throw new IllegalArgumentException("Unknown URI Received: " + uri.toString());
        }

        result = helper.getWritableDatabase().delete(tableName, selection, selectionArgs);

        if (result == -1) {
            throw new NullPointerException();
        }
        return result;
    }

    @Override
    public String getType(Uri uri) {
        String result = "";

        switch (ParentsActivityContract.MATCHER.match(uri)) {
            case ParentsActivityContract.ContactsTable.CONTENT_ID:
                result += ParentsActivityContract.ContactsTable.SINGLE_MIME;
                break;
            case ParentsActivityContract.ContactsTable.CONTENT:
                result += ParentsActivityContract.ContactsTable.MULTI_MIME;
                break;
            default:
                result += ParentsActivityContract.MULTI_MIME;
                break;
        }

        return result;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Uri result = null;
        String tableName = "";

        switch (ParentsActivityContract.MATCHER.match(uri)) {
            case ParentsActivityContract.ContactsTable.CONTENT:
                tableName = ParentsActivityContract.ContactsTable.TABLE_NAME;
                break;
            default:
                throw new IllegalArgumentException("Unknown URI Received: " + uri.toString());
        }

        result = ContentUris.withAppendedId(ParentsActivityContract.BASE_URI, helper.getWritableDatabase().insert(tableName, "", values));

        if (result == null) {
            throw new NullPointerException();
        }
        return result;
    }

    @Override
    public boolean onCreate() {

        helper = new OpenHelper(getContext(), DBNAME, null, VER);

        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        Cursor result = null;
        String tableName = "";
        String groupBy = "";
        String having = "";
        String orderBy = "";
        String limit = "";

        switch (ParentsActivityContract.MATCHER.match(uri)) {
            case ParentsActivityContract.ContactsTable.CONTENT:
                tableName = ParentsActivityContract.ContactsTable.TABLE_NAME;
                break;
            case ParentsActivityContract.ContactsTable.CONTENT_ID:
                tableName = ParentsActivityContract.ContactsTable.TABLE_NAME;
                selection += ParentsActivityContract.ContactsTable.Columns._ID + "=" + uri.getLastPathSegment();
                break;
            default:
                throw new IllegalArgumentException("Unknown URI Received: " + uri.toString());
        }

        result = helper.getReadableDatabase().query(tableName, projection, selection, selectionArgs, groupBy, having, orderBy, limit);

        if (result == null) {
            throw new NullPointerException();
        }
        return result;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        int result = -1;
        String tableName = "";

        switch (ParentsActivityContract.MATCHER.match(uri)) {
            case ParentsActivityContract.ContactsTable.CONTENT:
                tableName = ParentsActivityContract.ContactsTable.TABLE_NAME;
                break;
            case ParentsActivityContract.ContactsTable.CONTENT_ID:
                tableName = ParentsActivityContract.ContactsTable.TABLE_NAME;
                selection += ParentsActivityContract.ContactsTable.Columns._ID + "=" + uri.getLastPathSegment();
                break;
            default:
                throw new IllegalArgumentException("Unknown URI Received: " + uri.toString());
        }

        result = helper.getWritableDatabase().update(tableName, values, selection, selectionArgs);

        if (result == -1) {
            throw new NullPointerException();
        }
        return result;
    }

    private class OpenHelper extends SQLiteOpenHelper {


        public OpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL(ParentsActivityContract.ContactsTable.CREATE_SQL);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
            if (i2 < i) {
                // Drop Tables
                sqLiteDatabase.execSQL("DROP TABLE " + ParentsActivityContract.ContactsTable.TABLE_NAME);

                //Recreate Tables
                sqLiteDatabase.execSQL(ParentsActivityContract.ContactsTable.CREATE_SQL);
            }
        }
    }

}
