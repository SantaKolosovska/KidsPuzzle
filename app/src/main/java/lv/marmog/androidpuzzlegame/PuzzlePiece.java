package lv.marmog.androidpuzzlegame;

import android.content.Context;


public class PuzzlePiece extends androidx.appcompat.widget.AppCompatImageView { //izmenila android.support.v7.widget.AppCompatImageView
    public int xCoord;
    public int yCoord;
    public int pieceWidth;
    public int pieceHeight;
    public boolean canMove = true;



    public PuzzlePiece(Context context) {
        super(context);
    }

}