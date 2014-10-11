package com.hillbilly.kidslauncher;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

/**
 * A fragment representing a single Person detail screen.
 * This fragment is either contained in a {@link PersonListActivity}
 * in two-pane mode (on tablets) or a {@link PersonDetailActivity}
 * on handsets.
 */
public class PersonDetailFragment extends Fragment
        implements LoaderManager.LoaderCallbacks<Cursor> {

    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * Constant Identifying the loader
     */
    private final static int CONTACT_DETAILS_LOADER = 0;

    private static final String[] PROJECTION =
            {
                    ContactsContract.Data._ID,
                    ContactsContract.Data.DISPLAY_NAME,
                    ContactsContract.Data.MIMETYPE,
                    ContactsContract.Data.DATA1,
                    ContactsContract.Data.DATA2,
                    ContactsContract.Data.DATA3,
                    ContactsContract.Data.DATA4,
                    ContactsContract.Data.DATA5,
                    ContactsContract.Data.DATA6,
                    ContactsContract.Data.DATA7,
                    ContactsContract.Data.DATA8,
                    ContactsContract.Data.DATA9,
                    ContactsContract.Data.DATA10,
                    ContactsContract.Data.DATA11,
                    ContactsContract.Data.DATA12,
                    ContactsContract.Data.DATA13,
                    ContactsContract.Data.DATA14,
                    ContactsContract.Data.DATA15
            };

    /*
     * Defines a string that specifies a sort order of MIME type
     */
    private static final String SORT_ORDER = ContactsContract.Data.MIMETYPE;

    // Defines the selection clause
    private static String SELECTION = ContactsContract.Data.LOOKUP_KEY + " = ?";

    private ListView fLv;

    /**
     * The rootview of the display
     */
    private View fRootView;

    private MultiViewCursorAdapter Fmvca;

    /*
     * Defines a variable to contain the selection value. Once you
     * have the Cursor from the Contacts table, and you've selected
     * the desired row, move the row's LOOKUP_KEY value into this
     * variable.
     */
    private String mLookupKey;

    // Defines the array to hold the search criteria
    private String[] mSelectionArgs = {""};


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PersonDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLookupKey = getArguments().getString(ARG_ITEM_ID);
        getLoaderManager().initLoader(CONTACT_DETAILS_LOADER, null, this);
    }

    /**
     * Instantiate and return a new Loader for the given ID.
     *
     * @param id   The ID whose loader is to be created.
     * @param args Any arguments supplied by the caller.
     *
     * @return Return a new Loader instance that is ready to start loading.
     */
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Loader<Cursor> result = null;

        // Choose the proper action
        switch (id) {
            case CONTACT_DETAILS_LOADER:
                // Assigns the selection parameter
                mSelectionArgs[0] = mLookupKey;
                // Starts the query
                result = new CursorLoader(
                        getActivity(),
                        ContactsContract.Data.CONTENT_URI,
                        PROJECTION,
                        SELECTION,
                        mSelectionArgs,
                        SORT_ORDER
                );
                break;
        }
        return result;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fRootView = inflater.inflate(R.layout.fragment_person_detail, container, false);

        Fmvca = new MultiViewCursorAdapter(getActivity(), null, 0);

        fLv = (ListView) fRootView.findViewById(R.id.ContactDataList);

        fLv.setAdapter(Fmvca);

        return fRootView;
    }

    /**
     * Called when a previously created loader has finished its load.  Note
     * that normally an application is <em>not</em> allowed to commit fragment
     * transactions while in this call, since it can happen after an
     * activity's state is saved.  See {@link android.app.FragmentManager#beginTransaction()
     * FragmentManager.openTransaction()} for further discussion on this.
     * <p/>
     * <p>This function is guaranteed to be called prior to the release of
     * the last data that was supplied for this Loader.  At this point
     * you should remove all use of the old data (since it will be released
     * soon), but should not do your own release of the data since its Loader
     * owns it and will take care of that.  The Loader will take care of
     * management of its data so you don't have to.  In particular:
     * <p/>
     * <ul>
     * <li> <p>The Loader will monitor for changes to the data, and report
     * them to you through new calls here.  You should not monitor the
     * data yourself.  For example, if the data is a {@link android.database.Cursor}
     * and you place it in a {@link android.widget.CursorAdapter}, use
     * the {@link android.widget.CursorAdapter#CursorAdapter(android.content.Context,
     * android.database.Cursor, int)} constructor <em>without</em> passing
     * in either {@link android.widget.CursorAdapter#FLAG_AUTO_REQUERY}
     * or {@link android.widget.CursorAdapter#FLAG_REGISTER_CONTENT_OBSERVER}
     * (that is, use 0 for the flags argument).  This prevents the CursorAdapter
     * from doing its own observing of the Cursor, which is not needed since
     * when a change happens you will get a new Cursor throw another call
     * here.
     * <li> The Loader will release the data once it knows the application
     * is no longer using it.  For example, if the data is
     * a {@link android.database.Cursor} from a {@link android.content.CursorLoader},
     * you should not call close() on it yourself.  If the Cursor is being placed in a
     * {@link android.widget.CursorAdapter}, you should use the
     * {@link android.widget.CursorAdapter#swapCursor(android.database.Cursor)}
     * method so that the old Cursor is not closed.
     * </ul>
     *
     * @param loader The Loader that has finished.
     * @param data   The data generated by the Loader.
     */
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        switch (loader.getId()) {
            case CONTACT_DETAILS_LOADER:
                Fmvca.swapCursor(data);

                break;
        }
    }

    /**
     * Called when a previously created loader is being reset, and thus
     * making its data unavailable.  The application should at this point
     * remove any references it has to the Loader's data.
     *
     * @param loader The Loader that is being reset.
     */
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        switch (loader.getId()) {
            case CONTACT_DETAILS_LOADER:
                Fmvca.swapCursor(null);
        }

    }

    private class MultiViewCursorAdapter extends CursorAdapter {

        public static final int VIEW_TYPE_PHONE = 0;

        public static final int VIEW_TYPE_NAME = 1;

        public static final int VIEW_TYPE_EMAIL = 2;

        public static final int VIEW_TYPE_EVENT = 3;

        public static final int VIEW_TYPE_IM = 4;

        private LayoutInflater li;

        private Context fContext;

        /**
         * Recommended constructor.
         *
         * @param context The context
         * @param c       The cursor from which to get the data.
         * @param flags   Flags used to determine the behavior of the adapter; may
         *                be any combination of {@link #FLAG_AUTO_REQUERY} and
         *                {@link #FLAG_REGISTER_CONTENT_OBSERVER}.
         */
        public MultiViewCursorAdapter(Context context, Cursor c, int flags) {
            super(context, c, flags);
            li = LayoutInflater.from(context);
            fContext = context;
        }

        /**
         * Bind an existing view to the data pointed to by cursor
         *
         * @param view    Existing view, returned earlier by newView
         * @param context Interface to application's global information
         * @param cursor  The cursor from which to get the data. The cursor is already
         */
        @Override
        public void bindView(View view, Context context, Cursor cursor) {

            String mimeType = cursor.getString(
                    cursor.getColumnIndex(ContactsContract.Data.MIMETYPE));

            Resources res = fContext.getResources();
            if (view != null){
                if (mimeType.contentEquals(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)) { //Phone
                    ((TextView) view.findViewById(R.id.Person_Item_Detail_Type)).setText(
                            ContactsContract.CommonDataKinds.Phone.getTypeLabel(
                                    Resources.getSystem(),
                                    cursor.getType(cursor.getColumnIndex(
                                            ContactsContract.CommonDataKinds.Phone.TYPE)),
                                    cursor.getString(cursor.getColumnIndex(
                                            ContactsContract.CommonDataKinds.Phone.LABEL))));

                    ((TextView) view.findViewById(R.id.Person_Item_Detail_Data)).setText(
                            cursor.getString(cursor.getColumnIndex(
                                    ContactsContract.CommonDataKinds.Phone.NUMBER)));

                    ((Button) view.findViewById(R.id.Person_Item_Detail_Call)).setOnClickListener(
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                        /* TODO Add on click to initiate phone call */
                                }
                            });
                    ((Button) view.findViewById(R.id.Person_Item_Detail_Sms)).setOnClickListener(
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                         /* TODO add on click to initiate Text message */
                                }
                            });
                }
                else if (mimeType.contentEquals(
                        ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)) { //Name
                    ((TextView) view.findViewById(R.id.Person_Detail_List_Contact_Name)).setText(
                            cursor.getString(cursor.getColumnIndex(
                                    ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME)));
                }
                else if (mimeType.contentEquals(
                        ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)) { //Email
                    ((TextView) view.findViewById(R.id.Person_Item_Detail_Type)).setText(
                            ContactsContract.CommonDataKinds.Email.getTypeLabel(
                                    Resources.getSystem(),
                                    cursor.getType(cursor.getColumnIndex(
                                            ContactsContract.CommonDataKinds.Email.TYPE)),
                                    cursor.getString(cursor.getColumnIndex(
                                            ContactsContract.CommonDataKinds.Email.LABEL))));
                    TextView data = ((TextView) view.findViewById(R.id.Person_Item_Detail_Data));

                    data.setText(
                            cursor.getString(cursor.getColumnIndex(
                                    ContactsContract.CommonDataKinds.Phone.NUMBER)));
                    data.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);


                    LinearLayout.LayoutParams vLayoutParams;
                    Button b1 = ((Button) view.findViewById(R.id.Person_Item_Detail_Call));
                    b1.setOnClickListener(
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                        /* TODO Add on click to initiate email */
                                }
                            });

                    b1.setCompoundDrawablesWithIntrinsicBounds(
                            res.getDrawable(android.R.drawable.ic_dialog_email), null, null, null);

                    vLayoutParams = (b1.getLayoutParams()
                                             .getClass() == LinearLayout.LayoutParams.class) ?
                                    (LinearLayout.LayoutParams) b1.getLayoutParams() :
                                    new LinearLayout.LayoutParams(context, null);
                    vLayoutParams.weight = 2;
                    b1.setLayoutParams(vLayoutParams);

                    Button b2 = ((Button) view.findViewById(R.id.Person_Item_Detail_Sms));
                    b2.setVisibility(View.GONE);
                }
                else if (mimeType.contentEquals(
                        ContactsContract.CommonDataKinds.Event.CONTENT_ITEM_TYPE)) { //Event
                    TextView type = ((TextView) view.findViewById(R.id.Person_Item_Detail_Type));

                    switch (ContactsContract.CommonDataKinds.Event.getTypeResource(
                            cursor.getInt(cursor.getColumnIndex(
                                    ContactsContract.CommonDataKinds.Event.TYPE)))) {
                        case ContactsContract.CommonDataKinds.Event.TYPE_ANNIVERSARY:
                            type.setText("Anniversary");
                            break;
                        case ContactsContract.CommonDataKinds.Event.TYPE_BIRTHDAY:
                            type.setText("Birthday");
                            break;
                        case ContactsContract.CommonDataKinds.Event.TYPE_OTHER:
                            type.setText("Other");
                            break;
                        case ContactsContract.CommonDataKinds.Event.TYPE_CUSTOM:
                            type.setText(cursor.getString(cursor.getColumnIndex(
                                    ContactsContract.CommonDataKinds.Event.LABEL)));
                            break;
                    }

                    TextView data = ((TextView) view.findViewById(R.id.Person_Item_Detail_Data));

                    data.setText(
                            cursor.getString(cursor.getColumnIndex(
                                    ContactsContract.CommonDataKinds.Event.START_DATE)));
                    data.setInputType(InputType.TYPE_DATETIME_VARIATION_DATE);


                    LinearLayout.LayoutParams vLayoutParams;
                    Button b1 = ((Button) view.findViewById(R.id.Person_Item_Detail_Call));
                    b1.setOnClickListener(
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                        /* TODO Add on click to open event in calendar */
                                }
                            });

                    b1.setCompoundDrawablesWithIntrinsicBounds(
                            res.getDrawable(android.R.drawable.ic_menu_agenda), null, null, null);

                    vLayoutParams = (b1.getLayoutParams()
                                             .getClass() == LinearLayout.LayoutParams.class) ?
                                    (LinearLayout.LayoutParams) b1.getLayoutParams() :
                                    new LinearLayout.LayoutParams(context, null);
                    vLayoutParams.weight = 2;
                    b1.setLayoutParams(vLayoutParams);

                    Button b2 = ((Button) view.findViewById(R.id.Person_Item_Detail_Sms));
                    b2.setVisibility(View.GONE);
                } else if (mimeType.contentEquals(
                        ContactsContract.CommonDataKinds.Im.CONTENT_ITEM_TYPE)) { //IM
                    TextView type = ((TextView) view.findViewById(R.id.Person_Item_Detail_Type));

                    type.setText(ContactsContract.CommonDataKinds.Im.getTypeLabel(res,cursor.getInt(cursor.getColumnIndex(
                            ContactsContract.CommonDataKinds.Im.TYPE)),cursor.getString(cursor.getColumnIndex(
                            ContactsContract.CommonDataKinds.Im.LABEL))));

                    TextView proto = ((TextView) view.findViewById(R.id.person_detail_phone_list_im_protocol));
                    proto.setText(ContactsContract.CommonDataKinds.Im.getProtocolLabel(res,cursor.getInt(cursor.getColumnIndex(
                            ContactsContract.CommonDataKinds.Im.PROTOCOL)),cursor.getString(cursor.getColumnIndex(
                            ContactsContract.CommonDataKinds.Im.CUSTOM_PROTOCOL))));

                    TextView data = ((TextView) view.findViewById(R.id.person_detail_phone_list_im_data));

                    data.setText(
                            cursor.getString(cursor.getColumnIndex(
                                    ContactsContract.CommonDataKinds.Im.DATA)));
                    data.setInputType(InputType.TYPE_CLASS_TEXT+InputType.TYPE_TEXT_VARIATION_NORMAL);


                    LinearLayout.LayoutParams vLayoutParams;
                    Button b1 = ((Button) view.findViewById(R.id.person_detail_phone_list_im_action));
                    b1.setOnClickListener(
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                        /* TODO Add on click to open contact in IM app */
                                }
                            });
                }
            }
        }

        @Override
        public int getItemViewType(int position) {
            Cursor c = (Cursor) getItem(position);
            String mimeType = c.getString(c.getColumnIndex(ContactsContract.Data.MIMETYPE));

            if (mimeType.contentEquals(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)) {
                return VIEW_TYPE_PHONE;
            } else if (mimeType.contentEquals(ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)) {
                return VIEW_TYPE_NAME;
            } else if (mimeType.contentEquals(ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)) {
                return VIEW_TYPE_EMAIL;
            } else if (mimeType.contentEquals(ContactsContract.CommonDataKinds.Event.CONTENT_ITEM_TYPE)) {
                return VIEW_TYPE_EVENT;
            } else if (mimeType.contentEquals(ContactsContract.CommonDataKinds.Im.CONTENT_ITEM_TYPE)) {
                return VIEW_TYPE_IM;
            }

            return -1;
        }

        @Override
        public int getViewTypeCount() {
            return 5;
        }

        /**
         * Makes a new view to hold the data pointed to by cursor.
         *
         * @param context Interface to application's global information
         * @param cursor  The cursor from which to get the data. The cursor is already
         *                moved to the correct position.
         * @param parent  The parent to which the new view is attached to
         *
         * @return the newly created view.
         */
        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            View result = null;

            String mimeType = cursor.getString(
                    cursor.getColumnIndex(ContactsContract.Data.MIMETYPE));
            if (mimeType.contentEquals(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)) { //Phone Number
                result = li.inflate(R.layout.person_detail_phone_list_item, parent, false);
            }
            else if (mimeType.contentEquals(
                    ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)) { //Name
                result = li.inflate(R.layout.person_detail_phone_list_name, parent, false);
            }
            else if (mimeType.contentEquals(ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)) { //Email
                result = li.inflate(R.layout.person_detail_phone_list_item, parent, false);
            }
            else if (mimeType.contentEquals(ContactsContract.CommonDataKinds.Event.CONTENT_ITEM_TYPE)) { //Event
                result = li.inflate(R.layout.person_detail_phone_list_item, parent, false);
            }

            return result;
        }
    }
}

