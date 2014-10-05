package com.hillbilly.kidslauncher;


import android.app.Fragment;
import android.os.Bundle;
import android.preference.PreferenceActivity;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ParentsSettingsActivity extends PreferenceActivity {


    public ParentsSettingsActivity() {
        // Required empty public constructor
    }

    @Override
    public void onBuildHeaders(List<Header> target) {
        loadHeadersFromResource(R.xml.parents_setting_headers, target);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
