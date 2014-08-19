package com.hillbilly.kidslauncher;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.util.Log;

import java.util.List;

public class ParentsSettingsFragment extends PreferenceFragment {

    private static final String TAG = "Parent Settings";

	/*
	 * (non-Javadoc)
	 *
	 * @see android.preference.PreferenceActivity#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//PreferenceManager prefm = getPreferenceManager();
		addPreferencesFromResource(R.xml.parents_preference);
		PreferenceScreen ps = getPreferenceScreen();
        Log.v(TAG,"Preference Screen - " + ps.getTitle());
        PreferenceCategory pc = new PreferenceCategory(getActivity());
        Log.v(TAG,"Preference Category - " + pc.getTitle());
		pc.setTitle(R.string.Pref_Cat_Apps);
        Log.v(TAG,"PC Title Set");
		pc.setSummary(R.string.Pref_Cat_Apps_Summary);
        Log.v(TAG, "PC Summary Set");
		PackageManager pm = getActivity().getPackageManager();
        Log.v(TAG, "Package Manager - " + pm.toString());
        ps.addPreference(pc);
        Log.v(TAG, "PC added to PS");
        Intent i = new Intent(Intent.ACTION_MAIN, null);
        i.addCategory(Intent.CATEGORY_LAUNCHER);

        List<ResolveInfo> availableActivities = pm.queryIntentActivities(
                i, 0);
        for (ResolveInfo ri : availableActivities) {
            CheckBoxPreference p = new CheckBoxPreference(getActivity());

            p.setTitle(ri.loadLabel(pm));
            p.setSummary(ri.activityInfo.packageName);
            p.setChecked(false);
            p.setKey(ri.activityInfo.packageName);
            Log.v(TAG, p.toString());
            pc.addPreference(p);

        }

		//setPreferenceScreen(ps);
        Log.v(TAG, "Preference Screen set to ps");
	}
}
