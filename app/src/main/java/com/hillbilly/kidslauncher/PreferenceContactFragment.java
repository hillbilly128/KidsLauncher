package com.hillbilly.kidslauncher;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.provider.ContactsContract;
import android.util.Log;

public class PreferenceContactFragment extends PreferenceFragment
        implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int CONTACTS_LOADER = 0;

    private static final String TAG = "Parent Contact Settings";

    private PreferenceCategory contactsCategory;

    /*
     * (non-Javadoc)
     *
     * @see android.preference.PreferenceActivity#onCreate(android.os.Bundle)
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.getPreferenceManager()
                .setSharedPreferencesName(PersonListFragment.SETTINGS_NAME);

        addPreferencesFromResource(R.xml.parents_app_preference);
        PreferenceScreen ps = getPreferenceScreen();
        Log.v(TAG, "Preference Screen - " + ps.getTitle());
        contactsCategory = new PreferenceCategory(getActivity());
        Log.v(TAG, "Preference Category - " + contactsCategory.getTitle());
        contactsCategory.setTitle(R.string.Pref_Header_Contacts_Title);
        Log.v(TAG, "PC Title Set");
        contactsCategory.setSummary(R.string.Pref_Header_Contacts_Summary);
        Log.v(TAG, "PC Summary Set");
        //PackageManager pm = getActivity().getPackageManager();
        //Log.v(TAG, "Package Manager - " + pm.toString());
        ps.addPreference(contactsCategory);
        Log.v(TAG, "PC added to PS");

        getLoaderManager().initLoader(CONTACTS_LOADER, null, this);


//        Intent i = new Intent(Intent.ACTION_MAIN, null);
//        i.addCategory(Intent.CATEGORY_LAUNCHER);
//
//        List<ResolveInfo> availableActivities = pm.queryIntentActivities(
//                i, 0);
//        for (ResolveInfo ri : availableActivities) {
//            CheckBoxPreference p = new CheckBoxPreference(getActivity());
//
//            p.setTitle(ri.loadLabel(pm));
//            p.setSummary(ri.activityInfo.packageName);
//            p.setChecked(false);
//            p.setKey(ri.activityInfo.packageName);
//            Log.v(TAG, p.toString());
//            contactsCategory.addPreference(p);
//        }
//        setPreferenceScreen(ps);
//        Log.v(TAG, "Preference Screen set to ps");
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        /*
     * Takes action based on the ID of the Loader that's being created
     */

        Uri uri = ContactsContract.Contacts.CONTENT_URI;
        String[] projection = new String[]{
                ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME
        };

        switch (i) {
            case CONTACTS_LOADER:
                // Returns a new CursorLoader
                return new CursorLoader(
                        getActivity(),   // Parent activity context
                        uri,        // Table to query
                        projection,     // Projection to return
                        null,            // No selection clause
                        null,            // No selection arguments
                        null             // Default sort order
                );
            default:
                // An invalid id was passed in
                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        Log.v(TAG, "Loader finished cursor returned");
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {

            String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            String name = cursor.getString(
                    cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

            CheckBoxPreference cbp = new CheckBoxPreference(getActivity());
            cbp.setTitle(name);
            cbp.setKey(id);
            contactsCategory.addPreference(cbp);
            cursor.moveToNext();
            Log.v(TAG, "Contact " + name + " Added to Category");
        }
        Log.v(TAG, "All contacts added");

    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
        contactsCategory.removeAll();
    }
}
