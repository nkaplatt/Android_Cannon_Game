package com.exeter.np326.cannongame;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

/**
 * Created by nickplatt on 17/03/2017.
 */

public class SpriteView extends SurfaceView implements SurfaceHolder.Callback {
    GameModel model;
    Paint textPaint;
    Sprite sprite = new Sprite(); // used to get the width of the canvas screen
    public int levelPlaying;
    private SpriteThread spriteThread; // game loop
    ArrayList<TargetSprite> targets; // list of targets
    ArrayList<CannonBallSprite> cannonballs; // list of cannonballs
    Boolean clicked = false;
    int cannonsUsed = 0;
    boolean hitTarget, hitBlocker = false;
    AlertDialog alert;
    int time;
    MediaPlayer targetSound = MediaPlayer.create(getContext(), R.raw.glasssmash);
    MediaPlayer hit = MediaPlayer.create(getContext(), R.raw.hitsound);
    MediaPlayer cannonfire = MediaPlayer.create(getContext(), R.raw.cannonfire);
    SharedPreferences top_scores;
    SharedPreferences.Editor myEditor;
    final String score_sheet = "score_sheet";


    /**
     * Constructor for spriteview
     * @param context
     * @param attrs
     * @param level
     */
    public SpriteView(Context context, AttributeSet attrs, int level) {
        super(context, attrs);
        getHolder().addCallback(this);
        textPaint = new Paint();
        textPaint.setTextSize(sprite.gameWidth/30);
        textPaint.setAntiAlias(true);
        textPaint.setARGB(255, 255, 0, 0);

        levelPlaying = level; // variable carrying the game level being played

        // create a reference & editor for the shared preferences object
        top_scores = getContext().getSharedPreferences(score_sheet, 0);
        myEditor = top_scores.edit();

        // create file with values 0
        if (top_scores == null) {
            myEditor.putInt("1", 0);
            myEditor.putInt("2", 0);
            myEditor.putInt("3", 0);
            myEditor.putInt("4", 0);
            myEditor.putInt("5", 0);
            myEditor.commit();
        }
    }
    Configuration layout = getContext().getResources().getConfiguration();
    int orientation = layout.orientation;

    public void preferences() {
        // update the leaderboard
        int a = top_scores.getInt("1", 0);
        int b = top_scores.getInt("2", 0);
        int c = top_scores.getInt("3", 0);
        int d = top_scores.getInt("4", 0);
        int e = top_scores.getInt("5", 0);

        if (a < model.score) {
            myEditor.putInt("1", model.score);
            myEditor.putInt("2", a);
            myEditor.putInt("3", b);
            myEditor.putInt("4", c);
            myEditor.putInt("5", d);
        } else if (b < model.score) {
            myEditor.putInt("2", model.score);
            myEditor.putInt("3", b);
            myEditor.putInt("4", c);
            myEditor.putInt("5", d);
        } else if (c < model.score) {
            myEditor.putInt("3", model.score);
            myEditor.putInt("4", c);
            myEditor.putInt("5", d);
        } else if (d < model.score) {
            myEditor.putInt("4", model.score);
            myEditor.putInt("5", d);
        } else if (e < model.score) {
            myEditor.putInt("5", model.score);
        }
        myEditor.commit();
    }

    /**
     * Method creates a new alert for the end game phase
     * @param msg
     * @param score
     * @param ballsfired
     * @return
     */
    public AlertDialog new_alert(String msg, int score, int ballsfired, int time) {
        alert = new AlertDialog.Builder(getContext()).setTitle("Game Over").setMessage(msg
            + "\nNumber of cannonballs fired: " + ballsfired + "\nYour Score: " + score +
                "\nLeaderboard: " +  "\n" +
                "1: " + top_scores.getInt("1", 0) + "\n" +
                "2: " + top_scores.getInt("2", 0) + "\n" +
                "3: " + top_scores.getInt("3", 0) + "\n" +
                "4: " + top_scores.getInt("4", 0) + "\n" +
                "5: " + top_scores.getInt("5", 0) + "\n" +
                "\nTime played: " + time + " seconds")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        surfaceDestroyed(getHolder());
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert).show();
        return alert;
    }

    /**
     * Method to notify the app the game is over and to generate the alert dialog
     * @param ballsfired
     * @param score
     * @param winner
     * @param time
     */
    public void gameEnds(final int ballsfired, final int score, final boolean winner, final int time) {
        spriteThread.setRunning(false);
        getHandler().post(new Runnable() {
            @Override
            public void run() {
                if (winner) {
                    String msg = "You're a winner!";
                    AlertDialog alertd = new_alert(msg, score, ballsfired, time);
                    alertd.show();
                } else {
                    String msg = "Unlucky, you lost, maybe next time.";
                    AlertDialog alertd = new_alert(msg, score, ballsfired, time);
                    alertd.show();
                }
            }
        });

    }

    /**
     * Surface created for game
     * @param holder
     */
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        model = new GameModel(levelPlaying);
        spriteThread = new SpriteThread(holder);
        spriteThread.setRunning(true);
        spriteThread.start();
    }

    /**
     * @param holder
     * @param format
     * @param width
     * @param height
     */
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    /**
     * Method destroys threads safely
     * @param holder
     */
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        spriteThread.setRunning(false); // terminate Thread
        while (retry) {
            try {
                spriteThread.join(); // wait for Thread to finish
                retry = false;
            }
            catch (InterruptedException e) {
                Log.e(TAG, "Thread interrupted", e);
            }
        }
        if (alert != null) {
            alert.dismiss();
        }
        ((Activity) getContext()).finish();
    }

    /**
     * Method that handles the drawing of elements within the game
     * Is repeatedly called within the main thread of the game
     * @param canvas
     */
    public void drawGameElements(Canvas canvas) {
        super.draw(canvas);
        // dependencies for following functions
        Bitmap targetIcon = BitmapFactory.decodeResource(getResources(), R.drawable.target_icon); // fetch the image from resources
        targets = model.targetSprites;

        Bitmap cannonball = BitmapFactory.decodeResource(getResources(), R.drawable.cannonball);
        cannonballs = model.cannonballSprites;

        Bitmap cannonIcon = BitmapFactory.decodeResource(getResources(), R.drawable.cannon);
        CannonSprite cannon = model.cannonSprite;

        BlockerSprite blocker = model.blockerSprite;

        if (targets.size() > 0) {
            TargetSprite target = targets.get(0);
            target.draw(canvas, targetIcon);
            if (collisionTarget(cannonballs.get(0), targets.get(0))) {
                model.score += (10 * levelPlaying);
                model.timeRemaining += 3000;
                targets.remove(target);
                targetSound.start();
                hitTarget = false;
            }
        } else {
            preferences();
            gameEnds(cannonsUsed, model.score, true, (time/1000)); // targets ended so loses game
        }

        // draw cannonball
        if (clicked) {
            // on click event so draw cannonball
            cannonballs.get(0).level = levelPlaying;
            cannonballs.get(0).draw(canvas, cannonball); // fetch first cannonball
            if (cannonballs.get(0).offScreen) { // remove cannonball if its gone off screen
                cannonballs.remove(0); // remove the cannonball
                cannonballs.get(0).offScreen = false;
                clicked = false;
            }
        }

        // draw cannon
        cannon.draw(canvas, cannonIcon);

        // draw blocker
        blocker.draw(canvas);

        if (collisionBlocker(cannonballs.get(0), blocker)) {
            cannonballs.get(0).hitBlocker();
            model.score -= (15*levelPlaying);
            model.timeRemaining -= 2000;
            hit.start();
            hitBlocker = false;
        }

        // draw time, score and top score
        canvas.drawText(getResources().getString(
                R.string.time_remaining_format, model.timeRemaining/1000), 50, 45, textPaint);
        canvas.drawText(getResources().getString(
                R.string.score_format, model.score), 50, 80, textPaint);
        canvas.drawText("Top Score: "+top_scores.getInt("1",0), GameActivity.getWidth()/3, 80, textPaint);

        // stop score being below 0
        if (model.score < 0) {
            model.score = 0;
        }

        if (model.gameOver()) {
            preferences();
            gameEnds(cannonsUsed, model.score, false, (time/1000)); // timer ended so loses game
        }
    }

    /**
     * Method to detect whether the cannonball hits a target
     * @param Ball
     * @param target
     * @return
     */
    public boolean collisionTarget(CannonBallSprite Ball, TargetSprite target) {
        RectF rect1 = Ball.position();
        RectF rect2 = target.position();
        hitTarget = RectF.intersects(rect1, rect2);
        return hitTarget;
    }

    /**
     * Method that detects when the cannonball hits the blocker
     * @param Ball
     * @param blocker
     * @return
     */
    public  boolean collisionBlocker(CannonBallSprite Ball, BlockerSprite blocker) {
        RectF rect1 = Ball.position();
        RectF rect2 = blocker.position();
        hitBlocker = RectF.intersects(rect1, rect2);
        return hitBlocker;
    }

    /**
     * Method to fire a cannonball when screen touched
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        clicked = true;
        cannonsUsed++;
        float x = event.getX();
        float y = event.getY();
        cannonfire.start();
        CannonBallSprite ball = new CannonBallSprite();
        cannonballs.add(ball); // add cannonball to the array

        // draw cannonball from cannon to this mark
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            model.clickFire(x, y);
            model.moveCannon(x, y);
        }
        return super.onTouchEvent(event);
    }



    private class SpriteThread extends Thread {
        private SurfaceHolder surfaceHolder; // for manipulating canvas
        private boolean threadRunning = true; // running by default

        /**
         * Method to create new SpriteThread - runs the canvas redrawing
         * i.e. the game
         * @param holder
         */
        public SpriteThread(SurfaceHolder holder) {
            surfaceHolder = holder;
            setName("Sprite Thread");
        }

        /**
         * Method to set thread to running
         * @param running
         */
        public void setRunning(boolean running) {
            threadRunning = running;
        }

        /**
         * Main running thread of game
         */
        @Override
        public void run() {
            Canvas canvas = null;
            long previousFrameTime = System.currentTimeMillis();

            while (threadRunning) {
                try {
                    canvas = surfaceHolder.lockCanvas(null);

                    long currentTime = System.currentTimeMillis();
                    double elapsedTimeMS = currentTime - previousFrameTime;

                    synchronized (surfaceHolder) {
                        model.update((int) elapsedTimeMS);
                        time += (int) (elapsedTimeMS); // time played for
                        drawGameElements(canvas);
                        previousFrameTime = currentTime; // update previous time
                    }
                } finally {
                    if (canvas != null) {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                }
            }
        }
    }
}
