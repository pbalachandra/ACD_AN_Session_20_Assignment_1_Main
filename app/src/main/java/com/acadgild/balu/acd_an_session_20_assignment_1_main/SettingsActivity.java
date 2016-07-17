package com.acadgild.balu.acd_an_session_20_assignment_1_main;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * Created by BALU on 6/20/2016.
 */
public class SettingsActivity extends PreferenceActivity
{
    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
    }
}
