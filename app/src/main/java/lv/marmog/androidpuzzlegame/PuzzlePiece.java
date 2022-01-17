package lv.marmog.androidpuzzlegame;

import android.content.Context;

/**
 * Puzzle piece parameters, coordinates, width, height and canMove- if its on its place or not
 */
public class PuzzlePiece extends androidx.appcompat.widget.AppCompatImageView { //changed android.support.v7.widget.AppCompatImageView
    public int xCoord;
    public int yCoord;
    public int pieceWidth;
    public int pieceHeight;
    public boolean canMove = true;

    public PuzzlePiece(Context context) {
        super(context);
    }

}