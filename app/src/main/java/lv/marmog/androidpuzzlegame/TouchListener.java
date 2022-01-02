package lv.marmog.androidpuzzlegame;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import static java.lang.Math.abs;

import androidx.annotation.ColorInt;

import java.io.IOException;


public class TouchListener implements View.OnTouchListener {

    private float xDelta;
    private float yDelta;
    private PuzzleActivity activity;
    private MediaPlayer sound;

    public TouchListener(PuzzleActivity activity) {
        this.activity = activity;
    }

    public TouchListener() {
    }




    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        float x = motionEvent.getRawX();
        float y = motionEvent.getRawY();

        final double tolerance = sqrt(pow(view.getWidth(), 2) + pow(view.getHeight(), 2)) / 10;

        PuzzlePiece piece = (PuzzlePiece) view;
        if (!piece.canMove) {
            return true;

        }

        RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) view.getLayoutParams();

            // --- pieces become larger on touch ---
            lParams.height = piece.pieceHeight;
            lParams.width = piece.pieceWidth;
            // --- /pieces become larger on touch


        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN: //push
                xDelta = x - lParams.leftMargin;
                yDelta = y - lParams.topMargin;
                piece.bringToFront();

                // --- add sound
                sound = MediaPlayer.create(activity.getApplicationContext(), R.raw.salt_shake);
                sound.start();
                stopOnCompletion(sound);
                // --- /add sound

                break;
            case MotionEvent.ACTION_MOVE: //move
                lParams.leftMargin = (int) (x - xDelta);
                lParams.topMargin = (int) (y - yDelta);
                view.setLayoutParams(lParams);
                break;
            case MotionEvent.ACTION_UP: //release
                int xDiff = abs(piece.xCoord - lParams.leftMargin);
                int yDiff = abs(piece.yCoord - lParams.topMargin);
                if (xDiff <= tolerance && yDiff <= tolerance) {
                    lParams.leftMargin = piece.xCoord;
                    lParams.topMargin = piece.yCoord;


                    //blink------------------------------------------------------------------
                    CountDownTimer blinkTimer = new CountDownTimer(300,100) {

                        @Override
                        public void onTick(long millisUntilFinished) {
                            piece.setColorFilter(0X80FFFFFF); //can change filter or make blinking faster

                        }

                        @Override
                        public void onFinish() {
                            piece.clearColorFilter();
                        }

                    };
                    blinkTimer.start();
                    //-------------------------------------------------------------------blink

                    // --- add sound
                    sound = MediaPlayer.create(activity.getApplicationContext(), R.raw.lighter_flick3);
                    sound.start();
                    stopOnCompletion(sound);
                    // --- /add sound

                    piece.setLayoutParams(lParams);
                    piece.canMove = false;
                    sendViewToBack(piece);
                    activity.checkGameOver();
                }
                break;
        }

        return true;
    }

    public void sendViewToBack(final View child) {
        final ViewGroup parent = (ViewGroup) child.getParent();
        if (null != parent) {
            parent.removeView(child);
            parent.addView(child, 0);
        }
    }

    // stop media player after playing sound
    public void stopOnCompletion(MediaPlayer mp) {
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
                mp = null;
            }
        });
    }

}
