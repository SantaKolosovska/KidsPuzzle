package lv.marmog.androidpuzzlegame;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
//import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
//import android.view.View;
//import android.widget.AdapterView;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import static java.lang.Math.abs;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;


public class PuzzleActivity extends AppCompatActivity {
    ArrayList<PuzzlePiece> pieces;


    //picture from camera and gallery-------------------------------------------------------------------
    String mCurrentPhotoPath;
    String  mCurrentPhotoUri;
    //-------------------------------------------------------------------picture from camera
    //timer---------------------------------------
    TextView countTimer;
    //------------------------------------timer

    //popup-----------------------------------------------------
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private TextView newTimeIsUpText;
    private Button newTimeIsUpNext;
    //-----------------------------------------------------popup

//    int[] arrayPiecesColsRowsId = new int[4];
    int piecesNumber;
    int cols;
    int rows;
    int userId;



    //timer----------------------------------------------------------------
    int secondsRemaining = 300;//how many seconds left in timer
    CountDownTimer timer = new CountDownTimer(300000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) { //every time the clock ticks
            secondsRemaining--;
            countTimer.setText(Integer.toString(299 - secondsRemaining) + "sec"); //textView- xml

        }

        @Override
        public void onFinish() {
            createNewContentDialog(); //creates popup window-------------popup-----------
            timer.cancel();
        }
    };
    //---------------------------------------------------------timer

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle);

        //timer-------------------------------------------------------
        //counter initializing
        countTimer = findViewById(R.id.count_timer);
        countTimer.setText("OSec");
        timer.start();//starting timer
        //-------------------------------------------------timer



        final RelativeLayout layout = findViewById(R.id.layout);
        final ImageView imageView = findViewById(R.id.imageView);
        // ---- timer for getting it's height and positioning the pieces
        final TextView timer = findViewById(R.id.count_timer);
        // -------timer

        Intent intent = getIntent();
        final String assetName = intent.getStringExtra("assetName");
        //picture from camera and gallery ------------------------------------------------
        mCurrentPhotoPath = intent.getStringExtra("mCurrentPhotoPath");
        mCurrentPhotoUri = intent.getStringExtra("mCurrentPhotoUri");
        //-------------------------------------------------picture from camera

        // run image related code after the view was laid out
        // to have all dimensions calculated
        imageView.post(new Runnable() {
            @Override
            public void run() {
                if (assetName != null) {
                    setPicFromAsset(assetName, imageView);
                }else if(mCurrentPhotoPath != null) { //added else if picture from camera
                    setPicFromPath(mCurrentPhotoPath, imageView);
                } else if (mCurrentPhotoUri != null) {
                    imageView.setImageURI(Uri.parse(mCurrentPhotoUri));
                }
                pieces = splitImage();
                TouchListener touchListener = new TouchListener(PuzzleActivity.this);

                // shuffle pieces order
                Collections.shuffle(pieces);

                // --- SWITCH FOR COMPLEXITY LEVELS ---

//positioning and size of pieces--------------------------------------------------------------------
                int border = 28; //boarder size
                int rows ;
                int columns ;
                int marginLeft;
                double ratio = (double)((layout.getBottom() - imageView.getBottom()) - 2* border) / imageView.getHeight();
                if (ratio > 1){
                    ratio = 1;
                }

                switch (pieces.size()) {


                    case 4:
                        rows = 2;
                        columns = 2;
                        // 4 pieces - row 1
                        for (int i = 0; i < rows; i++) {
                            marginLeft = border;
                            for (int j=0; j < columns; j++){
                                PuzzlePiece piece = pieces.get((i*columns) + j);
                                piece.setOnTouchListener(touchListener);
                                layout.addView(piece);

                                RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) piece.getLayoutParams();

                                int percentageHeight = (int) (piece.pieceHeight * 3 / 4 * ratio);
                                int percentageWidth = (int) (piece.pieceWidth * 3 / 4 * ratio);
                                lParams.height = percentageHeight;
                                lParams.width = percentageWidth;
                                lParams.leftMargin = marginLeft;
                                lParams.topMargin = imageView.getBottom() + i*(int)((layout.getHeight()-imageView.getBottom() - border)/rows)+border;
                                lParams.bottomMargin = border;
                                piece.setLayoutParams(lParams);

                                marginLeft += (int)(imageView.getWidth()/columns) + (int)border/(columns-1);
                            }
                        }

                        break;



                    // --- 9 pieces ---
                    case 9:
                        rows = 3;
                        columns = 3;
                        for (int i = 0; i < rows; i++) {
                            marginLeft = border;
                            for (int j=0; j < columns; j++){
                                PuzzlePiece piece = pieces.get((i*columns) + j);
                                piece.setOnTouchListener(touchListener);
                                layout.addView(piece);

                                RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) piece.getLayoutParams();

                                int percentageHeight = (int) (piece.pieceHeight * 3 / 4 * ratio);
                                int percentageWidth = (int) (piece.pieceWidth * 3 / 4 * ratio);
                                lParams.height = percentageHeight;
                                lParams.width = percentageWidth;
                                lParams.leftMargin = marginLeft;
                                lParams.topMargin = imageView.getBottom() + i*(int)((layout.getHeight()-imageView.getBottom() - border)/rows)+border;
                                lParams.bottomMargin = border;
                                piece.setLayoutParams(lParams);

                                marginLeft += (int)(imageView.getWidth()/columns) + (int)border/(columns-1);
                            }
                        }
                        break;

                    // --- 12 pieces ---
                    case 12:

                        rows = 3;
                        columns = 4;
                        for (int i = 0; i < rows; i++) {
                            marginLeft = border;
                            for (int j=0; j < columns; j++){
                                PuzzlePiece piece = pieces.get((i*columns) + j);
                                piece.setOnTouchListener(touchListener);
                                layout.addView(piece);

                                RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) piece.getLayoutParams();

                                int percentageHeight = (int) (piece.pieceHeight * 3 / 4 * ratio);
                                int percentageWidth = (int) (piece.pieceWidth * 3 / 4 * ratio);
                                lParams.height = percentageHeight;
                                lParams.width = percentageWidth;
                                lParams.leftMargin = marginLeft;
                                lParams.topMargin = imageView.getBottom() + i*(int)((layout.getHeight()-imageView.getBottom() - border)/rows)+border;
                                lParams.bottomMargin = border;
                                piece.setLayoutParams(lParams);

                                marginLeft += (int)(imageView.getWidth()/columns) + (int)border/(columns-1);
                            }
                        }

                        break;

                }
                // --- /switch ---

                // --- original code ---
//                for (PuzzlePiece piece : pieces) {
//                    piece.setOnTouchListener(touchListener);
//                    layout.addView(piece);
//                    // randomize position, on the bottom of the screen
//                    //need to make not overlapping
//
//                    RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) piece.getLayoutParams();
//                    lParams.leftMargin = new Random().nextInt(layout.getWidth() - piece.pieceWidth);
//                    lParams.topMargin = layout.getHeight() - piece.pieceHeight;
//                    piece.setLayoutParams(lParams);
//                }
                // --- /original code ---
            }
        });
    }
//--------------------------------------------------------------------positioning and size of pieces


//    // --- create an array of pieces, cols, rows, id from intent extras
//    private int[] getPiecesColsRowsId () {
//        Intent getComplexityFromGridView = getIntent();
//        int[] arrayPiecesColsRowsId = new int[4];
//
//        int id = getComplexityFromGridView.getIntExtra("userId",0);
//        int piecesNumber = getComplexityFromGridView.getIntExtra("numOfPiecesToPuz", 0);
//        int cols = getComplexityFromGridView.getIntExtra("numOfColumnsToPuz", 0);
//        int rows = getComplexityFromGridView.getIntExtra("numOfRowsToPuz", 0);
//
//        arrayPiecesColsRowsId[0] = id;
//        arrayPiecesColsRowsId[1] = piecesNumber;
//        arrayPiecesColsRowsId[2] = cols;
//        arrayPiecesColsRowsId[3] = rows;
//
//        Log.w(PuzzleActivity.class.getName(), id + ", " + piecesNumber + ", " + cols + ", " + rows);
//        return arrayPiecesColsRowsId;
//    }
//    // ---

    // --- methods to get number of pieces, columns, rows and id from intent extras
    private int getPiecesNumber() {
        Intent getComplexityFromGridView = getIntent();
        piecesNumber = getComplexityFromGridView.getIntExtra("numOfPiecesToPuz", 0);
        return piecesNumber;
    }

    private int getCols() {
        Intent getComplexityFromGridView = getIntent();
        cols = getComplexityFromGridView.getIntExtra("numOfColumnsToPuz", 0);
        return cols;
    }

    private int getRows() {
        Intent getComplexityFromGridView = getIntent();
        rows = getComplexityFromGridView.getIntExtra("numOfRowsToPuz", 0);
        return rows;
    }

    private int getUserId() {
        Intent getComplexityFromGridView = getIntent();
        userId = getComplexityFromGridView.getIntExtra("userId",0);
        return userId;
    }
    // --- methods to get complexity and id




    public void checkGameOver( ) {
        if (isGameOver()) {
            timer.cancel(); //stops the timer

            //we want to do it after 3 seconds
            //3 sec waiting timer---------------------------------------------------------------
           CountDownTimer pauseTimer = new CountDownTimer(3000,1000) {
               @Override
               public void onTick(long millisUntilFinished) {

               }
               @Override
               public void onFinish() {

                  piecesNumber = getPiecesNumber();
                  userId = getUserId();
                   Log.w(PuzzleActivity.class.getName(), "Received level is " + piecesNumber);
                   Log.w(PuzzleActivity.class.getName(), "Received id is " + userId);

                   Intent countIntent = new Intent(getApplicationContext(), ScoreActivity.class);
                   countIntent.putExtra("KEY_SEND", countTimer.getText().toString());//want to transfer final textview with seconds
                   countIntent.putExtra("userId", userId);
                   countIntent.putExtra("level", piecesNumber);
                   Log.w(PuzzleActivity.class.getName(), "Sent level is " + piecesNumber);
                   Log.w(PuzzleActivity.class.getName(), "Sent id is " + userId);
                   startActivity(countIntent);//transfers to ScoreActivity

               }
           };
           pauseTimer.start();
            //---------------------------------------------------------------3 sec waiting timer

        }
    }


    private boolean isGameOver() {
        for (PuzzlePiece piece : pieces) {
            if (piece.canMove) {
                return false;
            }
        }

        return true;
    }

    private void setPicFromAsset(String assetName, ImageView imageView) {
        // Get the dimensions of the View
        int targetW = imageView.getWidth();
        int targetH = imageView.getHeight();

        AssetManager am = getAssets();
        try {
            InputStream is = am.open("img/" + assetName);
            // Get the dimensions of the bitmap
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(is, new Rect(-1, -1, -1, -1), bmOptions);
            int photoW = bmOptions.outWidth;
            int photoH = bmOptions.outHeight;

            // Determine how much to scale down the image
            int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

            is.reset();

            // Decode the image file into a Bitmap sized to fill the View
            bmOptions.inJustDecodeBounds = false;
            bmOptions.inSampleSize = scaleFactor;

            Bitmap bitmap = BitmapFactory.decodeStream(is, new Rect(-1, -1, -1, -1), bmOptions);
            imageView.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private ArrayList<PuzzlePiece> splitImage() {

//         // --- intent to getIntExtras from grid view activity
//        Intent getComplexityFromGridView = getIntent();
//
//        int piecesNumber = getComplexityFromGridView.getIntExtra("numOfPiecesToPuz", 0);
//        int rows =  getComplexityFromGridView.getIntExtra("numOfRowsToPuz", 0);
//        int cols =  getComplexityFromGridView.getIntExtra("numOfColumnsToPuz", 0);
//        Log.w(PuzzleActivity.class.getName(), "Number of pieces is " + piecesNumber);
//        // ---

        /*int piecesNumber = 56; // to check another possibilities
        int rows = 7;
        int cols = 8;
*/

//        // --- using array
//        arrayPiecesColsRowsId = getPiecesColsRowsId();
//        int piecesNumber = arrayPiecesColsRowsId[1];
//        int cols = arrayPiecesColsRowsId[2];
//        int rows = arrayPiecesColsRowsId[3];
//        // ---

        // --- using separate methods
        piecesNumber = getPiecesNumber();
        cols = getCols();
        rows = getRows();
        // ---

        ImageView imageView = findViewById(R.id.imageView);

        ArrayList<PuzzlePiece> pieces = new ArrayList<>(piecesNumber);

        // Get the scaled bitmap of the source image
        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
        Bitmap bitmap = drawable.getBitmap();

        int[] dimensions = getBitmapPositionInsideImageView(imageView);
        int scaledBitmapLeft = dimensions[0];
        int scaledBitmapTop = dimensions[1];
        int scaledBitmapWidth = dimensions[2];
        int scaledBitmapHeight = dimensions[3];

        int croppedImageWidth = scaledBitmapWidth - 2 * abs(scaledBitmapLeft);
        int croppedImageHeight = scaledBitmapHeight - 2 * abs(scaledBitmapTop);

        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, scaledBitmapWidth, scaledBitmapHeight, true);//calls createBitmap(Bitmap source, int 0, int 0, int width, int height), matrix- m, filter , The result will be the same as bitmap buth with sizes scaledBitmapWidth, scaledBitmapHeight
        Bitmap croppedBitmap = Bitmap.createBitmap(scaledBitmap, abs(scaledBitmapLeft), abs(scaledBitmapTop), croppedImageWidth, croppedImageHeight);//creates bitmap from existing bitmap- scaledbitmap, x, y, width, height

        // Calculate the with and height of the pieces
        int pieceWidth = croppedImageWidth/cols;
        int pieceHeight = croppedImageHeight/rows;

        // Create each bitmap piece and add it to the resulting array
        int yCoord = 0;
        for (int row = 0; row < rows; row++) {
            int xCoord = 0;
            for (int col = 0; col < cols; col++) {
                // calculate offset for each piece
                int offsetX = 0;
                int offsetY = 0;
                if (col > 0) {
                    offsetX = pieceWidth / 3;
                }
                if (row > 0) {
                    offsetY = pieceHeight / 3;
                }

                // apply the offset to each piece
                Bitmap pieceBitmap = Bitmap.createBitmap(croppedBitmap, xCoord - offsetX, yCoord - offsetY, pieceWidth + offsetX, pieceHeight + offsetY);
                PuzzlePiece piece = new PuzzlePiece(getApplicationContext());
                piece.setImageBitmap(pieceBitmap);
                piece.xCoord = xCoord - offsetX + imageView.getLeft();
                piece.yCoord = yCoord - offsetY + imageView.getTop();
                piece.pieceWidth = pieceWidth + offsetX;
                piece.pieceHeight = pieceHeight + offsetY;

                // this bitmap will hold our final puzzle piece image
                Bitmap puzzlePiece = Bitmap.createBitmap(pieceWidth + offsetX, pieceHeight + offsetY, Bitmap.Config.ARGB_8888);

                // draw path
                int bumpSize = pieceHeight / 4;
                Canvas canvas = new Canvas(puzzlePiece);
                Path path = new Path();
                path.moveTo(offsetX, offsetY);
                if (row == 0) {
                    // top side piece
                    path.lineTo(pieceBitmap.getWidth(), offsetY);
                } else {
                    // top bump
                    path.lineTo(offsetX + (pieceBitmap.getWidth() - offsetX) / 3.0f, offsetY);
                    path.cubicTo(offsetX + (pieceBitmap.getWidth() - offsetX) / 6.0f, offsetY - bumpSize, offsetX + (pieceBitmap.getWidth() - offsetX) / 6.0f * 5, offsetY - bumpSize, offsetX + (pieceBitmap.getWidth() - offsetX) / 3.0f * 2, offsetY);
                    path.lineTo(pieceBitmap.getWidth(), offsetY);
                }

                if (col == cols - 1) {
                    // right side piece
                    path.lineTo(pieceBitmap.getWidth(), pieceBitmap.getHeight());
                } else {
                    // right bump
                    path.lineTo(pieceBitmap.getWidth(), offsetY + (pieceBitmap.getHeight() - offsetY) / 3.0f);
                    path.cubicTo(pieceBitmap.getWidth() - bumpSize,offsetY + (pieceBitmap.getHeight() - offsetY) / 6.0f, pieceBitmap.getWidth() - bumpSize, offsetY + (pieceBitmap.getHeight() - offsetY) / 6.0f * 5, pieceBitmap.getWidth(), offsetY + (pieceBitmap.getHeight() - offsetY) / 3.0f * 2);
                    path.lineTo(pieceBitmap.getWidth(), pieceBitmap.getHeight());
                }

                if (row == rows - 1) {
                    // bottom side piece
                    path.lineTo(offsetX, pieceBitmap.getHeight());
                } else {
                    // bottom bump
                    path.lineTo(offsetX + (pieceBitmap.getWidth() - offsetX) / 3.0f * 2, pieceBitmap.getHeight());
                    path.cubicTo(offsetX + (pieceBitmap.getWidth() - offsetX) / 6.0f * 5,pieceBitmap.getHeight() - bumpSize, offsetX + (pieceBitmap.getWidth() - offsetX) / 6.0f, pieceBitmap.getHeight() - bumpSize, offsetX + (pieceBitmap.getWidth() - offsetX) / 3.0f, pieceBitmap.getHeight());
                    path.lineTo(offsetX, pieceBitmap.getHeight());
                }

                if (col == 0) {
                    // left side piece
                    path.close();
                } else {
                    // left bump
                    path.lineTo(offsetX, offsetY + (pieceBitmap.getHeight() - offsetY) / 3.0f * 2);
                    path.cubicTo(offsetX - bumpSize, offsetY + (pieceBitmap.getHeight() - offsetY) / 6.0f * 5, offsetX - bumpSize, offsetY + (pieceBitmap.getHeight() - offsetY) / 6.0f, offsetX, offsetY + (pieceBitmap.getHeight() - offsetY) / 3.0f);
                    path.close();
                }

                // mask the piece
                Paint paint = new Paint();
                paint.setColor(0XFF000000);
                paint.setStyle(Paint.Style.FILL);

                canvas.drawPath(path, paint);
                paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
                canvas.drawBitmap(pieceBitmap, 0, 0, paint);

                // draw a white border
                Paint border = new Paint();
                border.setColor(0X80FFFFFF);
                border.setStyle(Paint.Style.STROKE);
                border.setStrokeWidth(8.0f);
                canvas.drawPath(path, border);

                // draw a black border
                border = new Paint();
                border.setColor(0X80000000);
                border.setStyle(Paint.Style.STROKE);
                border.setStrokeWidth(3.0f);
                canvas.drawPath(path, border);

                // set the resulting bitmap to the piece
                piece.setImageBitmap(puzzlePiece);

                pieces.add(piece);
                xCoord += pieceWidth;
            }
            yCoord += pieceHeight;
        }

        return pieces;
    }

    private int[] getBitmapPositionInsideImageView(ImageView imageView) {
        int[] ret = new int[4];

        if (imageView == null || imageView.getDrawable() == null)
            return ret;

        // Get image dimensions
        // Get image matrix values and place them in an array
        float[] f = new float[9];
        imageView.getImageMatrix().getValues(f);

        // Extract the scale values using the constants (if aspect ratio maintained, scaleX == scaleY)
        final float scaleX = f[Matrix.MSCALE_X];
        final float scaleY = f[Matrix.MSCALE_Y];

        // Get the drawable (could also get the bitmap behind the drawable and getWidth/getHeight)
        final Drawable d = imageView.getDrawable();
        final int origW = d.getIntrinsicWidth();
        final int origH = d.getIntrinsicHeight();

        // Calculate the actual dimensions
        final int actW = Math.round(origW * scaleX);
        final int actH = Math.round(origH * scaleY);

        ret[2] = actW;
        ret[3] = actH;

        // Get image position
        // We assume that the image is centered into ImageView
        int imgViewW = imageView.getWidth();
        int imgViewH = imageView.getHeight();

        int top = (int) (imgViewH - actH)/2;
        int left = (int) (imgViewW - actW)/2;

        ret[0] = left;
        ret[1] = top;

        return ret;
    }

    //popup------------------------
    public void createNewContentDialog(){
        dialogBuilder = new AlertDialog.Builder(this);
        final View timeIsUpPopupView = getLayoutInflater().inflate(R.layout.activity_time_is_up, null);
        newTimeIsUpText = (TextView) timeIsUpPopupView.findViewById(R.id.timeIsUpText);
        newTimeIsUpNext = (Button) timeIsUpPopupView.findViewById(R.id.timeIsUpNext);

        dialogBuilder.setView(timeIsUpPopupView);
        dialog = dialogBuilder.create();
        dialog.show();

        newTimeIsUpNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //define next button
                Intent Intent = new Intent(getApplicationContext(), GridViewActivity.class);
                startActivity(Intent);
            }
        });
    }
    //------------------------popup

    //picture from camera---------------------------------------------------------------------------
    private void setPicFromPath(String mCurrentPhotoPath, ImageView imageView) {
        // Get the dimensions of the View
        int targetW = imageView.getWidth();
        int targetH = imageView.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions); //gets file from file, using path
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;


        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        Bitmap rotatedBitmap = bitmap;//just for rotating

        // rotate bitmap if needed
        try {
            ExifInterface ei = new ExifInterface(mCurrentPhotoPath);
            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotatedBitmap = rotateImage(bitmap, 90);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotatedBitmap = rotateImage(bitmap, 180);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotatedBitmap = rotateImage(bitmap, 270);
                    break;
            }
        } catch (IOException e) {
            Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }

        imageView.setImageBitmap(rotatedBitmap);
    }
//rotates bitmap to angle degrees
    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }
    //---------------------------------------------------------------------------picture from camera



}