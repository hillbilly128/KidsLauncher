package com.hillbilly.kidslauncher;

import android.app.Fragment;
import android.content.ContentUris;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A fragment representing a single Person detail screen.
 * This fragment is either contained in a {@link PersonListActivity}
 * in two-pane mode (on tablets) or a {@link PersonDetailActivity}
 * on handsets.
 */
public class PersonDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";
    private final static String[] PROJECTION = new String[]{
            ParentsActivityContract.ContactsTable.Columns._ID,
            ParentsActivityContract.ContactsTable.Columns.NAME,
            ParentsActivityContract.ContactsTable.Columns.HOME_NUMBER,
            ParentsActivityContract.ContactsTable.Columns.MOBILE_NUMBER
    };
    private Cursor person;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PersonDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments().containsKey(ARG_ITEM_ID)) {
            person = getActivity().getContentResolver().query(
                    ContentUris.withAppendedId(ParentsActivityContract.ContactsTable.CONTENT_URI, getArguments().getInt(ARG_ITEM_ID)),
                    PROJECTION,
                    null,
                    null,
                    null
            );

            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            //mItem = DummyContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_person_detail, container, false);

        int nameColumn = person.getColumnIndex(ParentsActivityContract.ContactsTable.Columns.NAME);
        int homePhoneColumn = person.getColumnIndex(ParentsActivityContract.ContactsTable.Columns.HOME_NUMBER);
        int mobilePhoneColumn = person.getColumnIndex(ParentsActivityContract.ContactsTable.Columns.MOBILE_NUMBER);
        if (person.getCount() > 0) {
            person.moveToFirst();
            ((TextView) rootView.findViewById(R.id.person_name)).setText(person.getString(nameColumn));
            ((TextView) rootView.findViewById(R.id.person_home_num)).setText(person.getString(homePhoneColumn));
            ((TextView) rootView.findViewById(R.id.person_mobile_num)).setText(person.getString(mobilePhoneColumn));
        }

        return rootView;
    }
}
