package com.exeter.np326.cannongame;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;

/**
 * Activity
 */
public class ContentActivity extends Activity {

    public static final String EXTRA_FILE = "file";

    /**
     * Method creating activity for displaying the html about or home page.
     * Allows the fragments to be placed on view.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getFragmentManager().findFragmentById(android.R.id.content)	== null) {
            String file = getIntent().getStringExtra(EXTRA_FILE);
            Fragment fragment = ContentFragment.newInstance(file); // Passes content fragment of html page

            getFragmentManager().beginTransaction().add(android.R.id.content, fragment).commit(); // add fragment to screen
        }
    }
}
