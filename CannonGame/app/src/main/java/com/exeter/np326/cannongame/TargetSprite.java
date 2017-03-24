package com.exeter.np326.cannongame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;

/**
 * Created by nickplatt on 15/03/2017.
 */

public class TargetSprite extends Sprite {
    private int speed;
    private int scale = gameWidth/9;
    private int move = placement.nextInt(gameWidth - scale);
    private int height = gameHeight/8; // draw at this height
    private int targetWidth;
    private int targetHeight;



    public TargetSprite(int speed) {
        this.speed = speed*5; // speed of target
    }

    public void draw(Canvas canvas, Bitmap target) {
        target = Bitmap.createScaledBitmap(target, scale, scale, false);
        canvas.drawBitmap(target, move, height, null);
        move = update(target);
    }

    private int update(Bitmap target) {
        targetHeight = target.getHeight();
        targetWidth = target.getWidth();
        if (move >= (gameWidth - targetWidth) || move + speed < 0) {
            speed = -speed;
        }
        move += speed;
        return move;
    }

    public RectF position() {
        float bTopLeftX = move;
        float bTopLeftY = height;
        RectF space = new RectF(bTopLeftX, bTopLeftY, bTopLeftX+targetWidth, bTopLeftY+targetHeight);
        return space;
    }
}
