package com.exeter.np326.cannongame;

import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.Random;

/**
 * Created by Nick on 19/03/2017.
 */

public class Sprite {
    private Vector2d s, v;
    Random placement = new Random();
    int gameWidth = GameActivity.getWidth();
    int gameHeight = GameActivity.getHeight();

    /**
     *
     */
    public Sprite() {

    }

    /**
     * @param canvas
     */
    public void draw(Canvas canvas) {

    }
}
