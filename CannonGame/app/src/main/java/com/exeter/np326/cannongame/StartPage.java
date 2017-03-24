package com.exeter.np326.cannongame;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class StartPage extends AppCompatActivity {

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.starting_page);
        Configuration layout = getResources().getConfiguration();
        int orientation = layout.orientation;
    }


    /**
     * Method creates options for user to access help or about page
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu	menu) {
        getMenuInflater().inflate(R.menu.options, menu);
        return(super.onCreateOptionsMenu(menu));
    }


    /**
     * Method houses two options and can load new fragments for activity
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) { // Determines whether the help or about page has been selected
            case R.id.about:
                Intent i = new Intent(this, ContentActivity.class).putExtra(ContentActivity.EXTRA_FILE, "file:///android_asset/misc/about.html");
                startActivity(i);
                return(true); // load about page

            case R.id.help:
                i = new Intent(this, ContentActivity.class).putExtra(ContentActivity.EXTRA_FILE, "file:///android_asset/misc/help.html");
                startActivity(i);
                return(true); // load help page
        }
        return(super.onOptionsItemSelected(item));
    }


    /**
     * Switch statement determines which level the player has selected
     * to begin playing at.
     * @param level
     */
    public void levelChosen(View level) {
        TextView textView = (TextView)findViewById(R.id.textView); // Currently just prints what been selected
        int value = 0; // will correspond to the level selected
        Intent game; // games activity
        switch (level.getId()) {
            case R.id.level_one:
                value = 1;
                game = new Intent(this, GameActivity.class);
                game.putExtra("game_level", value); // Add variable for game level to next activity
                startActivity(game);
                break;
            case R.id.level_two:
                value = 2;
                game = new Intent(this, GameActivity.class);
                game.putExtra("game_level", value); // Add variable for game level to next activity
                startActivity(game);
                break;
            case R.id.level_three:
                value = 3;
                game = new Intent(this, GameActivity.class);
                game.putExtra("game_level", value); // Add variable for game level to next activity
                startActivity(game);
                break;
            case R.id.level_four:
                value = 4;
                game = new Intent(this, GameActivity.class);
                game.putExtra("game_level", value); // Add variable for game level to next activity
                startActivity(game);
                break;
            case R.id.level_five:
                value = 5;
                game = new Intent(this, GameActivity.class);
                game.putExtra("game_level", value); // Add variable for game level to next activity
                startActivity(game);
                break;
        }
    }

}
