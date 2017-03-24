package com.exeter.np326.cannongame;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;

public class GameActivity extends Activity {
    SpriteView view;

    /**
     * Game activity creates content view of 5 buttons depicting levels
     * moves onto intent of gameview
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int n = 0;
        Bundle extras = getIntent().getExtras(); // gets extra from level selection in activity one
        if (extras != null) {
            n = extras.getInt("game_level"); // fetch game level
        }

        view = new SpriteView(this, null, n); // new view of game
        setContentView(view); // loads the game canvas
    }

    /**
     * Meethod fetches the width of the device
     * @return
     */
    public static int getWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    /**
     * Method fetches the height of the device
     * @return
     */
    public static int getHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

}
